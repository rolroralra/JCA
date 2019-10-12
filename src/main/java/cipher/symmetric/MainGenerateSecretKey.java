package cipher.symmetric;

import cipher.symmetric.engine.SymmetricCipher;
import util.Bytes;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainGenerateSecretKey {
    public static void main(String[] args) throws Exception {
        /* Way1. using "KeyGenerator" */
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        SecretKey key = keyGenerator.generateKey();

        System.out.println("Algorithm: " + key.getAlgorithm());
        System.out.println("Format: " + key.getFormat());
        System.out.println("Size: " + key.getEncoded().length);
        System.out.println("HexString: " + Bytes.convertBytesToHexString(key.getEncoded()));


        /* Way2. using "SecretKeySpec" to load saved key */
        SecureRandom random = new SecureRandom();
        byte[] keyData =  key.getEncoded();

        SecretKey loadedKey = new SecretKeySpec(keyData, "AES");
        if (Arrays.equals(loadedKey.getEncoded(), key.getEncoded())) {
            System.out.println("Success to load secret key!");
        }


        /* Save secret key to file */
        String keyFileName = SymmetricCipher.saveKeyFile(key, "secretKey01");


        /* Load secret key from file */
        SecretKey loadedKeyFromFile = SymmetricCipher.loadKeyFile(keyFileName);

        System.out.println("Algorithm: " + loadedKeyFromFile.getAlgorithm());
        System.out.println("Format: " + loadedKeyFromFile.getFormat());
        System.out.println("Size: " + loadedKeyFromFile.getEncoded().length);
        System.out.println("HexString: " + Bytes.convertBytesToHexString(loadedKeyFromFile.getEncoded()));
        if (Arrays.equals(loadedKeyFromFile.getEncoded(), key.getEncoded())) {
            System.out.println("Success to load secret key from key file(\"" + keyFileName + "\")!");
        }

        KeyGenerator desKeyGenerator = KeyGenerator.getInstance("DES");
        SecretKey desKey = desKeyGenerator.generateKey();
        System.out.println(Bytes.convertBytesToHexString(desKey.getEncoded()));
        System.out.println(desKey.getAlgorithm());
        System.out.println(desKey.getFormat());
        System.out.println(desKey.getEncoded().length);


        byte[] desKeyBytes = desKey.getEncoded();

        DESKeySpec desKeySpec = new DESKeySpec(desKeyBytes);

        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey convertedDesKeyBySecretKeyFactory = keyFactory.generateSecret(desKeySpec);

        SecretKey instanceOfSecretKey = new SecretKeySpec(desKeyBytes, "DES");
        System.out.println(Arrays.equals(instanceOfSecretKey.getEncoded(), convertedDesKeyBySecretKeyFactory.getEncoded()));

    }
}
