package pt.amane.ifoodapp.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pt.amane.ifoodapp.domain.model.Permissao;
import pt.amane.ifoodapp.domain.model.exceptions.PermissaoNaoEncontradaException;
import pt.amane.ifoodapp.domain.repository.PermissaoRepository;

@Service
public class PermissaoService {

	@Autowired
	private PermissaoRepository permissaoRepository;
	
	public Permissao findById(Long permissaoId) {
		return permissaoRepository.findById(permissaoId)
			.orElseThrow(() -> new PermissaoNaoEncontradaException(permissaoId));
	}
	
}
