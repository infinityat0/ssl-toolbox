package com.jmpeax.ssltoolbox.svc;

import com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.annotations.NotNull;

import javax.security.auth.x500.X500Principal;
import java.io.InputStream;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * The CertificateHelper class provides utility methods for working with X.509 certificates.
 * <p>
 * This class contains the following methods:
 * <p>
 * 1. getCertificate - This static method takes an InputStream containing a certificate and returns a Set of X509Certificates. It uses the X.509 CertificateFactory to generate the
 *  certificates from the input stream.
 * <p>
 * 2. getCommonName - This static method takes an X509Certificate and returns the Common Name (CN) from the subject of the certificate. It uses regular expressions to extract the
 *  CN from the subject's name.
 * <p>
 * 3. isValid - This static method takes an X509Certificate and checks if it is valid. It uses the checkValidity() method of the certificate to perform the validation.
 * <p>
 * Please note that this class relies on a logger instance from the Logger class for logging purposes.
 */
public class CertificateHelper {
    private static final Logger LOGGER = Logger.getInstance(CertificateHelper.class);


    /**
     * Retrieves a set of X.509 certificates from the given input stream.
     *
     * @param certificateInput the input stream from which to retrieve the certificates
     * @return a set of X.509 certificates obtained from the input stream
     */
    public static Set<X509Certificate> getCertificate(InputStream certificateInput){
        try {
            var fac = CertificateFactory.getInstance("X.509");
            return  fac.generateCertificates(certificateInput).stream()
                    .map(c -> (X509Certificate) c)
                    .collect(Collectors.toCollection(LinkedHashSet::new));
        } catch (CertificateException e) {
            LOGGER.error("Unable to create Certification Factory",e);
        }
        return Set.of();
    }

    /**
     * Retrieves the Common Name (CN) from the subject of the given X509Certificate.
     *
     * @param certificate the X509Certificate from which to retrieve the Common Name
     * @return the Common Name (CN) extracted from the subject of the certificate
     */
    @NotNull
    public static String  getCommonName(@NotNull X509Certificate certificate) {
        X500Principal principal = certificate.getSubjectX500Principal();
        String subject = principal.getName();
        Pattern pattern = Pattern.compile("CN=([^,]*)");
        Matcher matcher = pattern.matcher(subject);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "";
    }

    /**
     * Checks if the given X509Certificate is valid.
     *
     * @param certificate the X509Certificate to be checked for validity
     * @return true if the certificate is valid, false otherwise
     */
    public static boolean isValid(@NotNull X509Certificate certificate) {
        try {
            certificate.checkValidity();
            return true;
        } catch (Exception e) {
            LOGGER.debug("Certificate is not valid", e);
        }
        return false;
    }
}
