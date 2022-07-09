package pt.amane.ifoodapp.api.v1.openapi.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import pt.amane.ifoodapp.api.exceptionhandler.Problem;
import pt.amane.ifoodapp.api.v1.modeldtos.CozinhaModelDTO;
import pt.amane.ifoodapp.api.v1.modeldtos.inputdtos.CozinhaInput;

@Api(tags = "Cozinhas")
public interface CozinhaControllerOpenApi {

	@ApiOperation("Lista as cozinhas com paginação")
	PagedModel<CozinhaModelDTO> listar(Pageable pageable);
	
	@ApiOperation("Busca uma cozinha por ID")
	@ApiResponses({
		@ApiResponse(code = 400, message = "ID da cozinha inválido", response = Problem.class),
		@ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
	})
	CozinhaModelDTO buscar(
			@ApiParam(value = "ID de uma cozinha", example = "1", required = true)
			Long cozinhaId);
	
	@ApiOperation("Cadastra uma cozinha")
	@ApiResponses({
		@ApiResponse(code = 201, message = "Cozinha cadastrada"),
	})
	CozinhaModelDTO adicionar(
			@ApiParam(name = "corpo", value = "Representação de uma nova cozinha", required = true)
			CozinhaInput cozinhaInput);
	
	@ApiOperation("Atualiza uma cozinha por ID")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Cozinha atualizada"),
		@ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
	})
	CozinhaModelDTO atualizar(
			@ApiParam(value = "ID de uma cozinha", example = "1", required = true)
			Long cozinhaId,
			
			@ApiParam(name = "corpo", value = "Representação de uma cozinha com os novos dados", 
				required = true)
			CozinhaInput cozinhaInput);
	
	@ApiOperation("Exclui uma cozinha por ID")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Cozinha excluída"),
		@ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
	})
	void remover(
			@ApiParam(value = "ID de uma cozinha", example = "1", required = true)
			Long cozinhaId);
	
}
