package cipher.asymmetric;

import util.Bytes;
import util.Files;

import javax.crypto.Cipher;
import java.io.File;
import java.nio.charset.Charset;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class MainCipherRSA {

    static {
//        Provider provider = new BouncyCastleProvider();
//        Security.addProvider(provider);
//
//        for (String algo : Security.getAlgorithms("Cipher")) {
//            if (algo.startsWith("RSA")) {
//                System.out.println(algo);
//            }
//        }
//
//        for (Provider.Service service : provider.getServices()) {
//            if (service.getType().startsWith("Cipher") && service.getAlgorithm().contains("RSA")) {
//                System.out.println(service.getClassName());
//                System.out.println(service.getAlgorithm());
//                System.out.println(service.getType());
//            }
//        }
    }

    public static void main(String[] args) throws Exception {
        String publicKeyFileName = "public.key";
        String privateKeyFileName = "private.key";
        File publicKeyFile = new File(publicKeyFileName);
        File privateKeyFile = new File(privateKeyFileName);
        PublicKey publicKey = null;
        PrivateKey privateKey = null;

        if (publicKeyFile.exists() && privateKeyFile.exists()) {
            byte[] publicKeyBytes = Files.readBytes(publicKeyFileName);
            byte[] privateKeyBytes = Files.readBytes(privateKeyFileName);

            System.out.println("publicKeyBytes:" + publicKeyBytes.length);
            System.out.println("privateKeyBytes:" + privateKeyBytes.length);

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(publicKeyBytes));
            privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes));
        } else {
            KeyPair keyPair = MainKeyPairGenerator.generateKeyPair("RSA", 1024);
            publicKey = keyPair.getPublic();
            privateKey = keyPair.getPrivate();

            Files.writeBytes(publicKeyFileName, publicKey.getEncoded());
            Files.writeBytes(privateKeyFileName, privateKey.getEncoded());
        }

        Charset charset = Charset.forName("UTF-8");
        String plainText = "오늘도 별이 바람에 스치운다. How do you think?";

        byte[] encryptedBytes = encryptRSA(publicKey, plainText.getBytes(charset));
        System.out.println(Bytes.convertBytesToHexString(encryptedBytes));

        byte[] decryptedBytes = decryptRSA(privateKey, encryptedBytes);
        System.out.println(new String(decryptedBytes, charset));


        encryptedBytes = encryptRSAOAEP(publicKey, plainText.getBytes(charset));
        System.out.println(Bytes.convertBytesToHexString(encryptedBytes));

        decryptedBytes = decryptRSAOAEP(privateKey, encryptedBytes);
        System.out.println(new String(decryptedBytes, charset));
    }

    public static byte[] encryptRSA(PublicKey publicKey, byte[] plainBytes) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(plainBytes);
    }

    public static byte[] decryptRSA(PrivateKey privateKey, byte[] encryptedBytes) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(encryptedBytes);
    }

    public static byte[] encryptRSAOAEP(PublicKey publicKey, byte[] plainBytes) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(plainBytes);
    }

    public static byte[] decryptRSAOAEP(PrivateKey privateKey, byte[] encryptedBytes) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(encryptedBytes);
    }
}
