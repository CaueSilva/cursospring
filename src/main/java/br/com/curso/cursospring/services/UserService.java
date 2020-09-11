package br.com.curso.cursospring.services;

import org.springframework.security.core.context.SecurityContextHolder;

import br.com.curso.cursospring.security.UserSS;

public class UserService {

	//Retornando o usuário que está logado:
	public static UserSS authenticated() {
		try {
			return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch(Exception e) {
			//Se não tiver ninguém logado, dará exceção:
			e.printStackTrace();
			return null;
		}
	}

}
