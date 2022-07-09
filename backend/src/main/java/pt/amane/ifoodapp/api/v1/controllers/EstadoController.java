package pt.amane.ifoodapp.api.v1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pt.amane.ifoodapp.api.v1.assemblers.EstadoInputDisassembler;
import pt.amane.ifoodapp.api.v1.assemblers.EstadoModelAssembler;
import pt.amane.ifoodapp.api.v1.modeldtos.EstadoModelDTO;
import pt.amane.ifoodapp.api.v1.modeldtos.inputdtos.EstadoInput;
import pt.amane.ifoodapp.api.v1.openapi.controller.EstadoControllerOpenApi;
import pt.amane.ifoodapp.domain.model.Estado;
import pt.amane.ifoodapp.domain.repository.EstadoRepository;
import pt.amane.ifoodapp.domain.services.EstadoService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/v1/estados", produces = MediaType.APPLICATION_JSON_VALUE)
public class EstadoController implements EstadoControllerOpenApi {

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private EstadoService estadoService;

    @Autowired
    private EstadoModelAssembler estadoModelAssembler;

    @Autowired
    private EstadoInputDisassembler estadoInputDisassembler;

    @Override
    @GetMapping(value = "/{estadoId}")
    public EstadoModelDTO findById(@PathVariable Long estadoId){
        Estado estado = estadoService.findById(estadoId);
        return estadoModelAssembler.toModel(estado);
    }

    @Override
    @GetMapping
    public CollectionModel<EstadoModelDTO> findAll(){
        List<Estado> estados = estadoService.findAll();
        return estadoModelAssembler.toCollectionModel(estados);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EstadoModelDTO adicionar(@RequestBody @Valid EstadoInput estadoInput) {
        Estado estado = estadoInputDisassembler.toDomainObject(estadoInput);
        estado = estadoService.create(estado);
        return estadoModelAssembler.toModel(estado);
    }

    @Override
    @PutMapping("/{estadoId}")
    public EstadoModelDTO atualizar(@PathVariable Long estadoId,@RequestBody @Valid EstadoInput estadoInput) {
        Estado estadoAtual = estadoService.findById(estadoId);

        estadoInputDisassembler.copyToDomainObject(estadoInput, estadoAtual);

        estadoAtual = estadoService.create(estadoAtual);

        return estadoModelAssembler.toModel(estadoAtual);
    }

    @Override
    @DeleteMapping("/{estadoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long estadoId) {
        estadoService.delete(estadoId);
    }
}
