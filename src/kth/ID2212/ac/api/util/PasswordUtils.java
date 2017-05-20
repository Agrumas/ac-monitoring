package kth.ID2212.ac.api.util;

import javax.enterprise.context.Dependent;
import java.security.MessageDigest;
import java.util.Base64;

/**
 * Utility to prepare passwords for storing by hashing them.
 */
@Dependent
public class PasswordUtils {

    /**
     * Hashes the plain password using SHA-256
     * @param plainTextPassword plain text password
     * @return SHA-256 Hash of the password
     */
    public static String digestPassword(String plainTextPassword) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(plainTextPassword.getBytes("UTF-8"));
            byte[] passwordDigest = md.digest();
            return new String(Base64.getEncoder().encode(passwordDigest));
        } catch (Exception e) {
            throw new RuntimeException("Exception encoding password", e);
        }
    }
}