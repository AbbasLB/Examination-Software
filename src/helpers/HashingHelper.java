package helpers;

import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class HashingHelper {
	private static final int NB_OF_ITERATIONS = 1000;
	private static final int PASSWORD_OUTPUT_SIZE_IN_BITS = 192;
	private static final int SALT_SIZE_IN_BYTES = 24;
	private static final String HASHING_ALGORITHM = "PBKDF2WithHmacSHA512";
	private static final Base64.Encoder ENCODER = Base64.getEncoder();
	private static final Base64.Decoder DECODER = Base64.getDecoder();

	// return salted password
	private static String hashPassword(String password, byte[] salt) {
		try {
			SecretKeyFactory skf = SecretKeyFactory.getInstance(HASHING_ALGORITHM);
			PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, NB_OF_ITERATIONS,
					PASSWORD_OUTPUT_SIZE_IN_BITS);
			return new String(ENCODER.encode(skf.generateSecret(spec).getEncoded())) + ":"
					+ new String(ENCODER.encode(salt));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String hashNewPassword(String password) {
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[SALT_SIZE_IN_BYTES];
		random.nextBytes(salt);
		return hashPassword(password, salt);
	}

	public static boolean validatePassword(String password, String saltedPassword) {
		return hashPassword(password, DECODER.decode(saltedPassword.split(":")[1])).equals(saltedPassword);
	}
}
