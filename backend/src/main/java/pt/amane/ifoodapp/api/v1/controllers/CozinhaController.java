package pt.amane.ifoodapp.api.v1.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
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
import pt.amane.ifoodapp.api.v1.assemblers.CozinhaInputDisassembler;
import pt.amane.ifoodapp.api.v1.assemblers.CozinhaModelAssembler;
import pt.amane.ifoodapp.api.v1.modeldtos.CozinhaModelDTO;
import pt.amane.ifoodapp.api.v1.modeldtos.inputdtos.CozinhaInput;
import pt.amane.ifoodapp.api.v1.openapi.controller.CozinhaControllerOpenApi;
import pt.amane.ifoodapp.domain.model.Cozinha;
import pt.amane.ifoodapp.domain.repository.CozinhaRepository;
import pt.amane.ifoodapp.domain.services.CozinhaService;


@RestController
@RequestMapping(value = "/v1/cozinhas", produces = MediaType.APPLICATION_JSON_VALUE)
public class CozinhaController implements CozinhaControllerOpenApi {

	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Autowired
	private CozinhaService cadastroCozinha;
	
	@Autowired
	private CozinhaModelAssembler cozinhaModelAssembler;
	
	@Autowired
	private CozinhaInputDisassembler cozinhaInputDisassembler;
	
	@Autowired
	private PagedResourcesAssembler<Cozinha> pagedResourcesAssembler;
	
	@Override
	@GetMapping
	public PagedModel<CozinhaModelDTO> listar(@PageableDefault(size = 10) Pageable pageable) {
		Page<Cozinha> cozinhasPage = cozinhaRepository.findAll(pageable);
		
		PagedModel<CozinhaModelDTO> cozinhasPagedModel = pagedResourcesAssembler
				.toModel(cozinhasPage, cozinhaModelAssembler);
		
		return cozinhasPagedModel;
	}
	
	@Override
	@GetMapping("/{cozinhaId}")
	public CozinhaModelDTO buscar(@PathVariable Long cozinhaId) {
		Cozinha cozinha = cadastroCozinha.findById(cozinhaId);
		
		return cozinhaModelAssembler.toModel(cozinha);
	}

	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CozinhaModelDTO adicionar(@RequestBody @Valid CozinhaInput cozinhaInput) {
		Cozinha cozinha = cozinhaInputDisassembler.toDomainObject(cozinhaInput);
		cozinha = cadastroCozinha.create(cozinha);
		
		return cozinhaModelAssembler.toModel(cozinha);
	}
	
	@Override
	@PutMapping("/{cozinhaId}")
	public CozinhaModelDTO atualizar(@PathVariable Long cozinhaId,
			@RequestBody @Valid CozinhaInput cozinhaInput) {
		Cozinha cozinhaAtual = cadastroCozinha.findById(cozinhaId);
		cozinhaInputDisassembler.copyToDomainObject(cozinhaInput, cozinhaAtual);
		cozinhaAtual = cadastroCozinha.create(cozinhaAtual);
		
		return cozinhaModelAssembler.toModel(cozinhaAtual);
	}
	
	@Override
	@DeleteMapping("/{cozinhaId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long cozinhaId) {
		cadastroCozinha.delete(cozinhaId);
	}
	
}
