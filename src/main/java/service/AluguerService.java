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

    // ─────────────────────────────────────────
    // CRIAR RESERVA → PENDENTE
    // ─────────────────────────────────────────
    public Aluguer criarReserva(Veiculo veiculo, Utilizador cliente,
                                LocalDateTime dataLevantamento, LocalDateTime dataDevolucao) {

        if (veiculo.getStatus() != Status.DISPONIVEL)
            throw new RuntimeException("Veículo não está disponível!");
        if (dataLevantamento == null || dataDevolucao == null)
            throw new RuntimeException("Datas são obrigatórias!");
        if (dataLevantamento.isBefore(LocalDateTime.now()))
            throw new RuntimeException("Data de levantamento não pode ser no passado!");
        if (!dataDevolucao.isAfter(dataLevantamento))
            throw new RuntimeException("Data de devolução tem de ser depois do levantamento!");

        long dias = ChronoUnit.DAYS.between(dataLevantamento, dataDevolucao);
        if (dias < 1) dias = 1;

        double valorPrevisto = dias * veiculo.getPrecoDiario();

        Aluguer aluguer = new Aluguer();
        aluguer.setVeiculo(veiculo);
        aluguer.setCliente(cliente);
        aluguer.setDataReserva(LocalDateTime.now());
        aluguer.setDataLevantamento(dataLevantamento);
        aluguer.setDataDevolucao(dataDevolucao);
        aluguer.setValorFinalPrevisto(valorPrevisto);
        aluguer.setStatusAluguer(Status_Aluguer.PENDENTE);

        // Marcar veículo como alugado logo na reserva
        veiculoService.atualizarStatus(veiculo.getId(), Status.ALUGADO);

        return aluguerRepository.save(aluguer);
    }

    // ─────────────────────────────────────────
    // CONFIRMAR → PENDENTE para LEVANTAR
    // ─────────────────────────────────────────
    public Aluguer confirmarLevantamento(long id) {
        Aluguer aluguer = buscarPorId(id);
        if (aluguer.getStatusAluguer() != Status_Aluguer.PENDENTE)
            throw new RuntimeException("Só é possível confirmar alugueres pendentes!");

        aluguer.setStatusAluguer(Status_Aluguer.LEVANTAR);
        return aluguerRepository.save(aluguer);
    }

    // ─────────────────────────────────────────
    // LEVANTAR → LEVANTAR para ATIVO
    // ─────────────────────────────────────────
    public Aluguer registarLevantamento(long id) {
        Aluguer aluguer = buscarPorId(id);
        if (aluguer.getStatusAluguer() != Status_Aluguer.LEVANTAR)
            throw new RuntimeException("O aluguer ainda não está confirmado para levantamento!");

        aluguer.setStatusAluguer(Status_Aluguer.ATIVA);
        return aluguerRepository.save(aluguer);
    }

    // ─────────────────────────────────────────
    // DEVOLVER → ATIVO para DEVOLVER
    // ─────────────────────────────────────────
    public Aluguer pedirDevolucao(long id) {
        Aluguer aluguer = buscarPorId(id);
        if (aluguer.getStatusAluguer() != Status_Aluguer.ATIVA)
            throw new RuntimeException("Só é possível devolver alugueres ativos!");

        aluguer.setStatusAluguer(Status_Aluguer.DEVOLVER);
        return aluguerRepository.save(aluguer);
    }

    // ─────────────────────────────────────────
    // MANUTENÇÃO → DEVOLVER para MANUTENCAO
    // ─────────────────────────────────────────
    public Aluguer enviarParaManutencao(long id) {
        Aluguer aluguer = buscarPorId(id);
        if (aluguer.getStatusAluguer() != Status_Aluguer.DEVOLVER)
            throw new RuntimeException("O carro tem de estar em devolução para ir para manutenção!");

        aluguer.setStatusAluguer(Status_Aluguer.MANUTENCAO);
        veiculoService.atualizarStatus(aluguer.getVeiculo().getId(), Status.MANUTENCAO);
        return aluguerRepository.save(aluguer);
    }

    // ─────────────────────────────────────────
    // CONCLUIR → DEVOLVER ou MANUTENCAO para CONCLUIDA
    // ─────────────────────────────────────────
    public Aluguer concluir(long id) {
        Aluguer aluguer = buscarPorId(id);
        if (aluguer.getStatusAluguer() != Status_Aluguer.DEVOLVER &&
            aluguer.getStatusAluguer() != Status_Aluguer.MANUTENCAO)
            throw new RuntimeException("Não é possível concluir neste estado!");

        aluguer.setStatusAluguer(Status_Aluguer.CONCLUIDA);
        veiculoService.atualizarStatus(aluguer.getVeiculo().getId(), Status.DISPONIVEL);
        return aluguerRepository.save(aluguer);
    }

    // ─────────────────────────────────────────
    // CANCELAR → qualquer estado antes de ATIVO
    // ─────────────────────────────────────────
    public Aluguer cancelar(long id) {
        Aluguer aluguer = buscarPorId(id);
        if (aluguer.getStatusAluguer() == Status_Aluguer.ATIVA   ||
            aluguer.getStatusAluguer() == Status_Aluguer.CONCLUIDA)
            throw new RuntimeException("Não é possível cancelar um aluguer ativo ou concluído!");

        aluguer.setStatusAluguer(Status_Aluguer.CANCELADO);
        veiculoService.atualizarStatus(aluguer.getVeiculo().getId(), Status.DISPONIVEL);
        return aluguerRepository.save(aluguer);
    }

    // ─────────────────────────────────────────
    // UTILS
    // ─────────────────────────────────────────
    public Aluguer buscarPorId(long id) {
        return aluguerRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Aluguer não encontrado!"));
    }

    public List<Aluguer> listarTodos() {
        return aluguerRepository.findAll();
    }
}
