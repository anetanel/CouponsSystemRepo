package com.netanel.coupons.crypt;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Map;

public class TestPassword {
	public static void main(String[] args) throws NoSuchAlgorithmException {
		char[] password = "mypassword".toCharArray();
		Map<String, String> hashMap = PasswordHash.hashPassword(password);
		
		for (Map.Entry<String, String> entry : hashMap.entrySet()) {
		    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
		}
		
		System.out.println("*******************");

		char[] password2 = "mypassword".toCharArray();
//		System.out.println(PasswordHash.passwordMatches(hashMap.get("salt"), hashMap.get("hash"), password2));
		System.out.println(PasswordHash.passwordMatches(hashMap, password2));
	}
}
