package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import enums.Status;
import enums.Tipo_Caixa;
import enums.Tipo_Combustao;
import enums.Tipo_Motor;
import model.Categoria;
import model.Veiculo;
import repository.VeiculoRepository;

@Service
public class VeiculoService {

  @Autowired
  private VeiculoRepository veiculoRepository;

  // Adicionar
    public Veiculo adicionar(Veiculo veiculo) {
        if (veiculo.getMatricula() == null || veiculo.getMatricula().isBlank())
            throw new RuntimeException("Matrícula é obrigatória!");
        if (veiculo.getMarca() == null || veiculo.getMarca().isBlank())
            throw new RuntimeException("Marca é obrigatória!");
        if (veiculo.getModelo() == null || veiculo.getModelo().isBlank())
            throw new RuntimeException("Modelo é obrigatório!");
        if (veiculo.getPrecoDiario() <= 0)
            throw new RuntimeException("Preço diário tem de ser positivo!");

        return veiculoRepository.save(veiculo);
    }

     // Listar todos
    public List<Veiculo> listarTodos() {
        return veiculoRepository.findAll();
    }

    // Listar disponíveis
    public List<Veiculo> listarDisponiveis() {
        return veiculoRepository.findByStatus(Status.DISPONIVEL);
    }

    // Buscar por ID
    public Veiculo buscarId(long id) {
        return veiculoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Veículo não encontrado!"));
    }

    // Filtros
    public List<Veiculo> filtrarPorMarca(String marca) {
        return veiculoRepository.findByMarca(marca);
    }

    public List<Veiculo> filtrarPorCategoria(Categoria categoria) {
        return veiculoRepository.findByCategoria(categoria);
    }

    public List<Veiculo> filtrarPorTipoCaixa(Tipo_Caixa tipoCaixa) {
        return veiculoRepository.findByTipoCaixa(tipoCaixa);
    }

    public List<Veiculo> filtrarPorTipoCombustao(Tipo_Combustao tipoCombustao) {
        return veiculoRepository.findByTipoCombustao(tipoCombustao);
    }

    public List<Veiculo> filtrarPorTipoMotor(Tipo_Motor tipoMotor) {
        return veiculoRepository.findByTipoMotor(tipoMotor);
    }

    // Atualizar status
    public Veiculo atualizarStatus(long id, Status novoStatus) {
        Veiculo veiculo = buscarId(id);
        veiculo.setStatus(novoStatus);
        return veiculoRepository.save(veiculo);
    }

    // Apagar
    public void apagar(long id) {
        if (!veiculoRepository.existsById(id))
            throw new RuntimeException("Veículo não encontrado!");
        veiculoRepository.deleteById(id);
    }


}
