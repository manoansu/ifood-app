package pt.amane.ifoodapp.api.v1.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pt.amane.ifoodapp.api.v1.assemblers.PermissaoModelAssembler;
import pt.amane.ifoodapp.api.v1.modeldtos.PermissaoModelDTO;
import pt.amane.ifoodapp.api.v1.openapi.controller.PermissaoControllerOpenApi;
import pt.amane.ifoodapp.domain.model.Permissao;
import pt.amane.ifoodapp.domain.repository.PermissaoRepository;

@RestController
@RequestMapping(path = "/v1/permissoes", produces = MediaType.APPLICATION_JSON_VALUE)
public class PermissaoController implements PermissaoControllerOpenApi {

	@Autowired
	private PermissaoRepository permissaoRepository;
	
	@Autowired
	private PermissaoModelAssembler permissaoModelAssembler;
	
	@Override
	@GetMapping
	public CollectionModel<PermissaoModelDTO> listar() {
		List<Permissao> todasPermissoes = permissaoRepository.findAll();
		
		return permissaoModelAssembler.toCollectionModel(todasPermissoes);
	}
	
}
