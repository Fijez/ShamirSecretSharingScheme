package com.tversu.aidavydenko.secret;

import java.util.*;

import static com.tversu.aidavydenko.utils.Utils.getRandPolinom;
import static com.tversu.aidavydenko.utils.Utils.sieveOfEratosthenes;

public class GenerateSecret {

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
        // TODO: считываение P из json
        int P = 0;
        if (P == 0) {
            P = sieveOfEratosthenes((int) ((Math.random() * 100) + 20));
        }
        // TODO: считываение общего кол-ва частей секрета из json
        int numberSecretParts = 0;
        if (numberSecretParts != 0) {
            numberSecretParts = (int) ((Math.random() * 10) + 4);
        }
        // TODO: считываение секрета из json
        int secret = 18938 % P;//считывание секрета

        //значение с a1 по ak-1 генерируем
        List<Integer> polinom = new ArrayList<>(getRandPolinom(K, secret, P));
        Map<Integer, Integer> shares = findShares(polinom, numberSecretParts, P);
        // TODO: реализовать запись в json
    }

    public static Map<Integer, Integer> findShares(List<Integer> factors, int numberParts, int P){
        Map<Integer, Integer> shares = new HashMap<>();
        for (int i = 1; i <= numberParts; i++) {
            int temp = 0;
            for (int j = 0; j < factors.size(); j++) {
                //можно релизовать случаный параметр для поиска значения функ-и
                temp += factors.get(j) * Math.pow(i, j);
            }
            shares.put(i, temp % P);
        }
        return shares;
    }
}
