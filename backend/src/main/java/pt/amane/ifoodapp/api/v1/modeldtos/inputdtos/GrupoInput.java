package pt.amane.ifoodapp.api.v1.modeldtos.inputdtos;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GrupoInput {

	@ApiModelProperty(example = "Gerente", required = true)
	@NotBlank
	private String nome;
	
}
