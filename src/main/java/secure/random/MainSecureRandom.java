package secure.random;

import org.bouncycastle.util.encoders.Hex;
import util.Bytes;

import java.security.SecureRandom;

public class MainSecureRandom {
    public static void main(String[] args) throws Exception {
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");

//        random.setSeed(System.currentTimeMillis());

        int randomByteSize = random.nextInt(40) + 1;
        System.out.println("random Byte size : " + randomByteSize);

        byte[] bytes = new byte[randomByteSize];
        random.nextBytes(bytes);

        System.out.println("0x" + Hex.toHexString(bytes));
        System.out.println("0x" + Bytes.convertBytesToHexString(bytes));
    }



}
