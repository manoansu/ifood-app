package pt.amane.ifoodapp.api.v1.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import pt.amane.ifoodapp.api.v1.modeldtos.CozinhaModelDTO;

@ApiModel("CozinhasModel")
@Setter
@Getter
public class CozinhasModelOpenApi {
	
	private CozinhasEmbeddedModelOpenApi _embedded;
	private Links _links;
	private PageModelOpenApi page;
	
	@ApiModel("CozinhasEmbeddedModel")
	@Data
	public class CozinhasEmbeddedModelOpenApi {
		
		private List<CozinhaModelDTO> cozinhas;
		
	}
	
}
