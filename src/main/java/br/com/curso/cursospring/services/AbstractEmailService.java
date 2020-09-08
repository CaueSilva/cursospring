package br.com.curso.cursospring.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import br.com.curso.cursospring.domain.Pedido;

public abstract class AbstractEmailService implements EmailService {
	
	@Value("${default.sender}")
	private String sender;
	
	@Override
	public void sendOrderConfirmationEmail(Pedido pedido) {
		SimpleMailMessage mail = prepareSimpleMailMessageFromPedido(pedido);
		sendEmail(mail);
	}

	protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido pedido) {
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(pedido.getCliente().getEmail());
		mail.setFrom(sender);
		mail.setSubject("Confirmação de Pedido! Número: "+pedido.getId());
		mail.setSentDate(new Date(System.currentTimeMillis()));
		mail.setText(pedido.toString());
		return mail;
	}

}
