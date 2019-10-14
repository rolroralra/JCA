package util;

import java.io.*;

public class Files {
    public static void writeBytes(String fileName, byte[] inputBytes) throws IOException {
        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();

        OutputStream output = new BufferedOutputStream(new FileOutputStream(file));

        try {
            output.write(inputBytes);
        } finally {
            output.close();
        }
    }

    public static byte[] readBytes(String fileName) throws IOException {
        byte[] bytes = null;
        InputStream input = null;
        ByteArrayOutputStream output = null;

        try {
            input = new BufferedInputStream(new FileInputStream(new File(fileName)));
            output = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024];
            int read = -1;
            while ( (read = input.read(buffer)) != -1) {
                output.write(buffer, 0, read);
            }
            bytes = output.toByteArray();

        } finally {
            input.close();
            output.close();
        }

        return bytes;
    }
}
