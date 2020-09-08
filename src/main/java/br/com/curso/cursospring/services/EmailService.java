package br.com.curso.cursospring.services;

import org.springframework.mail.SimpleMailMessage;

import br.com.curso.cursospring.domain.Pedido;

public interface EmailService {
	
	void sendOrderConfirmationEmail(Pedido pedido);
	void sendEmail(SimpleMailMessage mail);

}
