package cipher.pbe;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.security.Security;

/* PBES1 (Password Based Encryption Scheme 1) */
public class MainPBES1 {
    public static void main(String[] args) throws Exception {
        for (String algo : Security.getAlgorithms("Cipher")) {
            if (algo.startsWith("PBE")) {
                System.out.println(algo);
            }
        }

        /* password must be ASCII */
        char[] password = "abcde1234".toCharArray();
        System.out.println(password);
        System.out.println(password.length);

        Charset charset = Charset.forName("UTF-8");
        String plainText = "오늘도 별이 바람에 스치운다. How do you think?";

        /* Salt */
        byte[] salt = new byte[8];
        SecureRandom.getInstance("SHA1PRNG").nextBytes(salt);

        /* Iteration Count */
        int iterationCount = 1000;

        /* PBES1 Encryption */
        byte[] encryptedBytes = encryptByPBES1(password, salt, iterationCount, plainText.getBytes(charset));

        /* PBES1 Decryption */
        byte[] decryptedBytes = decryptByPBES1(password, salt, iterationCount, encryptedBytes);

        System.out.println(new String(decryptedBytes, charset));
    }

    public static byte[] encryptByPBES1(char[] password, byte[] salt, int iterationCount, byte[] plainBytes) throws GeneralSecurityException {
        /* Generate SecretKey from Password */
        PBEKeySpec pbeKeySpec = new PBEKeySpec(password);
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
        SecretKey secretKey = secretKeyFactory.generateSecret(pbeKeySpec);
//        System.out.println(secretKey.getAlgorithm());
//        System.out.println(secretKey.getFormat());
//        System.out.println(secretKey.getEncoded().length);

        /* salt, iteration count */
        PBEParameterSpec params = new PBEParameterSpec(salt, iterationCount);

        /* Encryption */
        Cipher cipher = Cipher.getInstance("PBEWithMD5AndDES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, params);
        return cipher.doFinal(plainBytes);
    }

    public static byte[] decryptByPBES1(char[] password, byte[] salt, int iterationCount, byte[] plainBytes) throws GeneralSecurityException {
        /* Generate SecretKey from Password */
        PBEKeySpec pbeKeySpec = new PBEKeySpec(password);
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
        SecretKey secretKey = secretKeyFactory.generateSecret(pbeKeySpec);
//        System.out.println(secretKey.getAlgorithm());
//        System.out.println(secretKey.getFormat());
//        System.out.println(secretKey.getEncoded().length);

        /* salt, iteration count */
        PBEParameterSpec params = new PBEParameterSpec(salt, iterationCount);

        /* Decryption */
        Cipher cipher = Cipher.getInstance("PBEWithMD5AndDES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, params);
        return cipher.doFinal(plainBytes);
    }
}
