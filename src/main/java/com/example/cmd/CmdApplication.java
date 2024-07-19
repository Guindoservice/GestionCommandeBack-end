package com.example.cmd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class CmdApplication {

	public static void main(String[] args) {
		SpringApplication.run(CmdApplication.class, args);
	}

}
