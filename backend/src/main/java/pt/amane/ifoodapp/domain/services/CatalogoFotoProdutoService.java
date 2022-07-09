package pt.amane.ifoodapp.domain.services;

import java.io.InputStream;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pt.amane.ifoodapp.domain.services.FotoStorageService.NovaFoto;
import pt.amane.ifoodapp.domain.model.FotoProduto;
import pt.amane.ifoodapp.domain.model.exceptions.FotoProdutoNaoEncontradaException;
import pt.amane.ifoodapp.domain.repository.ProdutoRepository;

@Service
public class CatalogoFotoProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private FotoStorageService fotoStorage;
	
	@Transactional
	public FotoProduto create(FotoProduto foto, InputStream dadosArquivo) {
		Long restauranteId = foto.getRestauranteId();
		Long produtoId = foto.getProduto().getId();
		String nomeNovoArquivo = fotoStorage.gerarNomeArquivo(foto.getNomeArquivo());
		String nomeArquivoExistente = null;
		
		Optional<FotoProduto> fotoExistente = produtoRepository
				.findFotoById(restauranteId, produtoId);
		
		if (fotoExistente.isPresent()) {
			nomeArquivoExistente = fotoExistente.get().getNomeArquivo();
			produtoRepository.delete(fotoExistente.get());
		}
		
		foto.setNomeArquivo(nomeNovoArquivo);
		foto =  produtoRepository.save(foto);
		produtoRepository.flush();
		
		NovaFoto novaFoto = NovaFoto.builder()
				.nomeAquivo(foto.getNomeArquivo())
				.contentType(foto.getContentType())
				.inputStream(dadosArquivo)
				.build();

		fotoStorage.substituir(nomeArquivoExistente, novaFoto);
		
		return foto;
	}

	public FotoProduto findById(Long restauranteId, Long produtoId) {
		return produtoRepository.findFotoById(restauranteId, produtoId)
				.orElseThrow(() -> new FotoProdutoNaoEncontradaException(restauranteId, produtoId));
	}

	@Transactional
	public void delete(Long restauranteId, Long produtoId) {
		FotoProduto foto = findById(restauranteId, produtoId);
		
		produtoRepository.delete(foto);
		produtoRepository.flush();

		fotoStorage.remover(foto.getNomeArquivo());
	}
	
}
