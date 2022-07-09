package pt.amane.ifoodapp.domain.repository;

import pt.amane.ifoodapp.domain.model.FotoProduto;

public interface ProdutoRepositoryQueries {

	FotoProduto save(FotoProduto foto);
	
	void delete(FotoProduto foto);
	
}
