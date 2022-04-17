import com.tversu.aidavydenko.parts.RecoverSecret;
import com.tversu.aidavydenko.secret.GenerateSecret;

public class Demo {

    public static void main(String[] args) {
//        Secret secret = new Secret(FileManager.readSecret());
        RecoverSecret.recover();
//        System.out.println(secret);
    }
}
