package com.codedsalis.chowdify;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@SpringBootApplication
@RestController
public class ChowdifyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChowdifyApplication.class, args);
	}

}
