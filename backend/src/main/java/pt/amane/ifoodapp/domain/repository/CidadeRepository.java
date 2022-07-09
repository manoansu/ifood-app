package pt.amane.ifoodapp.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.amane.ifoodapp.domain.model.Cidade;


@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Long> {

}
