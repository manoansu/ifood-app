package pt.amane.ifoodapp.api.v1.assemblers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import pt.amane.ifoodapp.api.ApiLinks;
import pt.amane.ifoodapp.api.v1.controllers.RestauranteController;
import pt.amane.ifoodapp.api.v1.modeldtos.RestauranteApenasNomeModelDTO;
import pt.amane.ifoodapp.domain.model.Restaurante;

@Component
public class RestauranteApenasNomeModelAssembler 
		extends RepresentationModelAssemblerSupport<Restaurante, RestauranteApenasNomeModelDTO> {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private ApiLinks apiLinks;
	
	public RestauranteApenasNomeModelAssembler() {
		super(RestauranteController.class, RestauranteApenasNomeModelDTO.class);
	}
	
	@Override
	public RestauranteApenasNomeModelDTO toModel(Restaurante restaurante) {
		RestauranteApenasNomeModelDTO restauranteModel = createModelWithId(
				restaurante.getId(), restaurante);
		
		modelMapper.map(restaurante, restauranteModel);
		
		restauranteModel.add(apiLinks.linkToRestaurantes("restaurantes"));
		
		return restauranteModel;
	}
	
	@Override
	public CollectionModel<RestauranteApenasNomeModelDTO> toCollectionModel(Iterable<? extends Restaurante> entities) {
		return super.toCollectionModel(entities)
				.add(apiLinks.linkToRestaurantes());
	}
	
}
