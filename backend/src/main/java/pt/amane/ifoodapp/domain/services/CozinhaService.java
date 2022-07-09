package pt.amane.ifoodapp.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pt.amane.ifoodapp.domain.model.Cozinha;
import pt.amane.ifoodapp.domain.model.exceptions.CozinhaNaoEncontradaException;
import pt.amane.ifoodapp.domain.model.exceptions.EntidadeEmUsoException;
import pt.amane.ifoodapp.domain.repository.CozinhaRepository;

@Service
public class CozinhaService {

	private static final String MSG_COZINHA_EM_USO 
		= "Cozinha de código %d não pode ser removida, pois está em uso";

	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Transactional
	public Cozinha create(Cozinha cozinha) {
		return cozinhaRepository.save(cozinha);
	}
	
	@Transactional
	public void delete(Long cozinhaId) {
		try {
			cozinhaRepository.deleteById(cozinhaId);
			cozinhaRepository.flush();
			
		} catch (EmptyResultDataAccessException e) {
			throw new CozinhaNaoEncontradaException(cozinhaId);
		
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
				String.format(MSG_COZINHA_EM_USO, cozinhaId));
		}
	}
	
	public Cozinha findById(Long cozinhaId) {
		return cozinhaRepository.findById(cozinhaId)
			.orElseThrow(() -> new CozinhaNaoEncontradaException(cozinhaId));
	}
	
}
