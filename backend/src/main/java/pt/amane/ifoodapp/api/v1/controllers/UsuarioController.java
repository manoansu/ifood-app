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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import pt.amane.ifoodapp.api.v1.assemblers.UsuarioInputDisassembler;
import pt.amane.ifoodapp.api.v1.assemblers.UsuarioModelAssembler;
import pt.amane.ifoodapp.api.v1.modeldtos.UsuarioModelDTO;
import pt.amane.ifoodapp.api.v1.modeldtos.inputdtos.SenhaInput;
import pt.amane.ifoodapp.api.v1.modeldtos.inputdtos.UsuarioComSenhaInput;
import pt.amane.ifoodapp.api.v1.modeldtos.inputdtos.UsuarioInput;
import pt.amane.ifoodapp.api.v1.openapi.controller.UsuarioControllerOpenApi;
import pt.amane.ifoodapp.domain.model.Usuario;
import pt.amane.ifoodapp.domain.repository.UsuarioRepository;
import pt.amane.ifoodapp.domain.services.UsuarioService;

@RestController
@RequestMapping(path = "/v1/usuarios", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioController implements UsuarioControllerOpenApi {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private UsuarioService cadastroUsuario;
	
	@Autowired
	private UsuarioModelAssembler usuarioModelAssembler;
	
	@Autowired
	private UsuarioInputDisassembler usuarioInputDisassembler;
	
	@Override
	@GetMapping
	public CollectionModel<UsuarioModelDTO> listar() {
		List<Usuario> todasUsuarios = usuarioRepository.findAll();
		
		return usuarioModelAssembler.toCollectionModel(todasUsuarios);
	}
	
	@Override
	@GetMapping("/{usuarioId}")
	public UsuarioModelDTO buscar(@PathVariable Long usuarioId) {
		Usuario usuario = cadastroUsuario.findById(usuarioId);
		
		return usuarioModelAssembler.toModel(usuario);
	}
	
	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UsuarioModelDTO adicionar(@RequestBody @Valid UsuarioComSenhaInput usuarioInput) {
		Usuario usuario = usuarioInputDisassembler.toDomainObject(usuarioInput);
		usuario = cadastroUsuario.create(usuario);
		
		return usuarioModelAssembler.toModel(usuario);
	}
	
	@Override
	@PutMapping("/{usuarioId}")
	public UsuarioModelDTO atualizar(@PathVariable Long usuarioId,
			@RequestBody @Valid UsuarioInput usuarioInput) {
		Usuario usuarioAtual = cadastroUsuario.findById(usuarioId);
		usuarioInputDisassembler.copyToDomainObject(usuarioInput, usuarioAtual);
		usuarioAtual = cadastroUsuario.create(usuarioAtual);
		
		return usuarioModelAssembler.toModel(usuarioAtual);
	}
	
	@Override
	@PutMapping("/{usuarioId}/senha")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void alterarSenha(@PathVariable Long usuarioId, @RequestBody @Valid SenhaInput senha) {
		cadastroUsuario.alterarSenha(usuarioId, senha.getSenhaAtual(), senha.getNovaSenha());
	}
	
}
