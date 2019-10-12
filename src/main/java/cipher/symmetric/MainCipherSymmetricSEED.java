package cipher.symmetric;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import util.Bytes;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.security.Security;

public class MainCipherSymmetricSEED {
    public static void main(String[] args) throws Exception {
        /* 1. Add Bouncy Castle Provider supporting SEED Algorithm */
        Security.addProvider(new BouncyCastleProvider());

        /* 2. Generate SecretKey */
        KeyGenerator keyGenerator = KeyGenerator.getInstance("SEED");
        keyGenerator.init(128);
        SecretKey secretKey = keyGenerator.generateKey();

        /* 3. IV(Initial Vector) generate */
        SecureRandom secureRandom = new SecureRandom();
//        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
//        System.out.println(secureRandom.getAlgorithm());
//        System.out.println(secureRandom.getProvider());
        byte[] ivBytes = new byte[16];   // 128 bit
        secureRandom.nextBytes(ivBytes);
        System.out.println(Bytes.convertBytesToHexString(ivBytes));
        IvParameterSpec ivParameterSpec = new IvParameterSpec(ivBytes);


        /* 4. SEED Encryption */
        Charset charset = Charset.forName("UTF-8");
        String plainText = "오늘도 별이 바람에 스치운다. How do you think?";
        System.out.println(plainText);
        System.out.println(plainText.getBytes(charset).length);
        byte[] encryptedBytes = encrypt(secretKey, ivParameterSpec, plainText.getBytes(charset));
        System.out.println(Bytes.convertBytesToHexString(encryptedBytes));
        System.out.println(encryptedBytes.length);

//        secureRandom.nextBytes(ivBytes);
//        System.out.println(Bytes.convertBytesToHexString(ivBytes));
//        ivParameterSpec = new IvParameterSpec(ivBytes);
        byte[] decryptedBytes = decrypt(secretKey, ivParameterSpec, encryptedBytes);
        String decryptedString = new String(decryptedBytes, charset);
        System.out.println(decryptedString);

    }

    public static SecretKey generateKey(int keyBitSize) throws GeneralSecurityException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("SEED");
        keyGenerator.init(keyBitSize);
        return keyGenerator.generateKey();
    }

    public static byte[] encrypt(SecretKey secretKey, IvParameterSpec ivParameterSpec, byte[] plainBytes) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("SEED/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);
        return cipher.doFinal(plainBytes);
    }

    public static byte[] decrypt(SecretKey secretKey, IvParameterSpec ivParameterSpec, byte[] encodedBytes) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("SEED/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
        return cipher.doFinal(encodedBytes);
    }
}
