package pt.amane.ifoodapp.api.v1.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import pt.amane.ifoodapp.api.v1.modeldtos.PermissaoModelDTO;

@Api(tags = "Permissões")
public interface PermissaoControllerOpenApi {

	@ApiOperation("Lista as permissões")
	CollectionModel<PermissaoModelDTO> listar();
	
}
