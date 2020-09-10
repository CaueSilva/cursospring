package br.com.curso.cursospring.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
	
	private JWTUtil jwtUtil;
	private UserDetailsService userDetailsService;

	public JWTAuthorizationFilter(AuthenticationManager authenticationManager,
			JWTUtil jwtUtil,
			UserDetailsService userDetailsService) {
		super(authenticationManager);
		this.jwtUtil = jwtUtil;
		this.userDetailsService = userDetailsService;
	}

	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		//Recuperando o token gerado no login através do Header do HttpRequest:
		String header = request.getHeader("Authorization");
		
		//Validando se possui o Header:
		if(header != null && header.startsWith("Bearer ")) {
			UsernamePasswordAuthenticationToken token = getAuthentication(request, header.substring(7));
			//Se o token for válido, autoriza o usuário:
			if(token != null) {
				SecurityContextHolder.getContext().setAuthentication(token);
			}
		}
		
		//Continuando a requisição normalmente após a validação de token:
		chain.doFilter(request, response);
	}

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request, String token) {
		if(jwtUtil.tokenValido(token)) {
			String username = jwtUtil.getUsername(token);
			UserDetails user = userDetailsService.loadUserByUsername(username);
			return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		}
		return null;
	}
	
	
}
