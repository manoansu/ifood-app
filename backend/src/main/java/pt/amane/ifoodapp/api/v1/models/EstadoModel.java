package pt.amane.ifoodapp.api.v1.models;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "estados")
@Data
public class EstadoModel extends RepresentationModel<EstadoModel> {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Minas Gerais")
    private String nome;
}
