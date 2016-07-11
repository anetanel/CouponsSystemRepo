package com.netanel.coupons.app;

import java.time.LocalDate;

public class Test2 {
	public static void main(String[] args) {
	
		LocalDate a = LocalDate.of(2015, 10, 10);
//		LocalDate a = LocalDate.now();
		LocalDate b = LocalDate.now();
//		LocalDate c = LocalDate.now();
		LocalDate c = LocalDate.of(2017, 1, 1);
		
		boolean t = !(!b.isBefore(a) && !b.isAfter(c));
		
		System.out.println(t);
		
	}

}
