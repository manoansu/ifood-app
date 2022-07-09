package pt.amane.ifoodapp.api.v1.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import pt.amane.ifoodapp.api.v1.modeldtos.RestauranteBasicoModelDTO;

@ApiModel("RestaurantesBasicoModel")
@Data
public class RestaurantesBasicoModelOpenApi {

	private RestaurantesEmbeddedModelOpenApi _embedded;
	private Links _links;
	
	@ApiModel("RestaurantesEmbeddedModel")
	@Data
	public class RestaurantesEmbeddedModelOpenApi {
		
		private List<RestauranteBasicoModelDTO> restaurantes;
		
	}
	
}
