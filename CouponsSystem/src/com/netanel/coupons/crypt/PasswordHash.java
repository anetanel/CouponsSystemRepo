package com.netanel.coupons.crypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class PasswordHash {
	
	public static String[] hashPassword(char[] password, byte[] salt) throws NoSuchAlgorithmException {
		byte[] passByte = charToBytesASCII(password);
		byte[] combine = new byte[salt.length + passByte.length];
		// combine salt and password
		System.arraycopy(salt, 0, combine, 0, salt.length);
		System.arraycopy(passByte, 0, combine, salt.length, passByte.length);

		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(combine);
		String[] saltAndHash = { byteArrayToHexString(salt), byteArrayToHexString(md.digest()) };
		return saltAndHash;
	}
	
	public static String[] hashPassword(char[] password) throws NoSuchAlgorithmException {
		byte[] salt = getSalt();
		return hashPassword(password, salt);
	}
	
	private static byte[] getSalt() {
		SecureRandom sr = new SecureRandom();
		byte[] salt = new byte[32];
		sr.nextBytes(salt);
		return salt;
	}

	public static byte[] stringToBytesASCII(String str) {
		byte[] b = new byte[str.length()];
		for (int i = 0; i < b.length; i++) {
			b[i] = (byte) str.charAt(i);
		}
		return b;
	}

	public static byte[] charToBytesASCII(char[] str) {
		byte[] b = new byte[str.length];
		for (int i = 0; i < b.length; i++) {
			b[i] = (byte) str[i];
		}
		return b;
	}
	
	public static char[] BytesToCharASCII(byte[] b) {
		char[] str = new char[b.length];
		for (int i = 0; i < str.length; i++) {
			str[i] = (char) b[i];
		}
		return str;
	}
	
	public  static String byteArrayToHexString(byte[] byteArr) {
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < byteArr.length; i++) {
			String hex = Integer.toHexString(0xff & byteArr[i]);
			if (hex.length() == 1)
				hexString.append('0');
			hexString.append(hex);
		}
		return hexString.toString();
	}
	public static byte[] hexStringToByteArray(String s) {
	    int len = s.length();
	    byte[] data = new byte[len / 2];
	    for (int i = 0; i < len; i += 2) {
	        data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
	                             + Character.digit(s.charAt(i+1), 16));
	    }
	    return data;
	}
}
