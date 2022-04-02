package com.tversu.aidavydenko.secret;

import java.util.*;

import static com.tversu.aidavydenko.utils.Utils.getPolinom;
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
        int P = 0; //���������� ��� ��������� P(�������)
        if (P == 0) {
            P = sieveOfEratosthenes((int) ((Math.random() * 100) + 20));
        }
        int numberParts = 0;//���������� ���-�� ������ �������
        if (numberParts != 0) {
            numberParts = (int) ((Math.random() * 10) + 4);
        }
        int secret = 18938 % P;//���������� �������

        //�������� � a1 �� ak-1 ����������
        List<Integer> polinom = new ArrayList<>(getPolinom(K, secret, P));
        Map<Integer, Integer> shares = findShares(polinom, numberParts, P);
        //������ � json ����

    }

    //�������� Map, �� List<transferJSON> ?
    private static Map<Integer, Integer> findShares(List<Integer> factors, int numberParts, int P){
        Map<Integer, Integer> shares = new HashMap<>();
        for (int i = 1; i <= numberParts; i++) {
            int temp = 0;
            for (int j = 0; j < factors.size(); j++) {
                temp += factors.get(j) * Math.pow(i, j);//����� ���������� �������� �������� ��� ������ �������� ����-�
            }
            shares.put(i, temp % P);
        }
        return shares;
    }
}
