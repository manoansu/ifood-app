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
import pt.amane.ifoodapp.api.v1.assemblers.PermissaoModelAssembler;
import pt.amane.ifoodapp.api.v1.modeldtos.PermissaoModelDTO;
import pt.amane.ifoodapp.api.v1.openapi.controller.GrupoPermissaoControllerOpenApi;
import pt.amane.ifoodapp.domain.model.Grupo;
import pt.amane.ifoodapp.domain.services.GrupoService;

@RestController
@RequestMapping(path = "/v1/grupos/{grupoId}/permissoes",
		produces = MediaType.APPLICATION_JSON_VALUE)
public class GrupoPermissaoController implements GrupoPermissaoControllerOpenApi {

	@Autowired
	private GrupoService cadastroGrupo;
	
	@Autowired
	private PermissaoModelAssembler permissaoModelAssembler;
	
	@Autowired
	private ApiLinks apiLinks;
	
	@Override
	@GetMapping
	public CollectionModel<PermissaoModelDTO> listar(@PathVariable Long grupoId) {
		Grupo grupo = cadastroGrupo.findById(grupoId);
		
		CollectionModel<PermissaoModelDTO> permissoesModel
			= permissaoModelAssembler.toCollectionModel(grupo.getPermissoes())
				.removeLinks()
				.add(apiLinks.linkToGrupoPermissoes(grupoId))
				.add(apiLinks.linkToGrupoPermissaoAssociacao(grupoId, "associar"));
		
		permissoesModel.getContent().forEach(permissaoModel -> {
			permissaoModel.add(apiLinks.linkToGrupoPermissaoDesassociacao(
					grupoId, permissaoModel.getId(), "desassociar"));
		});
		
		return permissoesModel;
	}
	
	@Override
	@DeleteMapping("/{permissaoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> desassociar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
		cadastroGrupo.desassociarPermissao(grupoId, permissaoId);
		
		return ResponseEntity.noContent().build();
	}
	
	@Override
	@PutMapping("/{permissaoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> associar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
		cadastroGrupo.associarPermissao(grupoId, permissaoId);
		
		return ResponseEntity.noContent().build();
	}

}
