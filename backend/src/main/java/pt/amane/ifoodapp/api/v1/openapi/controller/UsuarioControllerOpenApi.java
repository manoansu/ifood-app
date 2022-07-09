package pt.amane.ifoodapp.api.v1.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import pt.amane.ifoodapp.api.exceptionhandler.Problem;
import pt.amane.ifoodapp.api.v1.modeldtos.UsuarioModelDTO;
import pt.amane.ifoodapp.api.v1.modeldtos.inputdtos.SenhaInput;
import pt.amane.ifoodapp.api.v1.modeldtos.inputdtos.UsuarioComSenhaInput;
import pt.amane.ifoodapp.api.v1.modeldtos.inputdtos.UsuarioInput;

@Api(tags = "Usuários")
public interface UsuarioControllerOpenApi {

	@ApiOperation("Lista os usuários")
	CollectionModel<UsuarioModelDTO> listar();

	@ApiOperation("Busca um usuário por ID")
	@ApiResponses({
		@ApiResponse(code = 400, message = "ID do usuário inválido", response = Problem.class),
		@ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
	})
	UsuarioModelDTO buscar(
			@ApiParam(value = "ID do usuário", example = "1", required = true)
			Long usuarioId);

	@ApiOperation("Cadastra um usuário")
	@ApiResponses({
		@ApiResponse(code = 201, message = "Usuário cadastrado"),
	})
	UsuarioModelDTO adicionar(
			@ApiParam(name = "corpo", value = "Representação de um novo usuário", required = true)
			UsuarioComSenhaInput usuarioInput);
	
	@ApiOperation("Atualiza um usuário por ID")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Usuário atualizado"),
		@ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
	})
	UsuarioModelDTO atualizar(
			@ApiParam(value = "ID do usuário", example = "1", required = true)
			Long usuarioId,
			
			@ApiParam(name = "corpo", value = "Representação de um usuário com os novos dados",
				required = true)
			UsuarioInput usuarioInput);

	@ApiOperation("Atualiza a senha de um usuário")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Senha alterada com sucesso"),
		@ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
	})
	void alterarSenha(
			@ApiParam(value = "ID do usuário", example = "1", required = true)
			Long usuarioId,
			
			@ApiParam(name = "corpo", value = "Representação de uma nova senha", 
				required = true)
			SenhaInput senha);

}