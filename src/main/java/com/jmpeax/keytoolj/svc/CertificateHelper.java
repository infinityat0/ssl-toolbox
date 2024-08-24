package com.jmpeax.keytoolj.svc;

import com.intellij.openapi.diagnostic.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Optional;

public class CertificateHelper {
    private static final Logger LOGGER = Logger.getInstance(CertificateHelper.class);


    public static Optional<X509Certificate> getCertificate(InputStream certificateInput){
        try {
            var fac = CertificateFactory.getInstance("X.509");
            return Optional.of((X509Certificate)fac.generateCertificate(certificateInput));
        } catch (CertificateException e) {
            LOGGER.error("Unable to create Certification Factory",e);
        }
        return Optional.empty();
    }
}
