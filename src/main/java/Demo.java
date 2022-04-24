import com.tversu.aidavydenko.parts.RecoverSecret;
import com.tversu.aidavydenko.secret.AddSecretParts;
import com.tversu.aidavydenko.secret.GenerateSecret;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Scanner;

import static com.tversu.aidavydenko.utils.FileManager.clearFolders;


public class Demo {

    public static void main(String[] args) {
        while(true) {
            System.out.println("������� 1, ����� ��������� ������\n" +
                    "������� 2, ����� ������������ ������\n" +
                    "������� 3, ����� �������� ����� �������\n" +
                    "������� 4, ����� ��������� ������ ���������\n");
            int chose = new Scanner(System.in).nextInt();
            switch (chose) {
                case 1:
                    clearFolders();
                    GenerateSecret.generateSecret();
                    break;
                case 2:
                    RecoverSecret.recover();
                    break;
                case 3:
                    // TODO: ����������� ��������, ������� ������
                    AddSecretParts.addSecretParts();
                    break;
            }
        }
    }
}
