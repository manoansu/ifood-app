package pt.amane.ifoodapp.api.v1.modeldtos.inputdtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class EstadoInput {

    @ApiModelProperty(example = "Minas Gerais", required = true)
    @NotBlank
    private String nome;
}
