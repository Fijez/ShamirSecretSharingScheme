package com.tversu.aidavydenko.parts;

import com.tversu.aidavydenko.secret.AddSecretParts;
import com.tversu.aidavydenko.utils.FileManager;
import com.tversu.aidavydenko.utils.SecretPart;

import java.util.List;

import static com.tversu.aidavydenko.utils.Utils.*;

public class RecoverSecret {
    private static final int MIN_NUMBER_OF_SECRET = 4;

    public static void recover() {
        //����������� ������ ������� �� json
        List<SecretPart> secretParts = FileManager.getSecretPartsPoints();
        int numK = secretParts.size();//���-�� ������, ��������� �� �����
        if (numK < MIN_NUMBER_OF_SECRET) {
            throw new RuntimeException("������������� ���-�� ������ �������");
        }
        int P = secretParts.get(0).getP();
        for (SecretPart part :
                secretParts) {
            if (!part.getP().equals(P)) {
                throw new RuntimeException("������������ ����� ������ ������");
            }
        }
        //TODO: ��������� �� ��������� ��������
        for (int i = 0; i < secretParts.size() - 1; i++) {
            SecretPart secretPart1 = secretParts.get(i);
            for (int j = i+1; j < secretParts.size(); j++) {
                SecretPart secretPart2 = secretParts.get(j);
                if (secretPart2.equals(secretPart1) || secretPart1.getPoint().equals(secretPart2.getPoint())) {
                    throw new RuntimeException("�����-�� ����� ������� ���������");
                }
            }
        }
        List<Integer> recoverySecret = AddSecretParts.interpolatingLagrangePolynomial(secretParts, P);
        FileManager.clearRestoredSecretFolder();
        FileManager.writeSecret(recoverySecret.get(0), P);
    }
}
