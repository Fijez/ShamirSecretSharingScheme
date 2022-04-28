import com.tversu.aidavydenko.parts.RecoverSecret;
import com.tversu.aidavydenko.secret.AddSecretParts;
import com.tversu.aidavydenko.secret.GenerateSecret;

import java.util.Scanner;

import static com.tversu.aidavydenko.utils.FileManager.clearFolders;


public class Demo {

    public static void main(String[] args) {
        boolean choice = true;
        while(choice) {
            System.out.println("¬ведите 1, чтобы разделить секрет\n" +
                    "¬ведите 2, чтобы восстановить секрет\n" +
                    "¬ведите 3, чтобы добавить части секрета\n" +
                    "¬ведите 4, чтобы закончить работу программы\n");
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
                    // TODO: неправильно работает, исправь ошибку
                    AddSecretParts.addSecretParts();
                    break;
                case 4:
                    choice = false;
                    break;

            }
        }
    }
}
