package cipher.symmetric;

import cipher.symmetric.engine.SymmetricCipher;
import util.Bytes;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;

public class MainCipherSymmetricSymmetricECB extends SymmetricCipher {
    public static void main(String[] args) throws Exception {
        // 1. Generate AES Secret Key
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256, SecureRandom.getInstance("SHA1PRNG"));
        SecretKey secretKey = keyGenerator.generateKey();
        System.out.println(secretKey.getEncoded().length);

        saveKeyFile(secretKey, "aesKey.raw");

        // 1. Generate AES Secret Key from saved file
//        SecretKey secretKey = loadKeyFile("aesKey.raw");


        // 2. AES Encryption
        Charset charset = Charset.forName("UTF-8");
        String plainText = "오늘도 별이 바람에 스치운다. How do you think?";
        System.out.println(plainText);
        byte[] encryptedBytes = encrypt(secretKey, plainText.getBytes(charset));
        System.out.println(Bytes.convertBytesToHexString(encryptedBytes));
        System.out.println(encryptedBytes.length);

        /* 3. AES Decryption */
        byte[] decryptedBytes = decrypt(secretKey, encryptedBytes);
        String decryptedString = new String(decryptedBytes, charset);
        System.out.println(decryptedString);

    }

    public static SecretKey generateKey(int keyBitSize) throws GeneralSecurityException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(keyBitSize, SecureRandom.getInstance("SHA1PRNG"));
        return keyGenerator.generateKey();
    }

    public static byte[] encrypt(SecretKey secretKey, byte[] plainBytes) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return cipher.doFinal(plainBytes);
    }

    public static byte[] decrypt(SecretKey secretKey, byte[] encryptedBytes) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return cipher.doFinal(encryptedBytes);
    }
}
