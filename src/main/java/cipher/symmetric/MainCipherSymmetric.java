package cipher.symmetric;

import util.Bytes;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainCipherSymmetric {
    public static void main(String[] args) throws Exception {
        /* Way1. using KeyGenerator */
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        SecretKey key = keyGenerator.generateKey();

        System.out.println("Algorithm: " + key.getAlgorithm());
        System.out.println("Format: " + key.getFormat());
        System.out.println("Size: " + key.getEncoded().length);
        System.out.println("HexString: " + Bytes.convertBytesToHexString(key.getEncoded()));


        /* Way2. using SecretKeySpec to load saved key */
        SecureRandom random = new SecureRandom();
        byte[] keyData =  key.getEncoded();

        SecretKey loadedKey = new SecretKeySpec(keyData, "AES");
        if (Arrays.equals(loadedKey.getEncoded(), key.getEncoded())) {
            System.out.println("Success to load secret key!");
        }


        /* Save secret key to file */
        File keyFile = new File("secretKey.raw");
        if (keyFile.exists()) {
            keyFile.delete();
            keyFile.createNewFile();
        }

        OutputStream out = new BufferedOutputStream(new FileOutputStream(keyFile));

        try {
            out.write(key.getEncoded());
            System.out.println("Success to save " + keyFile.getName());
        } finally {
            out.close();
        }


        /* Load secret key from file */
        InputStream in = new BufferedInputStream(new FileInputStream(keyFile));

        byte[] buffer = new byte[128];
        List<Byte> list = new ArrayList<Byte>();

        int read = 0;
        try {
            while ((read = in.read(buffer)) != -1) {
                for (int i = 0; i < read; i++) {
                    list.add(buffer[i]);
                }
            }
        } finally {
            in.close();
        }

        byte[] loadedKeyDataFromFile = new byte[list.size()];
        for (int i = 0; i < loadedKeyDataFromFile.length; i++) {
            loadedKeyDataFromFile[i] = list.get(i);
        }

        SecretKeySpec loadedKeyFromFile = new SecretKeySpec(loadedKeyDataFromFile, "AES");
        System.out.println("Algorithm: " + loadedKeyFromFile.getAlgorithm());
        System.out.println("Format: " + loadedKeyFromFile.getFormat());
        System.out.println("Size: " + loadedKeyFromFile.getEncoded().length);
        System.out.println("HexString: " + Bytes.convertBytesToHexString(loadedKeyFromFile.getEncoded()));
        if (Arrays.equals(loadedKeyFromFile.getEncoded(), key.getEncoded())) {
            System.out.println("Success to load secret key from key file(" + keyFile.getName() + ")!");
        }

    }
}
