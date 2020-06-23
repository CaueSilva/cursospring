package br.com.curso.cursospring.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.curso.cursospring.domain.Pedido;
import br.com.curso.cursospring.services.PedidoService;

@RestController
@RequestMapping(value="/pedidos")
public class PedidoResource {
	
	@Autowired
	private PedidoService service;
	
	@RequestMapping(method=RequestMethod.GET, value="/{id}") 
	public ResponseEntity<Pedido> find(@PathVariable Integer id) { 
		Pedido p1 = service.find(id);
		return ResponseEntity.ok().body(p1); 
	}
	
}
