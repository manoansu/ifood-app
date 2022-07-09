package pt.amane.ifoodapp.api.v1.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;


import io.swagger.annotations.ApiModel;
import lombok.Data;
import pt.amane.ifoodapp.api.v1.modeldtos.CidadeModelDTO;

@ApiModel("CidadesModel")
@Data
public class CidadesModelOpenApi {

	private CidadesEmbeddedModelOpenApi _embedded;
	private Links _links;
	
	@ApiModel("CidadesEmbeddedModel")
	@Data
	public class CidadesEmbeddedModelOpenApi {
		
		private List<CidadeModelDTO> cidades;
		
	}
	
}
