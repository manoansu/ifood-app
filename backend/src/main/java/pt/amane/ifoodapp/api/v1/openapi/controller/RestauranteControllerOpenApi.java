package pt.amane.ifoodapp.api.v1.openapi.controller;

import java.util.List;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import pt.amane.ifoodapp.api.exceptionhandler.Problem;
import pt.amane.ifoodapp.api.v1.modeldtos.RestauranteApenasNomeModelDTO;
import pt.amane.ifoodapp.api.v1.modeldtos.RestauranteBasicoModelDTO;
import pt.amane.ifoodapp.api.v1.modeldtos.RestauranteModelDTO;
import pt.amane.ifoodapp.api.v1.modeldtos.inputdtos.RestauranteInput;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "Restaurantes")
public interface RestauranteControllerOpenApi {

	@ApiOperation(value = "Lista restaurantes")
	@ApiImplicitParams({
		@ApiImplicitParam(value = "Nome da projeção de pedidos", allowableValues = "apenas-nome",
				name = "projecao", paramType = "query", type = "string")
	})
	CollectionModel<RestauranteBasicoModelDTO> listar();
	
	@ApiIgnore
	@ApiOperation(value = "Lista restaurantes", hidden = true)
	CollectionModel<RestauranteApenasNomeModelDTO> listarApenasNomes();
	
	@ApiOperation("Busca um restaurante por ID")
	@ApiResponses({
		@ApiResponse(code = 400, message = "ID do restaurante inválido", response = Problem.class),
		@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
	})
	RestauranteModelDTO buscar(
			@ApiParam(value = "ID de um restaurante", example = "1", required = true)
			Long restauranteId);
	
	@ApiOperation("Cadastra um restaurante")
	@ApiResponses({
		@ApiResponse(code = 201, message = "Restaurante cadastrado"),
	})
	RestauranteModelDTO adicionar(
			@ApiParam(name = "corpo", value = "Representação de um novo restaurante", required = true)
			RestauranteInput restauranteInput);
	
	@ApiOperation("Atualiza um restaurante por ID")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Restaurante atualizado"),
		@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
	})
	RestauranteModelDTO atualizar(
			@ApiParam(value = "ID de um restaurante", example = "1", required = true)
			Long restauranteId,
			
			@ApiParam(name = "corpo", value = "Representação de um restaurante com os novos dados", 
				required = true)
			RestauranteInput restauranteInput);
	
	@ApiOperation("Ativa um restaurante por ID")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Restaurante ativado com sucesso"),
		@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
	})
	ResponseEntity<Void> ativar(
			@ApiParam(value = "ID de um restaurante", example = "1", required = true)
			Long restauranteId);
	
	@ApiOperation("Inativa um restaurante por ID")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Restaurante inativado com sucesso"),
		@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
	})
	ResponseEntity<Void> inativar(
			@ApiParam(value = "ID de um restaurante", example = "1", required = true)
			Long restauranteId);
	
	@ApiOperation("Ativa múltiplos restaurantes")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Restaurantes ativados com sucesso")
	})
	void ativarMultiplos(
			@ApiParam(name = "corpo", value = "IDs de restaurantes", required = true)
			List<Long> restauranteIds);
	
	@ApiOperation("Inativa múltiplos restaurantes")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Restaurantes ativados com sucesso")
	})
	void inativarMultiplos(
			@ApiParam(name = "corpo", value = "IDs de restaurantes", required = true)
			List<Long> restauranteIds);

	@ApiOperation("Abre um restaurante por ID")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Restaurante aberto com sucesso"),
		@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
	})
	ResponseEntity<Void> abrir(
			@ApiParam(value = "ID de um restaurante", example = "1", required = true)
			Long restauranteId);
	
	@ApiOperation("Fecha um restaurante por ID")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Restaurante fechado com sucesso"),
		@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
	})
	ResponseEntity<Void> fechar(
			@ApiParam(value = "ID de um restaurante", example = "1", required = true)
			Long restauranteId);

}
