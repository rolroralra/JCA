package message.authentication.code;

import util.Bytes;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import java.nio.charset.Charset;

public class MainMac {
    public static void main(String[] args) throws Exception {
        /* 1. Generate Key */
        KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
        SecretKey key = keyGenerator.generateKey();
        System.out.println(key.getAlgorithm());
        System.out.println(key.getFormat());
        System.out.println(key.getEncoded().length);

        /* 2. MAC Init */
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(key);

        Charset charset = Charset.forName("UTF-8");
        String plainText = "오늘도 별이 바람에 스치운다. How do you think?";

        byte[] macBytes = mac.doFinal(plainText.getBytes(charset));
        System.out.println(Bytes.convertBytesToHexString(macBytes));
    }
}
