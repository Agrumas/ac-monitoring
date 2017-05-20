package kth.ID2212.ac.api.util;

import java.security.Key;

/**
 * Key generator to ensure validity of JWT
 */
public interface KeyGenerator {
    /**
     * Generates Key to ensure validity of JWT
     * @return Key
     */
    Key generateKey();
}
