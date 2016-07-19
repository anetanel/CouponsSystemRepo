package com.netanel.coupons.app;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test2 {
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
			Pattern.CASE_INSENSITIVE);

	public static boolean validate(String emailStr) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
		return matcher.find();
	}

	public static void main(String[] args) throws InterruptedException {
		System.out.println(validate("netanel.attali@gmail.com.il"));
	}

}
