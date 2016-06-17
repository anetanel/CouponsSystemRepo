package com.netanel.coupons.crypt;

import java.security.NoSuchAlgorithmException;

public class TestPassword {
	public static void main(String[] args) throws NoSuchAlgorithmException {
		char[] password = "mypassword".toCharArray();
		String[] hash = PasswordHash.hashPassword(password);
		for (String string : hash) {
			System.out.println(string);
		}
		
		System.out.println("*******************");
		byte[] salt = PasswordHash.hexStringToByteArray(hash[0]);
		String[] hash2 = PasswordHash.hashPassword(password, salt);
		for (String string : hash2) {
			System.out.println(string);
		}
		
//		char[] c1 = "this is a test".toCharArray();
//		byte[] b1 = PasswordHash.charToBytesASCII(c1);
//		for (byte b : b1) {
//			System.out.print(b + " ");
//		}
//		System.out.println();
//		char[] c2 = PasswordHash.BytesToCharASCII(b1);
//		for (int i = 0; i < c2.length; i++) {
//			System.out.print(c2[i]);
//		}
//		System.out.println("\n*******************");
//
//		
//		String hex = PasswordHash.byteToHex(b1);
//		System.out.println(hex);
//		byte[] b2 = PasswordHash.hexStringToByteArray(hex);
//		byte[] b2 = PasswordHash.stringToBytesASCII(hex);
//		for (byte b : b2) {
//			System.out.print(b + " ");
//		}
		
		
	}
}
