package repository;

import model.Aluguer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AluguerRepository extends JpaRepository<Aluguer, Long> {
}
