package pt.amane.ifoodapp.api.v1.assemblers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import pt.amane.ifoodapp.api.ApiLinks;
import pt.amane.ifoodapp.api.v1.controllers.RestauranteProdutoController;
import pt.amane.ifoodapp.api.v1.modeldtos.ProdutoModelDTO;
import pt.amane.ifoodapp.domain.model.Produto;

@Component
public class ProdutoModelAssembler 
		extends RepresentationModelAssemblerSupport<Produto, ProdutoModelDTO> {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private ApiLinks apiLinks;
	
	public ProdutoModelAssembler() {
		super(RestauranteProdutoController.class, ProdutoModelDTO.class);
	}
	
	@Override
	public ProdutoModelDTO toModel(Produto produto) {
		ProdutoModelDTO produtoModel = createModelWithId(
				produto.getId(), produto, produto.getRestaurante().getId());
		
		modelMapper.map(produto, produtoModel);
		
		produtoModel.add(apiLinks.linkToProdutos(produto.getRestaurante().getId(), "produtos"));

		produtoModel.add(apiLinks.linkToFotoProduto(
				produto.getRestaurante().getId(), produto.getId(), "foto"));
		
		return produtoModel;
	}
	
}
