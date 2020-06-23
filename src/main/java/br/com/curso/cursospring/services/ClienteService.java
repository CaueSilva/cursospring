package br.com.curso.cursospring.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.curso.cursospring.domain.Cliente;
import br.com.curso.cursospring.repositories.ClienteRepository;
import br.com.curso.cursospring.services.exception.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired //instância automática do Spring
	private ClienteRepository repo;
	
	public Cliente find(Integer id) {
		Optional<Cliente> cliente = repo.findById(id);
		return cliente.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado. Id: "+id+", tipo: "+Cliente.class.getName()));
	}
	
}
