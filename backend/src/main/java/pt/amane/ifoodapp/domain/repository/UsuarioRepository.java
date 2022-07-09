package pt.amane.ifoodapp.domain.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import pt.amane.ifoodapp.domain.model.Usuario;

@Repository
public interface UsuarioRepository extends CustomJpaRepository<Usuario, Long> {

	Optional<Usuario> findByEmail(String email);
	
}
