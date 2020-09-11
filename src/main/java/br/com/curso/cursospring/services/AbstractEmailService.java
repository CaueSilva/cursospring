package br.com.curso.cursospring.services;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import br.com.curso.cursospring.domain.Cliente;
import br.com.curso.cursospring.domain.Pedido;

public abstract class AbstractEmailService implements EmailService {
	
	@Value("${default.sender}")
	private String sender;
	
	@Autowired
	private TemplateEngine templateEngine;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
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
	
	//Processamento do HTML (populando o html com as informações do pedido)
	protected String htmlFromTemplatePedido(Pedido obj) {
		Context context = new Context();
		context.setVariable("pedido", obj);
		return templateEngine.process("email/confirmacaoPedido", context);
	}
	
	@Override
	public void sendOrderConfirmationHtmlEmail(Pedido obj) {
		try {
			MimeMessage mimeMessage = prepareMimeMessageFromPedido(obj);
			sendHtmlEmail(mimeMessage);
		} catch (MessagingException e) {
			e.printStackTrace();
			sendOrderConfirmationEmail(obj);
		}
	}
	
	protected MimeMessage prepareMimeMessageFromPedido(Pedido pedido) throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
		mimeMessageHelper.setTo(pedido.getCliente().getEmail());
		mimeMessageHelper.setFrom(sender);
		mimeMessageHelper.setSubject("Pedido confirmado! Código: "+pedido.getId());
		mimeMessageHelper.setSentDate(new Date(System.currentTimeMillis()));
		mimeMessageHelper.setText(htmlFromTemplatePedido(pedido), true);
		return mimeMessage;
	}
	
	@Override
	public void sendNewPasswordEmail(Cliente cliente, String newPassword) {
		SimpleMailMessage mail = prepareNewPasswordEmail(cliente,newPassword);
		sendEmail(mail);
	}

	protected SimpleMailMessage prepareNewPasswordEmail(Cliente cliente, String newPassword) {
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(cliente.getEmail());
		mail.setFrom(sender);
		mail.setSubject("Solicitação de nova senha.");
		mail.setSentDate(new Date(System.currentTimeMillis()));
		mail.setText("Nova senha: "+newPassword);
		return mail;
	}

}
