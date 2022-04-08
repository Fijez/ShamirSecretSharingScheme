package com.tversu.aidavydenko.secret;

import java.util.*;

import static com.tversu.aidavydenko.utils.Utils.getRandPolinom;
import static com.tversu.aidavydenko.utils.Utils.sieveOfEratosthenes;

public class GenerateSecret {

    private static final int K = 4;
    public static void generateSecret() {
        /**
        ����������� ���-�� ����������,
        ����������� ��� �������������� �������
        ���������� ������������ ������� ������� K-1, �.�. 3

        ������� �������� �� json, ������������ ����������� �� ���������,
        mod P (���� �� ������, �� ����������)
        ��� �� ���������� ����� ���-�� ������ ������

         secret < P
         ������ �� json
         ������ �������� ��������(a0 = secret), � �������� x^0

         //����� � json shares (��� �� ������ �������� ������ ���� �
         ������� ��������, �� �� �� ��� �� ��������)
         */
        // TODO: ����������� P �� json
        int P = 0;
        if (P == 0) {
            P = sieveOfEratosthenes((int) ((Math.random() * 100) + 20));
        }
        // TODO: ����������� ������ ���-�� ������ ������� �� json
        int numberSecretParts = 0;
        if (numberSecretParts != 0) {
            numberSecretParts = (int) ((Math.random() * 10) + 4);
        }
        // TODO: ����������� ������� �� json
        int secret = 18938 % P;//���������� �������

        //�������� � a1 �� ak-1 ����������
        List<Integer> polinom = new ArrayList<>(getRandPolinom(K, secret, P));
        Map<Integer, Integer> shares = findShares(polinom, numberSecretParts, P);
        // TODO: ����������� ������ � json
    }

    public static Map<Integer, Integer> findShares(List<Integer> factors, int numberParts, int P){
        Map<Integer, Integer> shares = new HashMap<>();
        for (int i = 1; i <= numberParts; i++) {
            int temp = 0;
            for (int j = 0; j < factors.size(); j++) {
                //����� ���������� �������� �������� ��� ������ �������� ����-�
                temp += factors.get(j) * Math.pow(i, j);
            }
            shares.put(i, temp % P);
        }
        return shares;
    }
}
