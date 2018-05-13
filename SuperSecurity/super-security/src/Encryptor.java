import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.Cipher;

public class Encryptor {
    public PublicKey pubKey;
    private PrivateKey privateKey;
    private byte[] encrypted;

    public Encryptor() throws Exception {
        KeyPair keyPair = buildKeyPair();
        this.pubKey = keyPair.getPublic();
        this.privateKey = keyPair.getPrivate();
    }

    public void writeEncrypted(String fileName) throws Exception {
        byte[] message = Files.readAllBytes(Paths.get(fileName));
        encrypted = encrypt(this.privateKey, new String(message));
        System.out.println(new String(encrypted));
        //BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        //writer.write(new String(encrypted));
    }

    public void writeDecrypted(String fileName) throws Exception {
        //byte[] message = Files.readAllBytes(Paths.get(fileName));
        byte[] decrypted = decrypt(this.pubKey, encrypted);
        System.out.println(new String(decrypted));
        //BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        //writer.write(new String(decrypted));
    }

    public static KeyPair buildKeyPair() throws NoSuchAlgorithmException {

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        return keyPairGenerator.genKeyPair();
    }

    public static byte[] encrypt(PrivateKey privateKey, String message) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        return cipher.doFinal(message.getBytes());
    }

    public static byte[] decrypt(PublicKey publicKey, byte[] encrypted) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        return cipher.doFinal(encrypted);
    }
}
