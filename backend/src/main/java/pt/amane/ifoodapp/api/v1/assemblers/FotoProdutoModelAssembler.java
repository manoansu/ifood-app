package pt.amane.ifoodapp.api.v1.assemblers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import pt.amane.ifoodapp.api.ApiLinks;
import pt.amane.ifoodapp.api.v1.controllers.RestauranteProdutoFotoController;
import pt.amane.ifoodapp.api.v1.modeldtos.FotoProdutoModelDTO;
import pt.amane.ifoodapp.domain.model.FotoProduto;

@Component
public class FotoProdutoModelAssembler 
		extends RepresentationModelAssemblerSupport<FotoProduto, FotoProdutoModelDTO> {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private ApiLinks apiLinks;
	
	public FotoProdutoModelAssembler() {
		super(RestauranteProdutoFotoController.class, FotoProdutoModelDTO.class);
	}
	
	@Override
	public FotoProdutoModelDTO toModel(FotoProduto foto) {
		FotoProdutoModelDTO fotoProdutoModel = modelMapper.map(foto, FotoProdutoModelDTO.class);
		
		fotoProdutoModel.add(apiLinks.linkToFotoProduto(
				foto.getRestauranteId(), foto.getProduto().getId()));
		
		fotoProdutoModel.add(apiLinks.linkToProduto(
				foto.getRestauranteId(), foto.getProduto().getId(), "produto"));
		
		return fotoProdutoModel;
	}
	
}
