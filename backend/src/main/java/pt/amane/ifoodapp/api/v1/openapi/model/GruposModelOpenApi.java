package pt.amane.ifoodapp.api.v1.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import pt.amane.ifoodapp.api.v1.modeldtos.GrupoModelDTO;

@ApiModel("GruposModel")
@Data
public class GruposModelOpenApi {

	private GruposEmbeddedModelOpenApi _embedded;
	private Links _links;
	
	@ApiModel("GruposEmbeddedModel")
	@Data
	public class GruposEmbeddedModelOpenApi {
		
		private List<GrupoModelDTO> grupos;
		
	}
	
}
