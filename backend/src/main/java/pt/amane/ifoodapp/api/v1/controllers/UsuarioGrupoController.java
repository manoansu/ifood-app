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
import pt.amane.ifoodapp.api.v1.assemblers.GrupoModelAssembler;
import pt.amane.ifoodapp.api.v1.modeldtos.GrupoModelDTO;
import pt.amane.ifoodapp.api.v1.openapi.controller.UsuarioGrupoControllerOpenApi;
import pt.amane.ifoodapp.domain.model.Usuario;
import pt.amane.ifoodapp.domain.services.UsuarioService;

@RestController
@RequestMapping(path = "/v1/usuarios/{usuarioId}/grupos", 
	produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioGrupoController implements UsuarioGrupoControllerOpenApi {

	@Autowired
	private UsuarioService cadastroUsuario;
	
	@Autowired
	private GrupoModelAssembler grupoModelAssembler;
	
	@Autowired
	private ApiLinks apiLinks;
	
	@Override
	@GetMapping
	public CollectionModel<GrupoModelDTO> listar(@PathVariable Long usuarioId) {
		Usuario usuario = cadastroUsuario.findById(usuarioId);
		
		CollectionModel<GrupoModelDTO> gruposModel = grupoModelAssembler.toCollectionModel(usuario.getGrupos())
				.removeLinks()
				.add(apiLinks.linkToUsuarioGrupoAssociacao(usuarioId, "associar"));
		
		gruposModel.getContent().forEach(grupoModel -> {
			grupoModel.add(apiLinks.linkToUsuarioGrupoDesassociacao(
					usuarioId, grupoModel.getId(), "desassociar"));
		});
		
		return gruposModel;
	}
	
	@Override
	@DeleteMapping("/{grupoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> desassociar(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
		cadastroUsuario.desassociarGrupo(usuarioId, grupoId);
		
		return ResponseEntity.noContent().build();
	}
	
	@Override
	@PutMapping("/{grupoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> associar(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
		cadastroUsuario.associarGrupo(usuarioId, grupoId);
		
		return ResponseEntity.noContent().build();
	}

}
