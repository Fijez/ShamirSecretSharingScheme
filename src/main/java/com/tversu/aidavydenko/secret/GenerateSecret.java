package com.tversu.aidavydenko.secret;

import java.util.*;

import static com.tversu.aidavydenko.utils.Utils.getPolinom;
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
        int P = 0; //считывание или генерация P(простое)
        if (P == 0) {
            P = sieveOfEratosthenes((int) ((Math.random() * 100) + 20));
        }
        int numberParts = 0;//считывание кол-во частей секрета
        if (numberParts != 0) {
            numberParts = (int) ((Math.random() * 10) + 4);
        }
        int secret = 18938 % P;//считывание секрета

        //значение с a1 по ak-1 генерируем
        List<Integer> polinom = new ArrayList<>(getPolinom(K, secret, P));
        Map<Integer, Integer> shares = findShares(polinom, numberParts, P);
        //запись в json файл

    }

    //заменить Map, на List<transferJSON> ?
    private static Map<Integer, Integer> findShares(List<Integer> factors, int numberParts, int P){
        Map<Integer, Integer> shares = new HashMap<>();
        for (int i = 1; i <= numberParts; i++) {
            int temp = 0;
            for (int j = 0; j < factors.size(); j++) {
                temp += factors.get(j) * Math.pow(i, j);//можно релизовать случаный параметр для поиска значения функ-и
            }
            shares.put(i, temp % P);
        }
        return shares;
    }
}
