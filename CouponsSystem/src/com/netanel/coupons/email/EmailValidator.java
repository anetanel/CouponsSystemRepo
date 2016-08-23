package com.netanel.coupons.email;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Email Address Syntax Validator Class.
 */
public class EmailValidator {

	// Valid email address regex
	private static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
			Pattern.CASE_INSENSITIVE);

	/**
	 * Validates an email address syntax
	 * @param emailStr a {@code String} email address to be validated.
	 * @return {@code true} if is a valid email address syntax, {@code false} otherwise.
	 */
	public static boolean validate(String emailStr) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
		return matcher.find();
	}
}
