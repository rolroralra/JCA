package signature;

import cipher.asymmetric.engine.AsymmetricCipher;
import util.Bytes;

import java.io.File;
import java.nio.charset.Charset;
import java.security.*;

public class MainSignature {
    public static void main(String[] args) throws Exception {
        String publicKeyFileName = "public_key";
        String privateKeyFileName = "private_key";
        PublicKey publicKey = null;
        PrivateKey privateKey = null;
        File publicKeyFile = new File(publicKeyFileName + ".RSA.X.509");
        File privateKeyFile = new File(privateKeyFileName + ".RSA.PKCS#8");

        if (publicKeyFile.exists() && privateKeyFile.exists()) {
            publicKey = AsymmetricCipher.loadPublicKeyFromFile(publicKeyFileName + ".RSA.X.509");
            privateKey = AsymmetricCipher.loadPrivateKeyFromFile(privateKeyFileName + ".RSA.PKCS#8");
        }
        else {
            KeyPair keyPair = AsymmetricCipher.generateKeyPair("RSA", 1024);
            publicKey = keyPair.getPublic();
            privateKey = keyPair.getPrivate();
            AsymmetricCipher.saveKeyFile(publicKey, "public_key");
            AsymmetricCipher.saveKeyFile(privateKey, "private_key");
        }

        Charset charset = Charset.forName("UTF-8");
        String plainText = "오늘도 별이 바람에 스치운다. How do you think?";

        /* Signature */
        byte[] signatureBytes = sign(privateKey, plainText.getBytes(charset));
        System.out.println(Bytes.convertBytesToHexString(signatureBytes));
        System.out.println(signatureBytes.length);

        /* Verification */
        boolean verified = verify(publicKey, signatureBytes, plainText.getBytes(charset));
        System.out.println("verified : " + verified);

    }

    public static byte[] sign(PrivateKey privateKey, byte[] plainBytes) throws GeneralSecurityException {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        signature.update(plainBytes);
        return signature.sign();
    }

    public static boolean verify(PublicKey publicKey, byte[] signatureBytes, byte[] plainBytes) throws GeneralSecurityException {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initVerify(publicKey);
        signature.update(plainBytes);
        return signature.verify(signatureBytes);
    }
}
