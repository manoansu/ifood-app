package pt.amane.ifoodapp.api.v1.controllers;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import pt.amane.ifoodapp.api.v1.assemblers.FormaPagamentoInputDisassembler;
import pt.amane.ifoodapp.api.v1.assemblers.FormaPagamentoModelAssembler;
import pt.amane.ifoodapp.api.v1.modeldtos.FormaPagamentoModelDTO;
import pt.amane.ifoodapp.api.v1.modeldtos.inputdtos.FormaPagamentoInput;
import pt.amane.ifoodapp.api.v1.openapi.controller.FormaPagamentoControllerOpenApi;
import pt.amane.ifoodapp.domain.model.FormaPagamento;
import pt.amane.ifoodapp.domain.repository.FormaPagamentoRepository;
import pt.amane.ifoodapp.domain.services.FormaPagamentoService;

@RestController
@RequestMapping(path = "/v1/formas-pagamento", produces = MediaType.APPLICATION_JSON_VALUE)
public class FormaPagamentoController implements FormaPagamentoControllerOpenApi {

	@Autowired
	private FormaPagamentoRepository formaPagamentoRepository;
	
	@Autowired
	private FormaPagamentoService cadastroFormaPagamento;
	
	@Autowired
	private FormaPagamentoModelAssembler formaPagamentoModelAssembler;
	
	@Autowired
	private FormaPagamentoInputDisassembler formaPagamentoInputDisassembler;
	
	@Override
	@GetMapping
	public ResponseEntity<CollectionModel<FormaPagamentoModelDTO>> listar(ServletWebRequest request) {
		ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());
		
		String eTag = "0";
		
		OffsetDateTime dataUltimaAtualizacao = formaPagamentoRepository.getDataUltimaAtualizacao();
		
		if (dataUltimaAtualizacao != null) {
			eTag = String.valueOf(dataUltimaAtualizacao.toEpochSecond());
		}
		
		if (request.checkNotModified(eTag)) {
			return null;
		}
		
		List<FormaPagamento> todasFormasPagamentos = formaPagamentoRepository.findAll();
		
		CollectionModel<FormaPagamentoModelDTO> formasPagamentosModel =
				formaPagamentoModelAssembler.toCollectionModel(todasFormasPagamentos);
		
		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePublic())
				.eTag(eTag)
				.body(formasPagamentosModel);
	}
	
	@Override
	@GetMapping("/{formaPagamentoId}")
	public ResponseEntity<FormaPagamentoModelDTO> buscar(@PathVariable Long formaPagamentoId,
			ServletWebRequest request) {
		
		ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());
		
		String eTag = "0";
		
		OffsetDateTime dataAtualizacao = formaPagamentoRepository
				.getDataAtualizacaoById(formaPagamentoId);
		
		if (dataAtualizacao != null) {
			eTag = String.valueOf(dataAtualizacao.toEpochSecond());
		}
		
		if (request.checkNotModified(eTag)) {
			return null;
		}
		
		FormaPagamento formaPagamento = cadastroFormaPagamento.findById(formaPagamentoId);
		
		FormaPagamentoModelDTO formaPagamentoModel = formaPagamentoModelAssembler.toModel(formaPagamento);
		
		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
				.eTag(eTag)
				.body(formaPagamentoModel);
	}
	
	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public FormaPagamentoModelDTO adicionar(@RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
		FormaPagamento formaPagamento = formaPagamentoInputDisassembler.toDomainObject(formaPagamentoInput);
		
		formaPagamento = cadastroFormaPagamento.create(formaPagamento);
		
		return formaPagamentoModelAssembler.toModel(formaPagamento);
	}
	
	@Override
	@PutMapping("/{formaPagamentoId}")
	public FormaPagamentoModelDTO atualizar(@PathVariable Long formaPagamentoId,
			@RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
		FormaPagamento formaPagamentoAtual = cadastroFormaPagamento.findById(formaPagamentoId);
		
		formaPagamentoInputDisassembler.copyToDomainObject(formaPagamentoInput, formaPagamentoAtual);
		
		formaPagamentoAtual = cadastroFormaPagamento.create(formaPagamentoAtual);
		
		return formaPagamentoModelAssembler.toModel(formaPagamentoAtual);
	}
	
	@Override
	@DeleteMapping("/{formaPagamentoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long formaPagamentoId) {
		cadastroFormaPagamento.excluir(formaPagamentoId);	
	}
	
}
