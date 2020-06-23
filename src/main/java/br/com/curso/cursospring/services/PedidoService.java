package br.com.curso.cursospring.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.curso.cursospring.domain.Pedido;
import br.com.curso.cursospring.repositories.PedidoRepository;
import br.com.curso.cursospring.services.exception.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired //instância automática do Spring
	private PedidoRepository repo;
	
	public Pedido find(Integer id) {
		Optional<Pedido> Pedido = repo.findById(id);
		return Pedido.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado. Id: "+id+", tipo: "+Pedido.class.getName()));
	}
	

}
