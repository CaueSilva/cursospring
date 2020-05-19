package br.com.curso.cursospring.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.curso.cursospring.domain.Categoria;
import br.com.curso.cursospring.services.CategoriaService;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaService service;
	
	@RequestMapping(method=RequestMethod.GET, value="/{id}") //recebe ID da requisição
	public ResponseEntity<?> find(@PathVariable Integer id) { //@PathVariable = {id} vinda da requisição
		Categoria cat1 = service.buscar(id);
		
		return ResponseEntity.ok().body(cat1); //Resposta do serviço "com sucesso"
	}
	
}
