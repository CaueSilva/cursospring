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
	
	public Categoria find(Integer id) {
		Optional<Categoria> categoria = repo.findById(id);
		return categoria.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado. Id: "+id+", tipo: "+Categoria.class.getName()));
	}
	
	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return repo.save(obj);
	}
	
	public Categoria update(Categoria obj) {
		find(obj.getId());
		return repo.save(obj);
	}
	
}
