package com.netanel.coupons.crypt;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Secure password object.
 *
 */
@XmlRootElement
public class Password {
	//
	// Attributes
	//
	private Map<String, String> hashAndSalt = new HashMap<>();
	
	//
	// Constructors
	//
	public Password(char[] password) {
		setNewPassword(password);
	}
	
	public Password(String hash, String salt) {
		hashAndSalt.put("hash", hash);
		hashAndSalt.put("salt", salt);
	}
	
	//
	// Functions
	//
	/**
	 * Get password hash and salt.
	 * @return A {@code map <String, String>} with keys "salt" and "hash", and values of the salt and password hash in hex.
	 */
	public Map<String, String> getHashAndSalt() {
		return hashAndSalt;
	}
	
	/**
	 * Sets a new secure password.
	 * @param password a {@code char[]} with the new password.
	 */
	public void setNewPassword(char[] password) {
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
