package pt.amane.ifoodapp.api.v1.openapi.controller;

import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;
import pt.amane.ifoodapp.api.exceptionhandler.Problem;
import pt.amane.ifoodapp.api.v1.modeldtos.EstadoModelDTO;
import pt.amane.ifoodapp.api.v1.modeldtos.inputdtos.EstadoInput;

@Api(tags = "Estados")
public interface EstadoControllerOpenApi {

    @ApiOperation("Listar os estados")
    CollectionModel<EstadoModelDTO> findAll();

    @ApiOperation("Busca um estado por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID do estado inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Estado não encontrado", response = Problem.class)
    })
    EstadoModelDTO findById(
            @ApiParam(value = "ID de um estado", example = "1", required = true)
            Long estadoId);

    @ApiOperation("Cadastra um estado")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Estado cadastrado"),
    })
    EstadoModelDTO adicionar(
            @ApiParam(name = "corpo", value = "Representação de um novo estado", required = true)
            EstadoInput estadoInput);

    @ApiOperation("Atualiza um estado por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Estado atualizado"),
            @ApiResponse(code = 404, message = "Estado não encontrado", response = Problem.class)
    })
    EstadoModelDTO atualizar(
            @ApiParam(value = "ID de um estado", example = "1", required = true)
            Long estadoId,

            @ApiParam(name = "corpo", value = "Representação de um estado com os novos dados", required = true)
            EstadoInput estadoInput);

    @ApiOperation("Exclui um estado por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Estado excluído"),
            @ApiResponse(code = 404, message = "Estado não encontrado", response = Problem.class)
    })
    void remover(
            @ApiParam(value = "ID de um estado", example = "1", required = true)
            Long estadoId);

}