package message.digest.password;

import util.Bytes;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainPassword {
    public static void main(String[] args) {
        String password = "helloworld12#";
        String digest = password(password);
        System.out.println("Password Hash Value : " + digest);
    }

    public static byte[] getHash(byte[] input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA1");
            return md.digest(input);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA1 Algorithm Not Supported!", e);
        }
    }

    public static String password(byte[] input) {
        byte[] digest = null;

        // Stage 1
        digest = getHash(input);

        // Stage 2
        digest = getHash(digest);

        StringBuilder sb = new StringBuilder(digest.length * 2 + 1);
        sb.append("*").append(Bytes.convertBytesToHexString(digest).toUpperCase());
        return sb.toString();
    }

    public static String password(String password) {
        if (password == null) {
            return null;
        }

        return password(password.getBytes());
    }
}
