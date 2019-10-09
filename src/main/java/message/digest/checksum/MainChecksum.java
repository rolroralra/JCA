package message.digest.checksum;

import util.Bytes;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;

public class MainChecksum {
    public static void main(String[] args) throws Exception {
        File file = new File("C:\\Users\\Shinyoung.Kim\\IdeaProjects\\JCA\\src\\main\\resources\\putty.zip");
        if (!file.exists()) {
            System.out.println("File not exists!");
            return;
        }

        MessageDigest messageDigest = MessageDigest.getInstance("MD5");

        /* Way 1. Use just InputStream / BufferedInputStream / FileInputStream */
//        InputStream input = new BufferedInputStream(new FileInputStream(file));
//        try {
//            byte[] buffer = new byte[1024];
//            int read = -1;
//
//            while ((read = input.read(buffer)) != -1) {
//                messageDigest.update(buffer, 0 , read);
//            }
//        } finally {
//            input.close();
//        }

        /* Way 2. Use DigestInputStream */
        InputStream input = new DigestInputStream(new BufferedInputStream(new FileInputStream(file)), messageDigest);
        try {
            while (input.read() != -1);
        } finally {
            input.close();
        }


        /* How to md5 of some file in Window 10?
        *  $ CertUtil -hashfile [filePath] MD5
        *
        * - Example
        * $ certutil.exe -hashfile .\putty.zip MD5
        * MD5의 .\putty.zip 해시:
        * 3e4ada166060928f7e76a1f63c1f631e
        * CertUtil: -hashfile 명령이 성공적으로 완료되었습니다.
        * */
        String md5 = "3e4ada166060928f7e76a1f63c1f631e";
        byte[] hashBytes = messageDigest.digest();
        String hashHexString = Bytes.convertBytesToHexString(hashBytes);

        System.out.println("MD5: " + md5);
        System.out.println("Hash Result: " + hashHexString);

        if (md5.equalsIgnoreCase(hashHexString)) {
            System.out.println("MD5 Hash Result SUCCESS!");
        }
    }
}
