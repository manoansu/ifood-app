package pt.amane.ifoodapp.api.v1.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import pt.amane.ifoodapp.api.ApiLinks;
import pt.amane.ifoodapp.api.v1.assemblers.ProdutoInputDisassembler;
import pt.amane.ifoodapp.api.v1.assemblers.ProdutoModelAssembler;
import pt.amane.ifoodapp.api.v1.modeldtos.ProdutoModelDTO;
import pt.amane.ifoodapp.api.v1.modeldtos.inputdtos.ProdutoInput;
import pt.amane.ifoodapp.api.v1.openapi.controller.RestauranteProdutoControllerOpenApi;
import pt.amane.ifoodapp.domain.model.Produto;
import pt.amane.ifoodapp.domain.model.Restaurante;
import pt.amane.ifoodapp.domain.repository.ProdutoRepository;
import pt.amane.ifoodapp.domain.services.ProdutoService;
import pt.amane.ifoodapp.domain.services.RestauranteService;

@RestController
@RequestMapping(path = "/v1/restaurantes/{restauranteId}/produtos", 
	produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteProdutoController implements RestauranteProdutoControllerOpenApi {

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private ProdutoService cadastroProduto;
	
	@Autowired
	private RestauranteService cadastroRestaurante;
	
	@Autowired
	private ProdutoModelAssembler produtoModelAssembler;
	
	@Autowired
	private ProdutoInputDisassembler produtoInputDisassembler;
	
	@Autowired
	private ApiLinks apiLinks;
	
	@Override
	@GetMapping
	public CollectionModel<ProdutoModelDTO> listar(@PathVariable Long restauranteId,
												   @RequestParam(required = false, defaultValue = "false") Boolean incluirInativos) {
		Restaurante restaurante = cadastroRestaurante.findById(restauranteId);
		
		List<Produto> todosProdutos = null;
		
		if (incluirInativos) {
			todosProdutos = produtoRepository.findTodosByRestaurante(restaurante);
		} else {
			todosProdutos = produtoRepository.findAtivosByRestaurante(restaurante);
		}
		
		return produtoModelAssembler.toCollectionModel(todosProdutos)
				.add(apiLinks.linkToProdutos(restauranteId));
	}
	
	@Override
	@GetMapping("/{produtoId}")
	public ProdutoModelDTO buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
		Produto produto = cadastroProduto.findById(restauranteId, produtoId);
		
		return produtoModelAssembler.toModel(produto);
	}
	
	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProdutoModelDTO adicionar(@PathVariable Long restauranteId,
			@RequestBody @Valid ProdutoInput produtoInput) {
		Restaurante restaurante = cadastroRestaurante.findById(restauranteId);
		
		Produto produto = produtoInputDisassembler.toDomainObject(produtoInput);
		produto.setRestaurante(restaurante);
		
		produto = cadastroProduto.create(produto);
		
		return produtoModelAssembler.toModel(produto);
	}
	
	@Override
	@PutMapping("/{produtoId}")
	public ProdutoModelDTO atualizar(@PathVariable Long restauranteId, @PathVariable Long produtoId,
			@RequestBody @Valid ProdutoInput produtoInput) {
		Produto produtoAtual = cadastroProduto.findById(restauranteId, produtoId);
		
		produtoInputDisassembler.copyToDomainObject(produtoInput, produtoAtual);
		
		produtoAtual = cadastroProduto.create(produtoAtual);
		
		return produtoModelAssembler.toModel(produtoAtual);
	}
	
}
