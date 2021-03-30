package com.xsushirollx.sushibyte;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.xsushirollx.sushibyte.repositories.UserDAO;

@SpringBootApplication
public class SushibyteApplication {
	@Autowired
	private UserDAO u1;
	
	public static void main(String[] args) {
		SpringApplication.run(SushibyteApplication.class, args);
		SushibyteApplication m1 = new SushibyteApplication();
		
		System.out.println(m1.u1.count());
	}

}
