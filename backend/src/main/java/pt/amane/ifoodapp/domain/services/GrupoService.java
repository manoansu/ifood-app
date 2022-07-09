package pt.amane.ifoodapp.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pt.amane.ifoodapp.domain.model.Grupo;
import pt.amane.ifoodapp.domain.model.Permissao;
import pt.amane.ifoodapp.domain.model.exceptions.EntidadeEmUsoException;
import pt.amane.ifoodapp.domain.model.exceptions.GrupoNaoEncontradoException;
import pt.amane.ifoodapp.domain.repository.GrupoRepository;

@Service
public class GrupoService {

	private static final String MSG_GRUPO_EM_USO 
		= "Grupo de código %d não pode ser removido, pois está em uso";
	
	@Autowired
	private GrupoRepository grupoRepository;
	
	@Autowired
	private PermissaoService cadastroPermissao;
	
	@Transactional
	public Grupo salvar(Grupo grupo) {
		return grupoRepository.save(grupo);
	}
	
	@Transactional
	public void excluir(Long grupoId) {
		try {
			grupoRepository.deleteById(grupoId);
			grupoRepository.flush();
			
		} catch (EmptyResultDataAccessException e) {
			throw new GrupoNaoEncontradoException(grupoId);
		
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
				String.format(MSG_GRUPO_EM_USO, grupoId));
		}
	}

	@Transactional
	public void desassociarPermissao(Long grupoId, Long permissaoId) {
		Grupo grupo = findById(grupoId);
		Permissao permissao = cadastroPermissao.findById(permissaoId);
		
		grupo.removerPermissao(permissao);
	}
	
	@Transactional
	public void associarPermissao(Long grupoId, Long permissaoId) {
		Grupo grupo = findById(grupoId);
		Permissao permissao = cadastroPermissao.findById(permissaoId);
		
		grupo.adicionarrPermissao(permissao);
	}
	
	public Grupo findById(Long grupoId) {
		return grupoRepository.findById(grupoId)
			.orElseThrow(() -> new GrupoNaoEncontradoException(grupoId));
	}
	
}
