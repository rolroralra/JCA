package message.digest.checksum;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;

public class Checksum {
    public static byte[] checksum(String fileName, String algorithm) throws Exception {
        MessageDigest md = MessageDigest.getInstance(algorithm);
        DigestInputStream digestInput = new DigestInputStream(new BufferedInputStream(new FileInputStream(fileName)), md);
        while (digestInput.read() != -1);

        return md.digest();
    }
}
