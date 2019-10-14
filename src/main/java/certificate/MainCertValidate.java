package certificate;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.*;
import java.security.GeneralSecurityException;
import java.security.Security;
import java.security.cert.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainCertValidate {
    public static void main(String[] args) throws Exception {
        Security.addProvider(new BouncyCastleProvider());

        X509Certificate cert = loadCertificateFromFile("yessign.der");

        X509Certificate trust = loadCertificateFromFile("root-rsa-sha2.der");

        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        List<X509Certificate> certList = new ArrayList<X509Certificate>();
        certList.add(cert);

        CertPath certPath =  certificateFactory.generateCertPath(certList);
        TrustAnchor trustAnchor = new TrustAnchor(trust, null);
        PKIXParameters params = new PKIXParameters(Collections.singleton(trustAnchor));
        params.setRevocationEnabled(false);
        CertPathValidator certPathValidator = CertPathValidator.getInstance("PKIX", "BC");
        PKIXCertPathValidatorResult result = null;

        try {
            result = (PKIXCertPathValidatorResult) certPathValidator.validate(certPath, params);
        } catch (CertPathValidatorException e) {
            System.out.println("Not Valid Certification File");
            e.printStackTrace();
        }

        System.out.println(result);
        System.out.println("Certification File is Valid!");
    }

    public static X509Certificate loadCertificateFromFile(String fileName) throws IOException, GeneralSecurityException {
        InputStream input = new BufferedInputStream(new FileInputStream(new File(fileName)));
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");

        X509Certificate cert = null;
        try {
            cert = (X509Certificate) certificateFactory.generateCertificate(input);
        } finally {
            input.close();
        }

        return cert;
    }
}
