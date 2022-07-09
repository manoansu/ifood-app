package pt.amane.ifoodapp.api.v1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import pt.amane.ifoodapp.api.ApiLinks;
import pt.amane.ifoodapp.api.v1.assemblers.UsuarioModelAssembler;
import pt.amane.ifoodapp.api.v1.modeldtos.UsuarioModelDTO;
import pt.amane.ifoodapp.api.v1.openapi.controller.RestauranteUsuarioResponsavelControllerOpenApi;
import pt.amane.ifoodapp.domain.model.Restaurante;
import pt.amane.ifoodapp.domain.services.RestauranteService;

@RestController
@RequestMapping(path = "/v1/restaurantes/{restauranteId}/responsaveis",
		produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteUsuarioResponsavelController implements RestauranteUsuarioResponsavelControllerOpenApi {

	@Autowired
	private RestauranteService cadastroRestaurante;
	
	@Autowired
	private UsuarioModelAssembler usuarioModelAssembler;
	
	@Autowired
	private ApiLinks apiLinks;
	
	@Override
	@GetMapping
	public CollectionModel<UsuarioModelDTO> listar(@PathVariable Long restauranteId) {
		Restaurante restaurante = cadastroRestaurante.findById(restauranteId);
		
		CollectionModel<UsuarioModelDTO> usuariosModel = usuarioModelAssembler
				.toCollectionModel(restaurante.getResponsaveis())
					.removeLinks()
					.add(apiLinks.linkToRestauranteResponsaveis(restauranteId))
					.add(apiLinks.linkToRestauranteResponsavelAssociacao(restauranteId, "associar"));

		usuariosModel.getContent().stream().forEach(usuarioModel -> {
			usuarioModel.add(apiLinks.linkToRestauranteResponsavelDesassociacao(
					restauranteId, usuarioModel.getId(), "desassociar"));
		});
		
		return usuariosModel;
	}

	@Override
	@DeleteMapping("/{usuarioId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> desassociar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
		cadastroRestaurante.desassociarResponsavel(restauranteId, usuarioId);
		
		return ResponseEntity.noContent().build();
	}
	
	@Override
	@PutMapping("/{usuarioId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> associar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
		cadastroRestaurante.associarResponsavel(restauranteId, usuarioId);
		
		return ResponseEntity.noContent().build();
	}

}
