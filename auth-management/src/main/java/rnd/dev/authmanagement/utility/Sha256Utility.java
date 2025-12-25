package rnd.dev.authmanagement.utility;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static rnd.dev.authmanagement.constant.SecurityConstants.SHA_256_ALGORITHM;

public class Sha256Utility {

    private Sha256Utility() {

    }

    public static String hash(String input) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(SHA_256_ALGORITHM);
            byte[] encodedHash = messageDigest.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder(2 * encodedHash.length);
            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0'); // prepend 0 if single-digit
                }
                hexString.append(hex); // always append the hex
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
