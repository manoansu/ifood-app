package pt.amane.ifoodapp.api.v1.modeldtos;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "cidades")
@Setter
@Getter
public class CidadeResumoModelDTO extends RepresentationModel<CidadeResumoModelDTO> {

	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Uberlândia")
	private String nome;
	
	@ApiModelProperty(example = "Minas Gerais")
	private String estado;
	
}
