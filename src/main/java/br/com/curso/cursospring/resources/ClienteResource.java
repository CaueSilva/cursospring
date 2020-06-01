package br.com.curso.cursospring.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.curso.cursospring.domain.Cliente;
import br.com.curso.cursospring.services.ClienteService;

@RestController
@RequestMapping(value="/clientes")
public class ClienteResource {
	
	@Autowired
	private ClienteService service;
	
	@RequestMapping(method=RequestMethod.GET, value="/{id}") 
	public ResponseEntity<?> find(@PathVariable Integer id) { 
		Cliente c1 = service.buscar(id);
		
		return ResponseEntity.ok().body(c1); 
	}
	
}
