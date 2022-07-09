package pt.amane.ifoodapp.api.v1.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import pt.amane.ifoodapp.api.v1.modeldtos.ProdutoModelDTO;

@ApiModel("ProdutosModel")
@Data
public class ProdutosModelOpenApi {

	private ProdutosEmbeddedModelOpenApi _embedded;
	private Links _links;
	
	@ApiModel("ProdutosEmbeddedModel")
	@Data
	public class ProdutosEmbeddedModelOpenApi {
		
		private List<ProdutoModelDTO> produtos;
		
	}
	
}
