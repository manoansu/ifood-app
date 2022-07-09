package pt.amane.ifoodapp.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pt.amane.ifoodapp.domain.model.Pedido;
import pt.amane.ifoodapp.domain.repository.PedidoRepository;

@Service
public class FluxoPedidoService {

	@Autowired
	private EmissaoPedidoService emissaoPedido;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Transactional
	public void confirmar(String codigoPedido) {
		Pedido pedido = emissaoPedido.findById(codigoPedido);
		pedido.confirmar();
		
		pedidoRepository.save(pedido);
	}
	
	@Transactional
	public void cancelar(String codigoPedido) {
		Pedido pedido = emissaoPedido.findById(codigoPedido);
		pedido.cancelar();
		
		pedidoRepository.save(pedido);
	}
	
	@Transactional
	public void entregar(String codigoPedido) {
		Pedido pedido = emissaoPedido.findById(codigoPedido);
		pedido.entregar();
	}
	
}
