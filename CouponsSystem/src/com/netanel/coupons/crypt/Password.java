package com.netanel.coupons.crypt;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class Password {
	//
	// Attributes
	//
	private Map<String, String> hashAndSalt = new HashMap<>(); 
	
	//
	// Constructors
	//
	public Password(char[] password) throws NoSuchAlgorithmException {
		setNewPassword(password);
	}
	
	public Password(String hash, String salt) {
		hashAndSalt.put("hash", hash);
		hashAndSalt.put("salt", salt);
	}
	
	//
	// Functions
	//
	public Map<String, String> getHashAndSalt() {
		return hashAndSalt;
	}
	
	public void setNewPassword(char[] password) throws NoSuchAlgorithmException {
		hashAndSalt = PasswordHash.hashPassword(password);
	}

	@Override
	public String toString() {
		return "Password [hashAndSalt=" + hashAndSalt + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((hashAndSalt == null) ? 0 : hashAndSalt.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Password other = (Password) obj;
		if (hashAndSalt == null) {
			if (other.hashAndSalt != null) {
				return false;
			}
		} else if (!hashAndSalt.equals(other.hashAndSalt)) {
			return false;
		}
		return true;
	}

	
	
}
