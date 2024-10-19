package us.takiyo.managers;

import java.util.Base64;

public class EncryptionManager {
    public static String Encrypt(String value) {
        byte[] encodedBytes = Base64.getEncoder().encode(value.getBytes());
        return new String(encodedBytes);
    }
}
