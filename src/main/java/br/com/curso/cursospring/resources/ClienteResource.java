package br.com.curso.cursospring.resources;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.curso.cursospring.domain.Cliente;
import br.com.curso.cursospring.dto.ClienteDTO;
import br.com.curso.cursospring.services.ClienteService;

@RestController
@RequestMapping(value="/clientes")
public class ClienteResource {
	
	@Autowired
	private ClienteService service;
	
	@RequestMapping(method=RequestMethod.GET, value="/{id}") 
	public ResponseEntity<Cliente> find(@PathVariable Integer id) { 
		Cliente c1 = service.find(id);
		
		return ResponseEntity.ok().body(c1); 
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO objDTO, @PathVariable Integer id){
		Cliente obj = service.fromDTO(objDTO);
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/{id}") 
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(method=RequestMethod.GET) 
	public ResponseEntity<List<ClienteDTO>> findAll() { 
		List<Cliente> listaCat = service.findAll();
		List<ClienteDTO> listaCatDto = listaCat.stream().map(cat -> new ClienteDTO(cat)).collect(Collectors.toList()); //conversor de lista
		return ResponseEntity.ok().body(listaCatDto); 
	}
	
	//Método de paginação com parâmetros opcionais
	//exemplo de resquest: Clientes/page?linesPerPage=5&page=1
	@RequestMapping(method=RequestMethod.GET, value="/page") 
	public ResponseEntity<Page<ClienteDTO>> findPage(
			@RequestParam(value="page" , defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage" , defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="orderBy" , defaultValue="nome") String orderBy, 
			@RequestParam(value="direction" , defaultValue="ASC") String direction) { 
		Page<Cliente> listaCat = service.findPage(page, linesPerPage, orderBy, direction);
		Page<ClienteDTO> listaCatDto = listaCat.map(cat -> new ClienteDTO(cat)); //conversor de lista
		return ResponseEntity.ok().body(listaCatDto); 
	}
	
}
