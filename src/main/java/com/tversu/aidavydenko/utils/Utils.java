package com.tversu.aidavydenko.utils;

import java.util.*;

public class Utils {
    public static List<Integer> getRandPolinom(int k, int secret, int P) {
        //List<Integer> factors = new ArrayList<>(P);
        List<Integer> result = new LinkedList<>();
//        for (int i = 0; i < P; i++) {1
//            factors.add(i);
//        }
//        factors.remove(secret);
        result.add(secret);
        for (int i = 1; i < k; i++) {
//            int index = (int) (Math.random() * factors.size());
            int index = (int) (Math.random() * P);
            result.add(index % P);
//            factors.remove(index);
        }
        return result;
    }

    public static int sieveOfEratosthenes(int n) {
        boolean[] prime = new boolean[n + 1];
        Arrays.fill(prime, true);
        for (int p = 2; p * p <= n; p++) {
            if (prime[p]) {
                for (int i = p * 2; i <= n; i += p) {
                    prime[i] = false;
                }
            }
        }
        List<Integer> primeNumbers = new LinkedList<>();
        for (int i = 2; i <= n; i++) {
            if (prime[i]) {
                primeNumbers.add(i);
            }
        }
        return primeNumbers.get(primeNumbers.size() - 1);
    }

//    public static int[] gcdex(int a, int b) {//расширенный алгоритм евклида
////        a = Math.abs(a);
//        if (b == 0) {
//            int x = 1; // База рекурсии. Если b=0, то НОД=a, 1*a+0=d
//            int y = 0;
//            return new int[] {a,b,x,y};// нужен только a
//        }
//        int[] fromEnd = gcdex(b, a % b); //рекурсивный переход
//        int x = fromEnd[3];
//        int y = fromEnd[2] - (a / b) * fromEnd[3];
//        return new int[]{a,b,x,y};
//    }
    public static int mod(int a, int P){
        int i = 0;
        a = (a % P + P) % P;
        if (a % P != 0)
        while ((a * i) % P != 1) {
            i++;
        }
        return i % P;
    }
}
