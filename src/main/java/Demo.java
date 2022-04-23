import com.tversu.aidavydenko.parts.RecoverSecret;
import com.tversu.aidavydenko.secret.GenerateSecret;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;


public class Demo {

    public static void main(String[] args) {
        clearFolders();
        GenerateSecret.generateSecret();
        RecoverSecret.recover();
//        GenerateSecret.generateSecret();
    }

    private static void clearFolders() {

        Path parts = Paths.get("src/main/java/resources/parts");
        Path restoreSecret = Paths.get("src/main/java/resources/restoreSecret");

        for (File file: Objects.requireNonNull(parts.toFile().listFiles())) {
            file.delete();
        }
        for (File file: Objects.requireNonNull(restoreSecret.toFile().listFiles())) {
            file.delete();
        }
    }
}
