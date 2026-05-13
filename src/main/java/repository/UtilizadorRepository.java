package repository;

import model.Utilizador;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UtilizadorRepository extends JpaRepository<Utilizador, Long> {


  // INFO: Optinal = Evitar NullPointerException. Pode returnar um Optional Vazio que permite usar o .isPresent() ou .orElseThrow()
  Optional<Utilizador> findByEmail(String email);
  boolean existsByEmail(String email); // Retorna se existe ou nao

  Optional<Utilizador> findByNome(String nome);

}
