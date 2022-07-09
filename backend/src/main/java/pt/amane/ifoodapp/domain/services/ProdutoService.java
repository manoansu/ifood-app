package pt.amane.ifoodapp.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pt.amane.ifoodapp.domain.model.Produto;
import pt.amane.ifoodapp.domain.model.exceptions.ProdutoNaoEncontradoException;
import pt.amane.ifoodapp.domain.repository.ProdutoRepository;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Transactional
	public Produto create(Produto produto) {
		return produtoRepository.save(produto);
	}
	
	public Produto findById(Long restauranteId, Long produtoId) {
		return produtoRepository.findById(restauranteId, produtoId)
			.orElseThrow(() -> new ProdutoNaoEncontradoException(restauranteId, produtoId));
	}
	
}
