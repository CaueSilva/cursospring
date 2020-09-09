package br.com.curso.cursospring.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private Environment env;

	private static final String[] PUBLIC_MATCHES = {
			"/h2-console/**"
	};

	//Endpoints liberados para acesso, somente métodos GET:
	private static final String[] PUBLIC_MATCHES_GET = {
			"/produtos/**",
			"/categorias/**"
	};

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//Habilitando o h2 para o profile de test:
		if(Arrays.asList(env.getActiveProfiles()).contains("test")) {
			http.headers().frameOptions().disable();
		}

		//Chamando o método corsConfigurationSource para aplicar as config. básicas dos endpoints (.cors())
		//e desabilitando a proteção a CSRF (armazenamento de login no escopo de memória da sessão, .and().csrf().disable()):
		http.cors().and().csrf().disable();

		//Permitindo acesso à todos os endpoints declarados em PUBLIC_MATCHES_GET, somente requisições GET (permiteAll()),
		//para os demais endpoints, será necessária autenticação (.anyRequest().authenticated()):
		http.authorizeRequests()
		.antMatchers(HttpMethod.GET, PUBLIC_MATCHES_GET).permitAll()
		.antMatchers(PUBLIC_MATCHES).permitAll()
		.anyRequest().authenticated();

		//Assegurando que o backend não irá criar sessão de usuário:
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); 
	}

	//Método que permite as configurações básicas de todos endpoints ("/**") de múltiplas fontes (.applyPermitDefaultValues()):
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return source;
	}
	
	
	//Método que criptografa senha:
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
