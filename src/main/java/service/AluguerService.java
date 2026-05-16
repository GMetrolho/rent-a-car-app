package service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import enums.Status;
import enums.Status_Aluguer;
import model.Aluguer;
import model.Utilizador;
import model.Veiculo;
import repository.AluguerRepository;

@Service
public class AluguerService {

    @Autowired
    private AluguerRepository aluguerRepository;
    @Autowired
    private VeiculoService veiculoService;

    // Criar reserva
    public Aluguer criarReserva(Veiculo veiculo, Utilizador cliente, LocalDateTime dataLevantamento, LocalDateTime dataDevolucao) {
        if (veiculo.getStatus() != Status.DISPONIVEL)
            throw new RuntimeException("Veículo não está disponível!");
        if (dataLevantamento == null || dataDevolucao == null)
            throw new RuntimeException("Datas são obrigatórias!");
        if (!dataDevolucao.isAfter(dataLevantamento))
            throw new RuntimeException("Data de devolução tem de ser depois do levantamento!");

        // Calcular valor previsto
        long dias = ChronoUnit.DAYS.between(dataLevantamento, dataDevolucao);
        double valorPrevisto = dias * veiculo.getPrecoDiario();

        // Criar o aluguer
        Aluguer aluguer = new Aluguer();
        aluguer.setVeiculo(veiculo);
        aluguer.setCliente(cliente);
        aluguer.setDataReserva(LocalDateTime.now());
        aluguer.setDataLevantamento(dataLevantamento);
        aluguer.setDataDevolucao(dataDevolucao);
        aluguer.setValorFinalPrevisto(valorPrevisto);
        aluguer.setStatusAluguer(Status_Aluguer.PENDENTE);

        // Bloquear o veículo
        veiculoService.atualizarStatus(veiculo.getId(), Status.ALUGADO);

        return aluguerRepository.save(aluguer);
    }

    // Confirmar levantamento
    public Aluguer confirmarLevantamento(long id) {
        Aluguer aluguer = buscarPorId(id);
        if (aluguer.getStatusAluguer() != Status_Aluguer.PENDENTE)
            throw new RuntimeException("Só é possível confirmar alugueres pendentes!");

        aluguer.setStatusAluguer(Status_Aluguer.ATIVA);
        return aluguerRepository.save(aluguer);
    }

    // Devolver
    public Aluguer devolver(long id) {
        Aluguer aluguer = buscarPorId(id);
        if (aluguer.getStatusAluguer() != Status_Aluguer.ATIVA)
            throw new RuntimeException("Só é possível devolver alugueres ativos!");

        aluguer.setStatusAluguer(Status_Aluguer.CONCLUIDA);
        veiculoService.atualizarStatus(aluguer.getVeiculo().getId(), Status.DISPONIVEL);

        return aluguerRepository.save(aluguer);
    }

    // Cancelar
    public Aluguer cancelar(long id) {
        Aluguer aluguer = buscarPorId(id);
        if (aluguer.getStatusAluguer() == Status_Aluguer.CONCLUIDA)
            throw new RuntimeException("Não é possível cancelar um aluguer já concluído!");

        aluguer.setStatusAluguer(Status_Aluguer.CANCELADO);
        veiculoService.atualizarStatus(aluguer.getVeiculo().getId(), Status.DISPONIVEL);

        return aluguerRepository.save(aluguer);
    }

    // Buscar por ID
    public Aluguer buscarPorId(long id) {
        return aluguerRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Aluguer não encontrado!"));
    }

    // Listar todos
    public List<Aluguer> listarTodos() {
        return aluguerRepository.findAll();
    }
}