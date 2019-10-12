package cipher.symmetric;

import cipher.symmetric.engine.SymmetricCipher;
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

public class MainCipherSymmetricSymmetricCCM extends SymmetricCipher {
    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    public static void main(String[] args) throws Exception {
        // 1. Generate AES Secret Key
        SecretKey secretKey = SymmetricCipher.generateSecretKey(256, "AES");
        System.out.println(secretKey.getEncoded().length);

        String keyFileName = SymmetricCipher.saveKeyFile(secretKey,"aes_ccm");

        // 1. Generate AES Secret Key from saved file
//        secretKey = SymmetricCipher.loadKeyFile(keyFileName)

        /* 2. IV(Initial Vector) generate */
        SecureRandom secureRandom = new SecureRandom();
//        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
//        System.out.println(secureRandom.getAlgorithm());
//        System.out.println(secureRandom.getProvider());
        byte[] ivBytes = new byte[13];   // CCM Mode must need 7 ~ 13 Byte Nonce
        System.out.println("Block Size : " + Cipher.getInstance("AES/CCM/NoPadding").getBlockSize());
        secureRandom.nextBytes(ivBytes);
        System.out.println(Bytes.convertBytesToHexString(ivBytes));
        IvParameterSpec ivParameterSpec = new IvParameterSpec(ivBytes);


        // 3. AES Encryption
        Charset charset = Charset.forName("UTF-8");
        String plainText = "오늘도 별이 바람에 스치운다. How do you think?";
        System.out.println(plainText);
        byte[] encryptedBytes = encrypt(secretKey, plainText.getBytes(charset), ivParameterSpec);
        System.out.println(Bytes.convertBytesToHexString(encryptedBytes));
        System.out.println(encryptedBytes.length);

        /* 4. AES Decryption */
        byte[] decryptedBytes = decrypt(secretKey, encryptedBytes, ivParameterSpec);
        String decryptedString = new String(decryptedBytes, charset);
        System.out.println(decryptedString);

    }

    public static SecretKey generateKey(int keyBitSize) throws GeneralSecurityException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(keyBitSize, SecureRandom.getInstance("SHA1PRNG"));
        return keyGenerator.generateKey();
    }

    public static byte[] encrypt(SecretKey secretKey, byte[] plainBytes, IvParameterSpec ivParameterSpec) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("AES/CCM/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);
        byte[] result = cipher.doFinal(plainBytes);
        return result;
    }

    public static byte[] decrypt(SecretKey secretKey, byte[] encryptedBytes, IvParameterSpec ivParameterSpec) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("AES/CCM/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
        return cipher.doFinal(encryptedBytes);
    }
}
