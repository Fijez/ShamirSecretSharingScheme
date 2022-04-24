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
        //������� �� json
        SecretImpl secretImpl = FileManager.readSecret();
        int P = secretImpl.getP();
        int numberSecretParts = secretImpl.getPartsCount();
        if (numberSecretParts < 4) {
            throw new RuntimeException("������������� ���-�� ������������ ������");
        }
        int secretN = secretImpl.getSecret();//���������� �������

        //�������� � a1 �� ak-1 ����������
            List<Integer> polinom = new ArrayList<>(getRandPolinom(K, secretN, P));
        List<SecretPart> shares = findShares(polinom, numberSecretParts, P);
        // TODO: ����� ������ ������ � ����, ����� ����� ������������ ������� ������������ � ������ ����� ������ ���
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
