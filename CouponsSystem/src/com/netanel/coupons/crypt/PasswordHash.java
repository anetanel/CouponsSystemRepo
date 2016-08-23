package com.netanel.coupons.crypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.DatatypeConverter;

/**
 * This class provides static functions that handles password encryption.
 * <p>
 * For better security, a password is never stored as plain text.
 * Rather, first the text is converted to an array of bytes. Than, a "salt" is added to the array.
 * The "salt" is a 32 bit SecureRandom array.
 * The combined password and salt is hashed using a one-way hashing algorithm (such as SHA-256) and converted to hexadecimal String.
 * The combined hash, along with the salt is needed to verify a password
 * @author Netanel Attali
 * @see <a href="https://crackstation.net/hashing-security.html"> Salted Password Hashing - Doing it Right </a>
 */
final public class PasswordHash {

	/**
	 * Hashes a password and returns a map containing the salted hash and the "salt" used.
	 * @param password	- A char array 
	 * @return	A map <String, String> with keys "salt" and "hash", and values of the salt and password hash in hex. 		
	 */
	static Map<String, String> hashPassword(char[] password) {
		byte[] salt = generateSalt();
		return hashPassword(password, salt);
	}
	
	/**
	 * Checks if the given password matches the hash and salt.
	 * The original password should be kept in the database along with its salt.
	 * @param saltHexStr - The salt as a hex String 
	 * @param hashHexStr - The password hash as a hex String
	 * @param password - A char array
	 * @return {@code true} if the given password matched the hash and salt, {@code false} otherwise
	 */
	public static boolean passwordMatches(String saltHexStr, String hashHexStr, char[] password) {
		byte[] salt = hexStringToByteArray(saltHexStr);
		
		return hashHexStr.equals(hashPassword(password, salt).get("hash"));
	}
	
	
	/**
	 * Checks if the given password matches the hash and salt.
	 * The original password should be kept in the database along with its salt.
	 * @param saltAndHash - A map <String, String> with keys "salt" and "hash", and values of the salt and password hash in hex. 
	 * @param password - A char array
	 * @return {@code true} if the given password matched the hash and salt, {@code false} otherwise
	 */
	public static boolean passwordMatches(Map<String, String> saltAndHash, char[] password) {
		
		return passwordMatches(saltAndHash.get("salt"), saltAndHash.get("hash"), password);
	}
	
	// Hashes a password with a given salt, and returns a map containing the salted hash and the salt used.
	private static Map<String, String> hashPassword(char[] password, byte[] salt) {		
		// Convert password to byte array
		byte[] passByte = charToBytesASCII(password);
		byte[] combine = new byte[salt.length + passByte.length];
		// combine salt and password
		System.arraycopy(salt, 0, combine, 0, salt.length);
		System.arraycopy(passByte, 0, combine, salt.length, passByte.length);

		// Hash the salted password 
		MessageDigest md;
		Map<String, String> saltAndHash = null;
		try {
			md = MessageDigest.getInstance("SHA-256");
			md.update(combine);
			saltAndHash = new HashMap<>();
			saltAndHash.put("salt", byteArrayToHexString(salt));
			saltAndHash.put("hash", byteArrayToHexString(md.digest()));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		return saltAndHash;
	}

	// Generated a secure random 32-bit byte array ("salt").
	private static byte[] generateSalt() {
		SecureRandom sr = new SecureRandom();
		byte[] salt = new byte[32];
		sr.nextBytes(salt);
		return salt;
	}

	// Converts char array to byte array
	private static byte[] charToBytesASCII(char[] str) {
		byte[] b = new byte[str.length];
		for (int i = 0; i < b.length; i++) {
			b[i] = (byte) str[i];
		}
		return b;
	}

	// Returns a hex string representation of a byte array.
	private static String byteArrayToHexString(byte[] byteArr) {
		return DatatypeConverter.printHexBinary(byteArr);
	}

	// Returns a byte array from a hex string created by byteArrayToHexString.
	private static byte[] hexStringToByteArray(String s) {
		return DatatypeConverter.parseHexBinary(s);
	}
}
