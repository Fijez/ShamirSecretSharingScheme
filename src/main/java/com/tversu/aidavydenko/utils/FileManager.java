package com.tversu.aidavydenko.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * � json �������� ������ ���������� �� �������� ����
 */
public class FileManager {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final int P_FOR_RANDOM = 500;

    public static void writeSharingSecret(List<SecretPart> secretParts) {
        try {
            Path secretPartsSource = Paths.get("src", "main", "java", "resources", "parts");//Path.of("ShamirSecretSharingScheme","src", "main", "resources", "parts");
            FileWriter fileWriter;
            for (SecretPart secretPart : secretParts) {
                File secretPartFile = Path.of(secretPartsSource.toString(), secretPart.hashCode() + ".json").toFile();
                secretPartFile.createNewFile();
                fileWriter = new FileWriter(secretPartFile);
                fileWriter.write(OBJECT_MAPPER.writeValueAsString(secretPart));
                fileWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static SecretImpl readSecret() {
        //File secretPartsFolder = Path.of("src", "main", "resources").toFile();
        SecretImpl secretImpl = new SecretImpl();
        ClassLoader classLoader = FileManager.class.getClassLoader();

        URL resource = classLoader.getResource("secret.json");
        if (resource == null) {
            throw new IllegalArgumentException("file is not found!");
        }
        File secretPartsFolder = new File(resource.getFile());
        try (FileReader fileReader = new FileReader(secretPartsFolder)) {
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuilder json = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                json.append(line);
            }
            if (!(json.toString().contains("\"secret\":") && json.toString().contains("\"partsCount\":"))) {
                throw new RuntimeException("�������� ��������� json");
            }
            if (json.toString().contains("\"p\":")) {
                secretImpl = OBJECT_MAPPER.readValue(json.toString(), SecretImpl.class);
            } else if (json.toString().contains("P")) {
                throw new RuntimeException("�������� ��������� json, 'P' ������ ���� ��������");
            } else {
                JsonNode jsonNode = OBJECT_MAPPER.readTree(String.valueOf(json));
                secretImpl.setSecret(jsonNode.path("secret").asInt());
                secretImpl.setPartsCount(jsonNode.path("partsCount").asInt());
                secretImpl.setP(Utils.sieveOfEratosthenes((int) (Math.random() * P_FOR_RANDOM)));
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return secretImpl;
    }

    public static void writeSecret(int secret, int P) {

        try {
            Secret secretAnonClass = new Secret() {
                @Override
                public Integer getSecret() {
                    return secret;
                }

                @Override
                public void setSecret(Integer secret) {
                    this.secret = secret;
                }

                @Override
                public Integer getP() {
                    return P;
                }

                @Override
                public void setP(Integer p) {
                    this.P = p;
                }

                int secret;
                int P;
            };

            secretAnonClass.setSecret(secret);
            secretAnonClass.setP(P);

            File forSecret = Paths.get("src", "main", "java", "resources", "restoreSecret", "restoredSecret.json").toFile();
            FileWriter fileWriter;
            fileWriter = new FileWriter(forSecret);


            fileWriter.write(OBJECT_MAPPER.writeValueAsString(secretAnonClass));
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static List<SecretPart> getSecretPartsPoints() {
        File secretPartsFolder = Path.of("src", "main", "java", "resources", "parts").toFile();
        List<SecretPart> result = new ArrayList<>();
        for (File secretPartFile : Objects.requireNonNull(secretPartsFolder.listFiles())) {
            try (FileReader fileReader = new FileReader(secretPartFile);
                 BufferedReader bufferedReader = new BufferedReader(fileReader)) {
                StringBuilder json = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    json.append(line);
                }
                SecretPart secretPart = OBJECT_MAPPER.readValue(json.toString(), SecretPart.class);
                result.add(secretPart);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static boolean checkIfSecretExists(SecretImpl result) {
        return Path.of("src", "main", "resources", "parts", result.getSecret().toString()).toFile().exists();
    }
}
