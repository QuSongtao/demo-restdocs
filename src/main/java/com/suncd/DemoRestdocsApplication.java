package com.suncd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 描述 Spring RestDocs
 *
 * @author qust
 * @version 1.0.0 2017-05-17
 */
@SpringBootApplication //<1>
public class DemoRestdocsApplication {

	public static void main(String[] args) {
		String str = "cplgx";
		SpringApplication.run(DemoRestdocsApplication.class, args);
	}
}
