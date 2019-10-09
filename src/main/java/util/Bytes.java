package util;

public class Bytes {
    private static final char[] HEX_ARRAY = "0123456789abcdef".toCharArray();
    public static String convertBytesToHexString(byte[] bytes) {
        char[] hexCharArray = new char[bytes.length * 2];

        for (int i = 0; i < bytes.length; i++) {
            short hexPart = (short) (bytes[i] & 0xFF);
//            System.out.println("hexPart = " + hexPart);
////            System.out.println("hexPart >>> 4 = " + (hexPart >>> 4));
////            System.out.println("hexPart & 0x0F = " + (hexPart & 0x0F));
            hexCharArray[i * 2] = HEX_ARRAY[hexPart >>> 4];
            hexCharArray[i * 2 + 1] = HEX_ARRAY[hexPart & 0x0F];
        }

        return new String(hexCharArray);
    }
}
