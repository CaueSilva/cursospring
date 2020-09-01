package br.com.curso.cursospring.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.curso.cursospring.domain.ItemPedido;
import br.com.curso.cursospring.domain.PagamentoComBoleto;
import br.com.curso.cursospring.domain.Pedido;
import br.com.curso.cursospring.domain.enums.EstadoPagamento;
import br.com.curso.cursospring.repositories.ItemPedidoRepository;
import br.com.curso.cursospring.repositories.PagamentoRepository;
import br.com.curso.cursospring.repositories.PedidoRepository;
import br.com.curso.cursospring.services.exception.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired //instância automática do Spring
	private PedidoRepository repo;
	@Autowired
	private BoletoService boletoService;
	@Autowired
	private PagamentoRepository pagamentoRepo;
	@Autowired
	private ProdutoService produtoService;
	@Autowired
	private ItemPedidoRepository itemPedidoRepo;
	
	public Pedido find(Integer id) {
		Optional<Pedido> Pedido = repo.findById(id);
		return Pedido.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado. Id: "+id+", tipo: "+Pedido.class.getName()));
	}
	
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if(obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		obj = repo.save(obj);
		pagamentoRepo.save(obj.getPagamento());
		for(ItemPedido itemPedido : obj.getItens()) {
			itemPedido.setDesconto(0.0);
			itemPedido.setPreco((produtoService.find(itemPedido.getProduto().getId())).getPreco());
			itemPedido.setPedido(obj);
		}
		itemPedidoRepo.saveAll(obj.getItens());
		return obj;
	}

}
