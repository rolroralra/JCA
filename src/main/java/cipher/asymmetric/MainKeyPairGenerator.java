package cipher.asymmetric;

import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class MainKeyPairGenerator {
    public static void main(String[] args) throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(1024);

        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        Key publicKey = keyPair.getPublic();
        Key privateKey = keyPair.getPrivate();

        System.out.println(publicKey.getAlgorithm());
        System.out.println(publicKey.getFormat());
//        System.out.println(Bytes.convertBytesToHexString(publicKey.getEncoded()));
        System.out.println(publicKey.getEncoded().length);


        System.out.println(privateKey.getAlgorithm());
        System.out.println(privateKey.getFormat());
//        System.out.println(Bytes.convertBytesToHexString(privateKey.getEncoded()));
        System.out.println(privateKey.getEncoded().length);


        byte[] publicKeyBytes = publicKey.getEncoded();
        byte[] privateKeyBytes = privateKey.getEncoded();

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(publicKeyBytes));
        PrivateKey privKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes));

        System.out.println(publicKey.equals(pubKey));
        System.out.println(privateKey.equals(privKey));
    }

    public static KeyPair generateKeyPair(String algorithm, int keyBitSize) throws GeneralSecurityException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(algorithm);
        keyPairGenerator.initialize(keyBitSize);
        return keyPairGenerator.generateKeyPair();
    }
}
