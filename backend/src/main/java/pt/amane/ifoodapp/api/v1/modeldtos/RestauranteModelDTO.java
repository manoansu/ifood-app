package pt.amane.ifoodapp.api.v1.modeldtos;

import java.math.BigDecimal;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "restaurantes")
@Setter
@Getter
public class RestauranteModelDTO extends RepresentationModel<RestauranteModelDTO> {

	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Thai Gourmet")
	private String nome;
	
	@ApiModelProperty(example = "12.00")
	private BigDecimal taxaFrete;
	
	private CozinhaModelDTO cozinha;
	
	private Boolean ativo;
	private Boolean aberto;
	private EnderecoModelDTO endereco;
	
}
