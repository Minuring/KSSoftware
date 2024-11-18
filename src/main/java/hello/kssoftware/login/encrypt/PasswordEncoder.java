package hello.kssoftware.login.encrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class PasswordEncoder {

    private PasswordEncoder() {
    }

    public static final String ALGORITHM = "SHA-256";

    public static String encode(String password, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance(ALGORITHM);
            md.update((password + salt).getBytes());
            byte[] digest = md.digest();

            return bytesToHex(digest);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("알고리즘이 존재하지 않습니다.");
        }
    }

    /**
     * @return 16바이트 새로운 salt String
     */
    public static String getSalt() {
        try {
            SecureRandom random = SecureRandom.getInstanceStrong();
            byte[] bytes = new byte[16];
            random.nextBytes(bytes);
            return bytesToHex(bytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("알고리즘이 존재하지 않습니다.");
        }
    }

    public static boolean matches(String rawPassword, String salt, String encodedPassword) {
        return encode(rawPassword, salt).equals(encodedPassword);
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }
}
