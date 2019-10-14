# JCA
- JCA (Java Cryptography Architecture)
- JCE (Java Cryptography Extension)
- SPI (Service Provider Interface)
- CSP (Cryptography Service Provider)
---
1. SecureRandom
    ```java
    class SecureRandomTest {
        public static void main(String[] args){
            /* Way1 */
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            
            /* Way2 */
            SecureRnadom random2 = new SecureRandom();
        }
    }
    ```
2. KeyGenerator, SecretKeySpec, KeyFactory
    ```java
    import javax.crypto.KeyGenerator;
    
    class KeyGeneratorTest {
       public static void main(String[] args){
           KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
           keyGenerator.init(256);
           SecretKey secretKey = keyGenerator.generateKey();
       }
    }
    ```
3. Cipher
    ```java
    import javax.crypto.Cipher;
    import java.nio.charset.Charset;
    import javax.crypto.KeyGenerator;
   
    class CipherTest {
       public static void main(String[] args){
           KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
           keyGenerator.init(256);
           SecretKey secretKey = keyGenerator.generateKey();
   
           Charset charset = Charset.forName("UTF-8");
           String plainText = "안녕하세요 JCA! Nice to meet you!";
               
           String transformation = "AES/ECB/PKCS5Padding"; // algorithm/op_mode/padding
           
           Cipher cipher = Cipher.getInstance(transformation);
           cipher.init(Cipher.ENCRYPT_MODE, secretKey);
           byte[] encryptedBytes = cipher.doFinal(plainText.getBytes(charset));
   
           cipher.init(Cipher.DECRYPT_MODE, secretKey);
           byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
   
           System.out.println(new String(decryptedBytes, charset));
       }
    }
    ```
