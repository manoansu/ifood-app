package pt.amane.ifoodapp.api.v1.modeldtos;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "formasPagamento")
@Setter
@Getter
public class FormaPagamentoModelDTO extends RepresentationModel<FormaPagamentoModelDTO> {

	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Cartão de crédito")
	private String descricao;
	
}
