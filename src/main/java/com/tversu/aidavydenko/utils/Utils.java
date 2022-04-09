package com.tversu.aidavydenko.utils;

import java.util.*;

public class Utils {
    public static Set<Integer> getRandPolinom(int k, int secret, int P) {
        List<Integer> factors = new ArrayList<>(P);
        Set<Integer> result = new HashSet<>(k);
        for (int i = 0; i < P; i++) {
            factors.add(i);
        }
        //factors.add(secret);
        for (int i = 1; i <= k; i++) {
            int index = (int) (Math.random() * factors.size());
            result.add(factors.get(index));
            factors.remove(index);
//            if (!factors.add((int) (Math.random() * P))) {
//                i--;
//                //возможен долгий поиск уникального значения
//            }
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

    public static int[] gcdex(int a, int b) {//расширенный алгоритм евклида
        if (b == 0) {
            return new int[]{a, 1, 0};
        }
        else {
            int[] temp = gcdex(b, a % b);
            return new int[]{temp[0], temp[2], temp[1] - temp[2] * (a / b)};
        }
    }
}
