package cipher.pbe;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;

public class MainPBES2 {
    public static void main(String[] args) throws Exception {
        char[] passsword = "가나다abc1234".toCharArray();

        Charset charset = Charset.forName("UTF-8");
        String plainText = "오늘도 별이 바람에 스치운다. How do you think?";

        /* salt */
        byte[] salt = new byte[8];
        new SecureRandom().nextBytes(salt);

        /* iteration count */
        int iterationCount = 1000;

        /* Generate AES SecretKey by PBKDF2 (Password Based Key Derivation Function 2) */
        PBEKeySpec pbeKeySpec = new PBEKeySpec(passsword, salt, iterationCount, 256);
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        SecretKey secretKey = new SecretKeySpec(secretKeyFactory.generateSecret(pbeKeySpec).getEncoded(), "AES");

        /* Encryption */
        byte[] encryptedBytes = encryptByPBES2(secretKey, plainText.getBytes(charset));

        /* Decryption */
        byte[] decryptedBytes = decryptByPBES2(secretKey, encryptedBytes);

        System.out.println(new String(decryptedBytes, charset));
    }

    public static byte[] encryptByPBES2(SecretKey secretKey, byte[] plainBytes) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return cipher.doFinal(plainBytes);
    }

    public static byte[] decryptByPBES2(SecretKey secretKey, byte[] encryptedBytes) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return cipher.doFinal(encryptedBytes);
    }
}
