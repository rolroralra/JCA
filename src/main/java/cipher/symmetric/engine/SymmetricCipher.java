package cipher.symmetric.engine;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public abstract class SymmetricCipher {
    public static SecretKey generateSecretKey(int keyBitSize, String algorithm) throws GeneralSecurityException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
        keyGenerator.init(keyBitSize, SecureRandom.getInstance("SHA1PRNG"));
        return keyGenerator.generateKey();
    }

    public static String saveKeyFile(SecretKey secretKey, String fileName) throws Exception {
        /* Save secret key to file */
        String resultFileName = fileName + "." + secretKey.getAlgorithm() + "." + secretKey.getFormat();
        File keyFile = new File(resultFileName);
        if (keyFile.exists()) {
            keyFile.delete();
            keyFile.createNewFile();
        }

        OutputStream out = new BufferedOutputStream(new FileOutputStream(keyFile));

        try {
            out.write(secretKey.getEncoded());
            System.out.println("Success to save " + keyFile.getName());
        } finally {
            out.close();
        }

        return resultFileName;
    }

    public static SecretKey loadKeyFile(String fileName) throws Exception {
        /* Load secret key from file */
        InputStream in = new BufferedInputStream(new FileInputStream(fileName));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
//        List<Byte> list = new ArrayList<Byte>();

        int read = 0;
        try {
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
//                for (int i = 0; i < read; i++) {
//                    list.add(buffer[i]);
//                }
            }
        } finally {
            in.close();
        }

        byte[] loadedKeyDataFromFile = out.toByteArray();
//        for (int i = 0; i < loadedKeyDataFromFile.length; i++) {
//            loadedKeyDataFromFile[i] = list.get(i);
//        }

        StringTokenizer st = new StringTokenizer(fileName, ".");
        st.nextToken();
        String algorithm = st.nextToken();

        return new SecretKeySpec(loadedKeyDataFromFile, algorithm);
    }

}
