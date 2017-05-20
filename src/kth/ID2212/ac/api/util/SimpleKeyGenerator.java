package kth.ID2212.ac.api.util;

import javax.crypto.spec.SecretKeySpec;
import javax.enterprise.context.Dependent;
import java.security.Key;

/**
 * Key Generator
 */
@Dependent
public class SimpleKeyGenerator implements KeyGenerator {
    /**
     * Generates Security key using DES
     * @return Security key
     */
    @Override
    public Key generateKey() {
        String keyString = "id2212";
        Key key = new SecretKeySpec(keyString.getBytes(), 0, keyString.getBytes().length, "DES");
        return key;
    }
}