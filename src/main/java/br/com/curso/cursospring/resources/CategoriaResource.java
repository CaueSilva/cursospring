package br.com.curso.cursospring.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.curso.cursospring.domain.Categoria;
import br.com.curso.cursospring.dto.CategoriaDTO;
import br.com.curso.cursospring.services.CategoriaService;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaService service;
	
	@RequestMapping(method=RequestMethod.GET, value="/{id}") //recebe ID da requisição
	public ResponseEntity<Categoria> find(@PathVariable Integer id) { //@PathVariable = {id} vinda da requisição
		Categoria cat1 = service.find(id);
		
		return ResponseEntity.ok().body(cat1); //Resposta do serviço "com sucesso"
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody CategoriaDTO objDTO){
		Categoria obj = service.fromDTO(objDTO);
		obj = service.insert(obj);
		
		//retorna a URI à requisição que inseriu o objeto:
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		
		//retorna o status 201 - CREATED - Http Status Code:
		return ResponseEntity.created(uri).build();
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value="/{id}",method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody CategoriaDTO objDTO, @PathVariable Integer id){
		Categoria obj = service.fromDTO(objDTO);
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(method=RequestMethod.DELETE, value="/{id}") 
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(method=RequestMethod.GET) 
	public ResponseEntity<List<CategoriaDTO>> findAll() { 
		List<Categoria> listaCat = service.findAll();
		List<CategoriaDTO> listaCatDto = listaCat.stream().map(cat -> new CategoriaDTO(cat)).collect(Collectors.toList()); //conversor de lista
		return ResponseEntity.ok().body(listaCatDto); 
	}
	
	//Método de paginação com parâmetros opcionais
	//exemplo de resquest: categorias/page?linesPerPage=5&page=1
	@RequestMapping(method=RequestMethod.GET, value="/page") 
	public ResponseEntity<Page<CategoriaDTO>> findPage(
			@RequestParam(value="page" , defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage" , defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="orderBy" , defaultValue="nome") String orderBy, 
			@RequestParam(value="direction" , defaultValue="ASC") String direction) { 
		Page<Categoria> listaCat = service.findPage(page, linesPerPage, orderBy, direction);
		Page<CategoriaDTO> listaCatDto = listaCat.map(cat -> new CategoriaDTO(cat)); //conversor de lista
		return ResponseEntity.ok().body(listaCatDto); 
	}
}
