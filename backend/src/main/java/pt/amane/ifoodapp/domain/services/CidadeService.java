package pt.amane.ifoodapp.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.amane.ifoodapp.domain.model.Cidade;
import pt.amane.ifoodapp.domain.model.Estado;
import pt.amane.ifoodapp.domain.model.exceptions.CidadeNaoEncontradaException;
import pt.amane.ifoodapp.domain.model.exceptions.EntidadeEmUsoException;
import pt.amane.ifoodapp.domain.repository.CidadeRepository;

@Service
public class CidadeService {

	private static final String MSG_CIDADE_EM_USO 
		= "Cidade de código %d não pode ser removida, pois está em uso";

	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EstadoService estadoService;
	
	@Transactional
	public Cidade create(Cidade cidade) {
		Long estadoId = cidade.getEstado().getId();

		Estado estado = estadoService.findById(estadoId);
		
		cidade.setEstado(estado);
		
		return cidadeRepository.save(cidade);
	}
	
	@Transactional
	public void delete(Long cidadeId) {
		try {
			cidadeRepository.deleteById(cidadeId);
			cidadeRepository.flush();
			
		} catch (EmptyResultDataAccessException e) {
			throw new CidadeNaoEncontradaException(cidadeId);
		
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
				String.format(MSG_CIDADE_EM_USO, cidadeId));
		}
	}
	
	public Cidade findById(Long cidadeId) {
		return cidadeRepository.findById(cidadeId)
			.orElseThrow(() -> new CidadeNaoEncontradaException(cidadeId));
	}
	
}
