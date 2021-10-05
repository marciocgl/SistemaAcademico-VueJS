package edu.ifes.ci.si.les.sa.config;

import java.io.IOException;
import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import edu.ifes.ci.si.les.sa.services._DBService;

@Configuration
@Profile("test")
public class TesteConfig {

	@Autowired
	private _DBService dbService;
	
	@Bean
	public boolean instatiateDatabase() throws ParseException, IOException {
		System.out.println("\nestá em TestConfig\n");
		dbService.instantiateTestDatabase();
		return true;
	}
}
