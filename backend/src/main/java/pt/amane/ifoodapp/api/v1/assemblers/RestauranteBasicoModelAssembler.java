package pt.amane.ifoodapp.api.v1.assemblers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import pt.amane.ifoodapp.api.ApiLinks;
import pt.amane.ifoodapp.api.v1.controllers.RestauranteController;
import pt.amane.ifoodapp.api.v1.modeldtos.RestauranteBasicoModelDTO;
import pt.amane.ifoodapp.domain.model.Restaurante;

@Component
public class RestauranteBasicoModelAssembler 
		extends RepresentationModelAssemblerSupport<Restaurante, RestauranteBasicoModelDTO> {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private ApiLinks apiLinks;
	
	public RestauranteBasicoModelAssembler() {
		super(RestauranteController.class, RestauranteBasicoModelDTO.class);
	}
	
	@Override
	public RestauranteBasicoModelDTO toModel(Restaurante restaurante) {
		RestauranteBasicoModelDTO restauranteModel = createModelWithId(
				restaurante.getId(), restaurante);
		
		modelMapper.map(restaurante, restauranteModel);
		
		restauranteModel.add(apiLinks.linkToRestaurantes("restaurantes"));
		
		restauranteModel.getCozinha().add(
				apiLinks.linkToCozinha(restaurante.getCozinha().getId()));
		
		return restauranteModel;
	}
	
	@Override
	public CollectionModel<RestauranteBasicoModelDTO> toCollectionModel(Iterable<? extends Restaurante> entities) {
		return super.toCollectionModel(entities)
				.add(apiLinks.linkToRestaurantes());
	}
	
}
