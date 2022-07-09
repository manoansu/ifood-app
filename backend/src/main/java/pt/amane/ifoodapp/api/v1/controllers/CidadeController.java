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

import pt.amane.ifoodapp.api.ResourceUriHelper;
import pt.amane.ifoodapp.api.v1.assemblers.CidadeInputDisassembler;
import pt.amane.ifoodapp.api.v1.assemblers.CidadeModelAssembler;
import pt.amane.ifoodapp.api.v1.modeldtos.CidadeModelDTO;
import pt.amane.ifoodapp.api.v1.modeldtos.inputdtos.CidadeInput;
import pt.amane.ifoodapp.api.v1.openapi.controller.CidadeControllerOpenApi;
import pt.amane.ifoodapp.domain.model.Cidade;
import pt.amane.ifoodapp.domain.model.exceptions.EstadoNaoEncontradoException;
import pt.amane.ifoodapp.domain.model.exceptions.NegocioException;
import pt.amane.ifoodapp.domain.repository.CidadeRepository;
import pt.amane.ifoodapp.domain.services.CidadeService;

@RestController
@RequestMapping(path = "/v1/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeController implements CidadeControllerOpenApi {

	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private CidadeService cadastroCidade;
	
	@Autowired
	private CidadeModelAssembler cidadeModelAssembler;
	
	@Autowired
	private CidadeInputDisassembler cidadeInputDisassembler;
	
	@Override
	@GetMapping
	public CollectionModel<CidadeModelDTO> listar() {
		List<Cidade> todasCidades = cidadeRepository.findAll();
		
		return cidadeModelAssembler.toCollectionModel(todasCidades);
	}
	
	@Override
	@GetMapping("/{cidadeId}")
	public CidadeModelDTO buscar(@PathVariable Long cidadeId) {
		Cidade cidade = cadastroCidade.findById(cidadeId);
		
		return cidadeModelAssembler.toModel(cidade);
	}
	
	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeModelDTO adicionar(@RequestBody @Valid CidadeInput cidadeInput) {
		try {
			Cidade cidade = cidadeInputDisassembler.toDomainObject(cidadeInput);
			
			cidade = cadastroCidade.create(cidade);
			
			CidadeModelDTO cidadeModel = cidadeModelAssembler.toModel(cidade);
			
			ResourceUriHelper.addUriInResponseHeader(cidadeModel.getId());
			
			return cidadeModel;
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@Override
	@PutMapping("/{cidadeId}")
	public CidadeModelDTO atualizar(@PathVariable Long cidadeId,
									@RequestBody @Valid CidadeInput cidadeInput) {
		try {
			Cidade cidadeAtual = cadastroCidade.findById(cidadeId);
			
			cidadeInputDisassembler.copyToDomainObject(cidadeInput, cidadeAtual);
			
			cidadeAtual = cadastroCidade.create(cidadeAtual);
			
			return cidadeModelAssembler.toModel(cidadeAtual);
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@Override
	@DeleteMapping("/{cidadeId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long cidadeId) {
		cadastroCidade.delete(cidadeId);
	}
	
}
