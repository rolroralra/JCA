package cipher.asymmetric.engine;

import util.Files;

import java.io.IOException;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.StringTokenizer;

public class AsymmetricCipher {
    public static KeyPair generateKeyPair(String algorithm, int keyBitSize) throws GeneralSecurityException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(algorithm);
        keyPairGenerator.initialize(keyBitSize);
        return keyPairGenerator.generateKeyPair();
    }

    public static String saveKeyFile(Key key, String fileName) throws IOException {
        String resultFileNalme = fileName + "." + key.getAlgorithm() + "." + key.getFormat();
        Files.writeBytes(resultFileNalme, key.getEncoded());
        return resultFileNalme;
    }

    public static PublicKey loadPublicKeyFromFile(String fileName) throws GeneralSecurityException, IOException {
        byte[] publicKeyBytes = Files.readBytes(fileName);

        StringTokenizer st = new StringTokenizer(fileName, ".");
        st.nextToken();
        String algorithm = st.nextToken();
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);

        return keyFactory.generatePublic(new X509EncodedKeySpec(publicKeyBytes));
    }

    public static PrivateKey loadPrivateKeyFromFile(String fileName) throws GeneralSecurityException, IOException {
        byte[] privateKeyBytes = Files.readBytes(fileName);

        StringTokenizer st = new StringTokenizer(fileName, ".");
        st.nextToken();
        String algorithm = st.nextToken();
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);

        return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes));
    }
}
