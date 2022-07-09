package pt.amane.ifoodapp.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import pt.amane.ifoodapp.domain.model.Estado;
import pt.amane.ifoodapp.domain.model.exceptions.EntidadeEmUsoException;
import pt.amane.ifoodapp.domain.model.exceptions.EstadoNaoEncontradoException;
import pt.amane.ifoodapp.domain.repository.EstadoRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class EstadoService {

	private static final String MSG_ESTADO_EM_USO 
		= "Estado de código %d não pode ser removido, pois está em uso";
	
	@Autowired
	private EstadoRepository estadoRepository;

	@Transactional(readOnly = true)
	public Estado findById(Long estadoId) {
		return estadoRepository.findById(estadoId)
				.orElseThrow(() -> new EstadoNaoEncontradoException(estadoId));
	}

	@Transactional(readOnly = true)
	public List<Estado> findAll(){
		List<Estado> estados = estadoRepository.findAll(Sort.by("nome"));
		return  estados;
	}
	
	@Transactional
	public Estado create(Estado estado) {
		return estadoRepository.save(estado);
	}

	@Transactional
	public Estado update(Long estadoId, Estado estado){
		try {
			Estado newEstado = estadoRepository.getOne(estadoId);
			newEstado.setNome(estado.getNome());
			return estadoRepository.save(estado);
		}catch (EntityNotFoundException e){
			throw new EstadoNaoEncontradoException(String.format(MSG_ESTADO_EM_USO, estadoId));
		}
	}

	public void delete(Long estadoId) {
		try {
			estadoRepository.deleteById(estadoId);
			estadoRepository.flush();
			
		} catch (EmptyResultDataAccessException e) {
			throw new EstadoNaoEncontradoException(estadoId);
		
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
				String.format(MSG_ESTADO_EM_USO, estadoId));
		}
	}
}
