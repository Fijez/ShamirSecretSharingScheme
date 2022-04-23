package com.tversu.aidavydenko.parts;

import com.tversu.aidavydenko.utils.FileManager;
import com.tversu.aidavydenko.utils.SecretImpl;
import com.tversu.aidavydenko.utils.SecretPart;

import java.util.List;

import static com.tversu.aidavydenko.utils.Utils.*;

public class RecoverSecret {
    private static final int MIN_NUMBER_OF_SECRET= 4;
    public static void recover() {
        SecretImpl secretImpl = new SecretImpl();
        // TODO: ����������� ������ ������� �� json
        List<SecretPart> secretParts = FileManager.getSecretPartsPoints();
        int numK = secretParts.size();//���-�� ������, ��������� �� �����
        if(numK < MIN_NUMBER_OF_SECRET) {
            throw new RuntimeException("������������� ���-�� ������ �������");
        }
        int P = secretParts.get(0).getP();
        for (SecretPart part :
                secretParts) {
            if (!part.getP().equals(P)) {
            throw new RuntimeException("������������ ����� ������ ������");
            }
        }
        int recoverySecret = interpolatingLagrangePolynomial(secretParts, P) % P;
        FileManager.writeSecret(recoverySecret, P);
    }

    private static int interpolatingLagrangePolynomial(List<SecretPart> points, int P) {
        int result = 0;
        Integer[] values = points.stream().parallel().map(SecretPart::getValue).limit(4).toArray(Integer[]::new);
        Integer[] onlyPoints = points.stream().parallel().map(SecretPart::getPoint).limit(4).toArray(Integer[]::new);
        for (int i = 0; i < MIN_NUMBER_OF_SECRET; i++) {
            int temp = (findLi(i, onlyPoints, P) * values[i]) % P;
            result = (result + temp) % P;
        }
        return result;
    }

    private static int findLi(int i, Integer[] points, int P){
        int numerator = 1;
        int denominator = 1;
        for (int j = 0; j < i; j++) {
            numerator *= (-points[j]);
            denominator *= points[i] - points[j];
        }
        for (int j = i+1; j < points.length; j++) {
            numerator *= (-points[j]);
            denominator *= points[i] - points[j];
        }
        numerator = (numerator % P + P) % P;
        boolean denomIsUpZero = denominator > 0;
        denominator = mod(denominator, P);
        if (!denomIsUpZero){
            denominator = (denominator * 2) % P;
        }
        return (denominator * numerator) % P;
    }
}
