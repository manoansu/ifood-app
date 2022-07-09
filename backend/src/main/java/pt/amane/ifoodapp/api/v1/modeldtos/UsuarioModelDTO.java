package pt.amane.ifoodapp.api.v1.modeldtos;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;

@Relation(collectionRelation = "usuarios")
@Data
public class UsuarioModelDTO extends RepresentationModel<UsuarioModelDTO> {

	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "João da Silva")
	private String nome;
	
	@ApiModelProperty(example = "joao.ger@algafood.com.br")
	private String email;
	
}
