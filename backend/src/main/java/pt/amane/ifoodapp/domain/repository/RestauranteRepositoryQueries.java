package pt.amane.ifoodapp.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import pt.amane.ifoodapp.domain.model.Restaurante;

public interface RestauranteRepositoryQueries {

	List<Restaurante> find(String nome,
						   BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);
	
	List<Restaurante> findComFreteGratis(String nome);

}