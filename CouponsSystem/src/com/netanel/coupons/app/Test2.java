package com.netanel.coupons.app;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class Test2 {
	public static void main(String[] args) throws MalformedURLException, URISyntaxException {
		String image = "icons/vmax.png";
		// Load the directory as a resource
		//URL u = ClassLoader.getSystemResource(image);
		System.out.println(new File(image).exists());
//		if (ClassLoader.getSystemResource(image) == null) {
//			System.out.println("No");
//		} else {
//			System.out.println("yes");
//		}
		// Turn the resource into a File object
		//File f = new File(dir_url.toURI());
		// List the directory
		//System.out.println(f.exists());
	}
	

}
