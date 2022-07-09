package pt.amane.ifoodapp.api.v1.assemblers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import pt.amane.ifoodapp.api.ApiLinks;
import pt.amane.ifoodapp.api.v1.controllers.PedidoController;
import pt.amane.ifoodapp.api.v1.modeldtos.PedidoResumoModelDTO;
import pt.amane.ifoodapp.domain.model.Pedido;

@Component
public class PedidoResumoModelAssembler 
		extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoModelDTO> {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private ApiLinks apiLinks;

	public PedidoResumoModelAssembler() {
		super(PedidoController.class, PedidoResumoModelDTO.class);
	}
	
	@Override
	public PedidoResumoModelDTO toModel(Pedido pedido) {
		PedidoResumoModelDTO pedidoModel = createModelWithId(pedido.getCodigo(), pedido);
		modelMapper.map(pedido, pedidoModel);
		
		pedidoModel.add(apiLinks.linkToPedidos("pedidos"));
		
		pedidoModel.getRestaurante().add(
				apiLinks.linkToRestaurante(pedido.getRestaurante().getId()));

		pedidoModel.getCliente().add(apiLinks.linkToUsuario(pedido.getCliente().getId()));
		
		return pedidoModel;
	}

}
