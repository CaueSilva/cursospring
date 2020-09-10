package br.com.curso.cursospring.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtil {
	
	@Value("${jwt.secret}")
	private String security;
	
	@Value("${jwt.expiration}")
	private Long expiration;
		
	public String generateToken(String username) {
		//Usando o JWT para criar um Token de Login para o usuário
		return Jwts.builder().setSubject(username).setExpiration(new Date(System.currentTimeMillis()+expiration)).signWith(SignatureAlgorithm.HS512, security.getBytes()).compact();
	}
}
