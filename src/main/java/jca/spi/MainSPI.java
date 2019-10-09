package jca.spi;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Provider;
import java.security.Security;

public class MainSPI {

    public static void main(String[] args) {
        Provider bcProvider = new BouncyCastleProvider();
        Security.addProvider(bcProvider);

        Provider provider = Security.getProvider("BC");
        if (provider != null) {
            System.out.println(provider.getName());
            System.out.println(provider.getInfo());
//            System.out.println(provider.getServices());
        }

        if (provider == bcProvider) {
            System.out.println("Provider Object is singleton!");
        }
    }
}
