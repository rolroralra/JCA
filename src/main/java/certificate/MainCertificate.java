package certificate;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public class MainCertificate {
    public static void main(String[] args) throws Exception {
        File certFile = new File("yessign.der");

        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        X509Certificate cert = null;
        InputStream input = new BufferedInputStream(new FileInputStream(certFile));
        try {
            cert = (X509Certificate) certificateFactory.generateCertificate(input);
        } finally {
            input.close();
        }

        System.out.println(cert);
    }
}
