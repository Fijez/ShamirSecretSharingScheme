package com.tversu.aidavydenko.parts;

import java.util.HashMap;
import java.util.Map;

import static com.tversu.aidavydenko.utils.Utils.gcdex;
import static com.tversu.aidavydenko.utils.Utils.sieveOfEratosthenes;

public class RecoverSecret {
    private static final int MIN_NUMBER_OF_SECRET= 4;
    public static int recover() {
        //из файла
        int P = 0;
        if (P == 0) {
            P = sieveOfEratosthenes((int) (Math.random() * 100));
            System.out.println(P);
        }
        //из файла
        Map<Integer, Integer> secretParts = new HashMap<>();
        int numK = secretParts.size();//кол-во частей, считанных из файла
        if(numK < MIN_NUMBER_OF_SECRET) {
            System.out.println("недостаточно частей секрета");
            return -1;
        }
        int secret = interpolatingLagrangePolynomial(secretParts, P) % P;
        return secret;

    }

    private static int interpolatingLagrangePolynomial(Map<Integer,Integer> points, int P) {
        int result = 0;
        Integer[] values = points.values().toArray(new Integer[0]);
        for (int i = 0; i < points.size(); i++) {
            result += findLi(i, points.keySet().toArray(new Integer[0]), P)*values[i];
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
        denominator = (gcdex(denominator, P)[1] % 13 + 13) % 13;//поиск обратного
        numerator *= denominator;
        return numerator;
    }
}
