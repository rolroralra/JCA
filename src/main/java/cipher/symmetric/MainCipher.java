package cipher.symmetric;

import cipher.symmetric.engine.SymmetricCipher;
import message.digest.checksum.Checksum;
import util.Bytes;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;
import java.util.Arrays;

public class MainCipher {
    public static void main(String[] args) throws Exception {
        SecretKey secretKey = SymmetricCipher.generateSecretKey(256, "AES");

        File plainFile = new File("plain.txt");
        if (!plainFile.exists()) {
            plainFile.createNewFile();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(plainFile)));
            bw.write("오늘도 별이 바람에 스치운다. How do you think?");
            bw.flush();
            System.out.println("plain.txt CREATE NEW!");
        }

        File encryptedFile = new File("encrypted.txt");
        if (encryptedFile.exists()) {
            encryptedFile.delete();
            encryptedFile.createNewFile();
            System.out.println("encrypted.txt DELETE AND CREATE NEW!");
        }

        File decryptedFile = new File("decrypted.txt");
        if (decryptedFile.exists()) {
            decryptedFile.exists();
            decryptedFile.createNewFile();
            System.out.println("decrypted.txt DELETE AND CREATE NEW!");
        }

        IvParameterSpec ivParameterSpec = null;
        /* File Encryption */
        {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            ivParameterSpec = new IvParameterSpec(cipher.getIV());

            InputStream input = null;
            OutputStream output = null;
            try {
                input = new BufferedInputStream(new FileInputStream(plainFile));
                output = new BufferedOutputStream(new FileOutputStream(encryptedFile));

                int read = 0;
                byte[] buffer = new byte[1024];
                byte[] cipherBuff = null;
                while ((read = input.read(buffer)) != -1) {
                    cipherBuff = cipher.update(buffer, 0, read);
                    if (cipherBuff != null) {
                        output.write(cipherBuff);
                    }
                }

                cipherBuff = cipher.doFinal();
                if (cipherBuff != null) {
                    output.write(cipherBuff);
                }
            } finally {
                input.close();
                output.close();
            }
        }

        /* File Decryption */
        {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
            InputStream input = null;
            OutputStream output = null;
            try {
                input = new BufferedInputStream(new FileInputStream(encryptedFile));
                output = new BufferedOutputStream(new FileOutputStream(decryptedFile));

                int read = 0;
                byte[] buffer = new byte[1024];
                byte[] cipherBuff = null;
                while ((read = input.read(buffer)) != -1) {
                    cipherBuff = cipher.update(buffer, 0, read);
                    if (cipherBuff != null) {
                        output.write(cipherBuff);
                    }
                }

                cipherBuff = cipher.doFinal();
                if (cipherBuff != null) {
                    output.write(cipherBuff);
                }
            } finally {
                input.close();
                output.close();
            }
        }

        System.out.println("plain.txt : " + Bytes.convertBytesToHexString(Checksum.checksum("plain.txt", "SHA1")));
        System.out.println("decrypted.txt : " + Bytes.convertBytesToHexString(Checksum.checksum("decrypted.txt", "SHA1")));
        if (Arrays.equals(Checksum.checksum("plain.txt", "SHA1"), Checksum.checksum("decrypted.txt", "SHA1"))) {
            System.out.println("file encryption and decryption SUCCESS!");
        }
    }
}
