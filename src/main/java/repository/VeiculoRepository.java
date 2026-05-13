package repository;

import model.Veiculo;
import model.Categoria;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import enums.Tipo_Caixa;
import enums.Tipo_Combustao;
import enums.Tipo_Motor;
import enums.Status;




@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {

  List<Veiculo> findByStatus(Status status);
  List<Veiculo> findByCategoria(Categoria categoria);
  List<Veiculo> findByMarca(String marca);
  List<Veiculo> findByTipoCaixa(Tipo_Caixa tipo_caixa);
  List<Veiculo> findByTipoCombustao(Tipo_Combustao tipo_combustao);
  List<Veiculo> findByTipoMotor(Tipo_Motor tipo_motor);
  List<Veiculo> findByAnoGreaterThan(int ano);
  List<Veiculo> findByAnoLessThan(int ano);

}
