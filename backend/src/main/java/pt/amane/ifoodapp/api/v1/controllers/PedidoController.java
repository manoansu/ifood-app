package pt.amane.ifoodapp.api.v1.controllers;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import pt.amane.ifoodapp.api.v1.assemblers.PedidoInputDisassembler;
import pt.amane.ifoodapp.api.v1.assemblers.PedidoModelAssembler;
import pt.amane.ifoodapp.api.v1.assemblers.PedidoResumoModelAssembler;
import pt.amane.ifoodapp.api.v1.modeldtos.PedidoModelDTO;
import pt.amane.ifoodapp.api.v1.modeldtos.PedidoResumoModelDTO;
import pt.amane.ifoodapp.api.v1.modeldtos.inputdtos.PedidoInput;
import pt.amane.ifoodapp.api.v1.openapi.controller.PedidoControllerOpenApi;
import pt.amane.ifoodapp.core.data.PageWrapper;
import pt.amane.ifoodapp.core.data.PageableTranslator;
import pt.amane.ifoodapp.domain.filters.PedidoFilter;
import pt.amane.ifoodapp.domain.model.Pedido;
import pt.amane.ifoodapp.domain.model.Usuario;
import pt.amane.ifoodapp.domain.model.exceptions.EntidadeNaoEncontradaException;
import pt.amane.ifoodapp.domain.model.exceptions.NegocioException;
import pt.amane.ifoodapp.domain.repository.PedidoRepository;
import pt.amane.ifoodapp.domain.services.EmissaoPedidoService;
import pt.amane.ifoodapp.infraestruture.spec.PedidoSpecs;

@RestController
@RequestMapping(path = "/v1/pedidos", produces = MediaType.APPLICATION_JSON_VALUE)
public class PedidoController implements PedidoControllerOpenApi {

	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private EmissaoPedidoService emissaoPedido;
	
	@Autowired
	private PedidoModelAssembler pedidoModelAssembler;
	
	@Autowired
	private PedidoResumoModelAssembler pedidoResumoModelAssembler;
	
	@Autowired
	private PedidoInputDisassembler pedidoInputDisassembler;
	
	@Autowired
	private PagedResourcesAssembler<Pedido> pagedResourcesAssembler;
	
	@Override
	@GetMapping
	public PagedModel<PedidoResumoModelDTO> pesquisar(PedidoFilter filtro,
													  @PageableDefault(size = 10) Pageable pageable) {
		Pageable pageableTraduzido = traduzirPageable(pageable);
		
		Page<Pedido> pedidosPage = pedidoRepository.findAll(
				PedidoSpecs.usandoFiltro(filtro), pageableTraduzido);
		
		pedidosPage = new PageWrapper<>(pedidosPage, pageable);
		
		return pagedResourcesAssembler.toModel(pedidosPage, pedidoResumoModelAssembler);
	}
	
	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PedidoModelDTO adicionar(@Valid @RequestBody PedidoInput pedidoInput) {
		try {
			Pedido novoPedido = pedidoInputDisassembler.toDomainObject(pedidoInput);

			// TODO pegar usu??rio autenticado
			novoPedido.setCliente(new Usuario());
			novoPedido.getCliente().setId(1L);

			novoPedido = emissaoPedido.emitir(novoPedido);

			return pedidoModelAssembler.toModel(novoPedido);
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@Override
	@GetMapping("/{codigoPedido}")
	public PedidoModelDTO buscar(@PathVariable String codigoPedido) {
		Pedido pedido = emissaoPedido.findById(codigoPedido);
		
		return pedidoModelAssembler.toModel(pedido);
	}
	
	private Pageable traduzirPageable(Pageable apiPageable) {
		var mapeamento = Map.of(
				"codigo", "codigo",
				"subtotal", "subtotal",
				"taxaFrete", "taxaFrete",
				"valorTotal", "valorTotal",
				"dataCriacao", "dataCriacao",
				"restaurante.nome", "restaurante.nome",
				"restaurante.id", "restaurante.id",
				"cliente.id", "cliente.id",
				"cliente.nome", "cliente.nome"
			);
		
		return PageableTranslator.translate(apiPageable, mapeamento);
	}
	
}
