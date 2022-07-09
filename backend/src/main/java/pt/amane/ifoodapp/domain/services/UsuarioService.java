package pt.amane.ifoodapp.domain.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pt.amane.ifoodapp.domain.model.Grupo;
import pt.amane.ifoodapp.domain.model.Usuario;
import pt.amane.ifoodapp.domain.model.exceptions.NegocioException;
import pt.amane.ifoodapp.domain.model.exceptions.UsuarioNaoEncontradoException;
import pt.amane.ifoodapp.domain.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private GrupoService cadastroGrupo;
	
	@Transactional
	public Usuario create(Usuario usuario) {
		usuarioRepository.detach(usuario);
		
		Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());
		
		if (usuarioExistente.isPresent() && !usuarioExistente.get().equals(usuario)) {
			throw new NegocioException(
					String.format("Já existe um usuário cadastrado com o e-mail %s", usuario.getEmail()));
		}
		
		return usuarioRepository.save(usuario);
	}
	
	@Transactional
	public void alterarSenha(Long usuarioId, String senhaAtual, String novaSenha) {
		Usuario usuario = findById(usuarioId);
		
		if (usuario.senhaNaoCoincideCom(senhaAtual)) {
			throw new NegocioException("Senha atual informada não coincide com a senha do usuário.");
		}
		
		usuario.setSenha(novaSenha);
	}

	@Transactional
	public void desassociarGrupo(Long usuarioId, Long grupoId) {
		Usuario usuario = findById(usuarioId);
		Grupo grupo = cadastroGrupo.findById(grupoId);
		
		usuario.removerGrupo(grupo);
	}
	
	@Transactional
	public void associarGrupo(Long usuarioId, Long grupoId) {
		Usuario usuario = findById(usuarioId);
		Grupo grupo = cadastroGrupo.findById(grupoId);
		
		usuario.adicionarGrupo(grupo);
	}
	
	public Usuario findById(Long usuarioId) {
		return usuarioRepository.findById(usuarioId)
			.orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioId));
	}
	
}
