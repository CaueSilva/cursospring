package br.com.curso.cursospring.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.curso.cursospring.domain.Categoria;
import br.com.curso.cursospring.repositories.CategoriaRepository;
import br.com.curso.cursospring.services.exception.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired //instância automática do Spring
	private CategoriaRepository repo;
	
	public Categoria buscar(Integer id) {
		Optional<Categoria> categoria = repo.findById(id);
		return categoria.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado. Id: "+id+", tipo: "+Categoria.class.getName()));
	}
	
}
