package br.com.curso.cursospring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.curso.cursospring.domain.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
	
	//Validando e-mail único no banco
	//Se o método findBy possuir, no final, o nome de algum atributo do objeto (atributo email do Cliente, por exemplo), o Spring Data irá
	//implementar o método de busca automaticamente
	@Transactional(readOnly=true)
	Cliente findByEmail(String email);
	
}
