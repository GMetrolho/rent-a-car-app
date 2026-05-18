package repository;

import model.Aluguer;
import model.Utilizador;
import model.Veiculo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;
import enums.Status_Aluguer;

@Repository
public interface AluguerRepository extends JpaRepository<Aluguer, Long> {

    List<Aluguer> findByCliente(Utilizador cliente);
    List<Aluguer> findByVeiculo(Veiculo veiculo);
    List<Aluguer> findByStatusAluguer(Status_Aluguer status);
}

