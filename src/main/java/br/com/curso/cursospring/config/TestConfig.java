package br.com.curso.cursospring.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.curso.cursospring.services.DBService;
import br.com.curso.cursospring.services.EmailService;
import br.com.curso.cursospring.services.MockEmailService;

@Configuration
@Profile("test")
public class TestConfig {
	
	@Autowired
	private DBService dbService;
	
	@Bean
	public boolean instantiateDatabase() throws ParseException {
		dbService.instantiateTestDatabase();
		return true;
	}
	
	//O profile de testes irá usar o Bean do MockMailService
	@Bean
	public EmailService emailService() {
		return new MockEmailService();
	}
	
}