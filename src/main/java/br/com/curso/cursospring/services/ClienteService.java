package br.com.curso.cursospring.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.curso.cursospring.domain.Cliente;
import br.com.curso.cursospring.dto.ClienteDTO;
import br.com.curso.cursospring.repositories.ClienteRepository;
import br.com.curso.cursospring.services.exception.DataIntegrityException;
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
	
	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}
	
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		} catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir o cliente, há entidades relacionadas.", e);
		}
	}
	
	public List<Cliente> findAll() {
		return repo.findAll();
	}
	
	//Serviço de paginação de consulta de banco: retorna a pesquisa em "lote" (de 10 em 10, por exemplo)
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Cliente fromDTO(ClienteDTO objDTO) {
		return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(), null, null);
	}
	
}
