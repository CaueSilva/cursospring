package br.com.curso.cursospring.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.curso.cursospring.domain.Cliente;
import br.com.curso.cursospring.repositories.ClienteRepository;
import br.com.curso.cursospring.services.exception.ObjectNotFoundException;

@Service
public class AuthService {

	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private EmailService emailService;

	private Random rand = new Random();

	public void sendNewPassword(String email) {
		Cliente cliente = clienteRepository.findByEmail(email);
		if(cliente == null) {
			throw new ObjectNotFoundException("E-mail não encontrado.");
		}
		String newPassword = newPassword();
		cliente.setSenha(bCryptPasswordEncoder.encode(newPassword));
		clienteRepository.save(cliente);
		emailService.sendNewPasswordEmail(cliente, newPassword);
	}

	private String newPassword() {
		char[] vet = new char[10];
		for (int i = 0 ; i < 10 ; i++) {
			vet[i] = randomChar();
		}
		return new String(vet);
	}

	private char randomChar() {
		int opcao = rand.nextInt(3);
		//Se opcao = 0, retorna digito:
		if(opcao == 0) {
			return (char) (rand.nextInt(10) + 48); 
		}
		//Se opcao = 1, retorna letra maiúscula:
		else if(opcao == 1) {
			return (char) (rand.nextInt(26) + 65);
		}
		//Se opcao = 2, retorna letra minúscula:
		else {
			return (char) (rand.nextInt(26) + 97);
		}
	}

}
