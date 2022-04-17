package com.tversu.aidavydenko.parts;

import com.tversu.aidavydenko.utils.FileManager;
import com.tversu.aidavydenko.utils.SecretImpl;
import com.tversu.aidavydenko.utils.SecretPart;

import java.util.List;

import static com.tversu.aidavydenko.utils.Utils.gcdex;
import static com.tversu.aidavydenko.utils.Utils.sieveOfEratosthenes;

public class RecoverSecret {
    private static final int MIN_NUMBER_OF_SECRET= 4;
    public static void recover() {
        SecretImpl secretImpl = new SecretImpl();
        // TODO: считываение частей секрета из json
        List<SecretPart> secretParts = FileManager.getSecretPartsPoints();
        int numK = secretParts.size();//кол-во частей, считанных из файла
        if(numK < MIN_NUMBER_OF_SECRET) {
            throw new RuntimeException("Недостаточное кол-во частей секрета");
        }
        int P = secretParts.get(0).getP();
        int recoverySecret = interpolatingLagrangePolynomial(secretParts, P) % P;
        FileManager.writeSecret(recoverySecret, P);
    }

    private static int interpolatingLagrangePolynomial(List<SecretPart> points, int P) {
        int result = 0;
        Integer[] values = points.stream().map(SecretPart::getValue).toArray(Integer[]::new);
        Integer[] onlyPoints = points.stream().map(SecretPart::getPoint).toArray(Integer[]::new);
        for (int i = 0; i < points.size(); i++) {
            result += findLi(i, onlyPoints, P)*values[i];
        }
        return result;
    }

    private static int findLi(int i, Integer[] points, int P){
        int numerator = 1;
        int denominator = 1;
        for (int j = 0; j < i; j++) {
            numerator *= points[j];
            denominator *= points[i] - points[j];
        }
        for (int j = i+1; j < points.length; j++) {
            numerator *= points[j];
            denominator *= points[i] - points[j];
        }
        numerator = numerator % P;
        denominator = (gcdex(denominator, P)[1] % P + P) % P;//поиск обратного
        numerator *= denominator;
        return numerator;
    }
}
