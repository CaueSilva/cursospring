package br.com.curso.cursospring.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.curso.cursospring.domain.Produto;
import br.com.curso.cursospring.dto.ProdutoDTO;
import br.com.curso.cursospring.resources.utils.URL;
import br.com.curso.cursospring.services.ProdutoService;

@RestController
@RequestMapping(value="/produtos")
public class ProdutoResource {
	
	@Autowired
	private ProdutoService service;
	
	@RequestMapping(method=RequestMethod.GET, value="/{id}") 
	public ResponseEntity<Produto> find(@PathVariable Integer id) { 
		Produto p1 = service.find(id);
		return ResponseEntity.ok().body(p1); 
	}
	
	//Método de paginação com parâmetros opcionais
	//exemplo de resquest: produtos/?nome=computador&categorias=1,2,3
	@RequestMapping(method=RequestMethod.GET) 
	public ResponseEntity<Page<ProdutoDTO>> findPage(
			@RequestParam(value="nome" , defaultValue="") String nome,
			@RequestParam(value="categorias" , defaultValue="") String categorias,
			@RequestParam(value="page" , defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage" , defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="orderBy" , defaultValue="nome") String orderBy, 
			@RequestParam(value="direction" , defaultValue="ASC") String direction) { 
		String nomeDecoded = URL.decodeParam(nome);
		List<Integer> ids = URL.decodeIntList(categorias);
		Page<Produto> listaProd = service.search(nomeDecoded, ids, page, linesPerPage, orderBy, direction);
		Page<ProdutoDTO> listaProdDto = listaProd.map(prod -> new ProdutoDTO(prod)); //conversor de lista
		return ResponseEntity.ok().body(listaProdDto); 
	}
	
}
