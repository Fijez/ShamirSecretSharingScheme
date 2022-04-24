package com.tversu.aidavydenko.secret;

import com.tversu.aidavydenko.utils.FileManager;
import com.tversu.aidavydenko.utils.SecretImpl;
import com.tversu.aidavydenko.utils.SecretPart;

import java.util.*;

import static com.tversu.aidavydenko.utils.Utils.getRandPolinom;
import static com.tversu.aidavydenko.utils.Utils.sieveOfEratosthenes;

public class  GenerateSecret {

    private static final int K = 4;
    public static void generateSecret() {
        /**
        минимальное кол-во участников,
        необходимых для восстановления секрета
        необходимо использовать полином степени K-1, т.е. 3

        входное значение из json, обозначающее ограничение на множестве,
        mod P (если не указан, то генерируем)
        так же генерируем общее кол-во частей ключей

         secret < P
         данное из json
         первое значение полинома(a0 = secret), у которого x^0

         //вывод в json shares (так же нельзя забывать размер поля и
         степерь полинома, но по ТЗ его не передаем)
         */
        //считали из json
        SecretImpl secretImpl = FileManager.readSecret();
        int P = secretImpl.getP();
        int numberSecretParts = secretImpl.getPartsCount();
        if (numberSecretParts < 4) {
            throw new RuntimeException("Недостаточное кол-во генерируемых частей");
        }
        int secretN = secretImpl.getSecret();//считывание секрета

        //значение с a1 по ak-1 генерируем
            List<Integer> polinom = new ArrayList<>(getRandPolinom(K, secretN, P));
        List<SecretPart> shares = findShares(polinom, numberSecretParts, P);
        // TODO: потом измени запись в файл, чтобы части разделенного секрета записывались в номвую папку каждый раз
        FileManager.writeSharingSecret(shares);
    }
    public static List<SecretPart> findShares(List<Integer> polinom, int numberSecretParts, int P){
        List<SecretPart> shares = new ArrayList<>();
        for (int i = 1; i <= numberSecretParts; i++) {
            int temp = polinom.get(0);
            int k = (int) (Math.random()*P);
            if(shares.stream().anyMatch(x->x.getPoint().equals(k))){
                i--;
                continue;
            }
            for (int j = 1; j < polinom.size(); j++) {
                temp = (temp + (int)(polinom.get(j) * (Math.pow(k, j) % P)) % P)% P;
            }
            if(temp == 0){
                i--;
                continue;
            }
            shares.add(new SecretPart(k, temp, P));
        }
        return shares;
    }
}
