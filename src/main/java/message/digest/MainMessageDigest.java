package message.digest;

import util.Bytes;

import java.nio.charset.Charset;
import java.security.MessageDigest;

public class MainMessageDigest {
    public static void main(String[] args) throws Exception {
        Charset charset = Charset.forName("UTF-8");
        String plainText = "오늘도 별이 바람에 스치운다. How do you think?";

        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(plainText.getBytes(charset));

        byte[] hashBytes = messageDigest.digest();
        System.out.println(Bytes.convertBytesToHexString(hashBytes));

        System.out.println(Bytes.convertBytesToHexString(messageDigest.digest(plainText.getBytes(charset))));

    }
}
