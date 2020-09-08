package br.com.curso.cursospring.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.curso.cursospring.services.DBService;
import br.com.curso.cursospring.services.EmailService;
import br.com.curso.cursospring.services.SmtpEmailService;

@Configuration
@Profile("dev")
public class DevConfig {
	
	@Autowired
	private DBService dbService;
	
	//Recuperando o valor de spring.jpa.hibernate.ddl-auto que cria/atualiza/dropa o BD na variável strategy
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;
	
	@Bean
	public boolean instantiateDatabase() throws ParseException {
		//Se o banco de dados já foi criado e preenchido, retorna false
		if(!strategy.equals("create")) {
			return false;
		}
		dbService.instantiateTestDatabase();
		return true;
	}
	
	//O profile de desenvolvimento irá usar o Bean do SmtpEmailService
	@Bean
	public EmailService emailService() {
		return new SmtpEmailService();
	}
	
}