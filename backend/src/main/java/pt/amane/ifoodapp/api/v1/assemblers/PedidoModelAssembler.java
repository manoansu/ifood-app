package pt.amane.ifoodapp.api.v1.assemblers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import pt.amane.ifoodapp.api.ApiLinks;
import pt.amane.ifoodapp.api.v1.controllers.PedidoController;
import pt.amane.ifoodapp.api.v1.modeldtos.PedidoModelDTO;
import pt.amane.ifoodapp.domain.model.Pedido;

@Component
public class PedidoModelAssembler 
		extends RepresentationModelAssemblerSupport<Pedido, PedidoModelDTO> {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private ApiLinks algaLinks;

	public PedidoModelAssembler() {
		super(PedidoController.class, PedidoModelDTO.class);
	}
	
	@Override
	public PedidoModelDTO toModel(Pedido pedido) {
		PedidoModelDTO pedidoModel = createModelWithId(pedido.getCodigo(), pedido);
		modelMapper.map(pedido, pedidoModel);
		
		pedidoModel.add(algaLinks.linkToPedidos("pedidos"));
		
		if (pedido.podeSerConfirmado()) {
			pedidoModel.add(algaLinks.linkToConfirmacaoPedido(pedido.getCodigo(), "confirmar"));
		}
		
		if (pedido.podeSerCancelado()) {
			pedidoModel.add(algaLinks.linkToCancelamentoPedido(pedido.getCodigo(), "cancelar"));
		}
		
		if (pedido.podeSerEntregue()) {
			pedidoModel.add(algaLinks.linkToEntregaPedido(pedido.getCodigo(), "entregar"));
		}
		
		pedidoModel.getRestaurante().add(
				algaLinks.linkToRestaurante(pedido.getRestaurante().getId()));
		
		pedidoModel.getCliente().add(
				algaLinks.linkToUsuario(pedido.getCliente().getId()));
		
		pedidoModel.getFormaPagamento().add(
				algaLinks.linkToFormaPagamento(pedido.getFormaPagamento().getId()));
		
		pedidoModel.getEnderecoEntrega().getCidade().add(
				algaLinks.linkToCidade(pedido.getEnderecoEntrega().getCidade().getId()));
		
		pedidoModel.getItens().forEach(item -> {
			item.add(algaLinks.linkToProduto(
					pedidoModel.getRestaurante().getId(), item.getProdutoId(), "produto"));
		});
		
		return pedidoModel;
	}

}
