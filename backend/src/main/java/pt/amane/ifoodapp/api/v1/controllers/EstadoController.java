package pt.amane.ifoodapp.api.v1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.amane.ifoodapp.api.v1.assemblers.EstadoModelAssembler;
import pt.amane.ifoodapp.api.v1.models.EstadoModel;
import pt.amane.ifoodapp.domain.model.Estado;
import pt.amane.ifoodapp.domain.repository.EstadoRepository;
import pt.amane.ifoodapp.domain.services.EstadoService;

@RestController
@RequestMapping(value = "/v1/estados", produces = MediaType.APPLICATION_JSON_VALUE)
public class EstadoController {

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private EstadoService estadoService;

    @Autowired
    private EstadoModelAssembler estadoModelAssembler;

    @GetMapping(value = "/{estadoId}")
    public EstadoModel findById(@PathVariable Long estadoId){
        Estado estado = estadoService.findById(estadoId);
        return estadoModelAssembler.toModel(estado);

    }
}
