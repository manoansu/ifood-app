package pt.amane.ifoodapp.api.v1.assemblers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import pt.amane.ifoodapp.api.ApiLinks;
import pt.amane.ifoodapp.api.v1.controllers.RestauranteController;
import pt.amane.ifoodapp.api.v1.modeldtos.RestauranteModelDTO;
import pt.amane.ifoodapp.domain.model.Restaurante;

@Component
public class RestauranteModelAssembler 
		extends RepresentationModelAssemblerSupport<Restaurante, RestauranteModelDTO> {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private ApiLinks apiLinks;
	
	public RestauranteModelAssembler() {
		super(RestauranteController.class, RestauranteModelDTO.class);
	}
	
	@Override
	public RestauranteModelDTO toModel(Restaurante restaurante) {
		RestauranteModelDTO restauranteModel = createModelWithId(restaurante.getId(), restaurante);
		modelMapper.map(restaurante, restauranteModel);
		
		restauranteModel.add(apiLinks.linkToRestaurantes("restaurantes"));
		
		if (restaurante.ativacaoPermitida()) {
			restauranteModel.add(
					apiLinks.linkToRestauranteAtivacao(restaurante.getId(), "ativar"));
		}

		if (restaurante.inativacaoPermitida()) {
			restauranteModel.add(
					apiLinks.linkToRestauranteInativacao(restaurante.getId(), "inativar"));
		}

		if (restaurante.aberturaPermitida()) {
			restauranteModel.add(
					apiLinks.linkToRestauranteAbertura(restaurante.getId(), "abrir"));
		}

		if (restaurante.fechamentoPermitido()) {
			restauranteModel.add(
					apiLinks.linkToRestauranteFechamento(restaurante.getId(), "fechar"));
		}
		
		restauranteModel.add(apiLinks.linkToProdutos(restaurante.getId(), "produtos"));
		
		restauranteModel.getCozinha().add(
				apiLinks.linkToCozinha(restaurante.getCozinha().getId()));
		
		if (restauranteModel.getEndereco() != null 
				&& restauranteModel.getEndereco().getCidade() != null) {
			restauranteModel.getEndereco().getCidade().add(
					apiLinks.linkToCidade(restaurante.getEndereco().getCidade().getId()));
		}
		
		restauranteModel.add(apiLinks.linkToRestauranteFormasPagamento(restaurante.getId(),
				"formas-pagamento"));
		
		restauranteModel.add(apiLinks.linkToRestauranteResponsaveis(restaurante.getId(),
				"responsaveis"));
		
		return restauranteModel;
	}
	
	@Override
	public CollectionModel<RestauranteModelDTO> toCollectionModel(Iterable<? extends Restaurante> entities) {
		return super.toCollectionModel(entities)
				.add(apiLinks.linkToRestaurantes());
	}
	
}
