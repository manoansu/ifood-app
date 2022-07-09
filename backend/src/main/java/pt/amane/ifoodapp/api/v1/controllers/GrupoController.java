package pt.amane.ifoodapp.api.v1.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import pt.amane.ifoodapp.api.v1.assemblers.GrupoInputDisassembler;
import pt.amane.ifoodapp.api.v1.assemblers.GrupoModelAssembler;
import pt.amane.ifoodapp.api.v1.modeldtos.GrupoModelDTO;
import pt.amane.ifoodapp.api.v1.modeldtos.inputdtos.GrupoInput;
import pt.amane.ifoodapp.api.v1.openapi.controller.GrupoControllerOpenApi;
import pt.amane.ifoodapp.domain.model.Grupo;
import pt.amane.ifoodapp.domain.repository.GrupoRepository;
import pt.amane.ifoodapp.domain.services.GrupoService;

@RestController
@RequestMapping(path = "/v1/grupos", produces = MediaType.APPLICATION_JSON_VALUE)
public class GrupoController implements GrupoControllerOpenApi {

	@Autowired
	private GrupoRepository grupoRepository;
	
	@Autowired
	private GrupoService cadastroGrupo;
	
	@Autowired
	private GrupoModelAssembler grupoModelAssembler;
	
	@Autowired
	private GrupoInputDisassembler grupoInputDisassembler;
	
	@Override
	@GetMapping
	public CollectionModel<GrupoModelDTO> listar() {
		List<Grupo> todosGrupos = grupoRepository.findAll();
		
		return grupoModelAssembler.toCollectionModel(todosGrupos);
	}
	
	@Override
	@GetMapping("/{grupoId}")
	public GrupoModelDTO buscar(@PathVariable Long grupoId) {
		Grupo grupo = cadastroGrupo.findById(grupoId);
		
		return grupoModelAssembler.toModel(grupo);
	}
	
	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public GrupoModelDTO adicionar(@RequestBody @Valid GrupoInput grupoInput) {
		Grupo grupo = grupoInputDisassembler.toDomainObject(grupoInput);
		
		grupo = cadastroGrupo.salvar(grupo);
		
		return grupoModelAssembler.toModel(grupo);
	}
	
	@Override
	@PutMapping("/{grupoId}")
	public GrupoModelDTO atualizar(@PathVariable Long grupoId,
			@RequestBody @Valid GrupoInput grupoInput) {
		Grupo grupoAtual = cadastroGrupo.findById(grupoId);
		
		grupoInputDisassembler.copyToDomainObject(grupoInput, grupoAtual);
		
		grupoAtual = cadastroGrupo.salvar(grupoAtual);
		
		return grupoModelAssembler.toModel(grupoAtual);
	}
	
	@Override
	@DeleteMapping("/{grupoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long grupoId) {
		cadastroGrupo.excluir(grupoId);	
	}
	
}
