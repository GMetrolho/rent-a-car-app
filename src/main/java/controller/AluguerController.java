package controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import model.Aluguer;
import model.Utilizador;
import model.Veiculo;
import service.AluguerService;
import service.UtilizadorService;
import service.VeiculoService;

@RestController
@RequestMapping("/alugueres")
public class AluguerController {

    @Autowired
    private AluguerService aluguerService;
    @Autowired
    private VeiculoService veiculoService;
    @Autowired
    private UtilizadorService utilizadorService;

    @GetMapping
    public List<Aluguer> listarTodos() {
        return aluguerService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Aluguer> buscarPorId(@PathVariable long id) {
        return ResponseEntity.ok(aluguerService.buscarPorId(id));
    }

    // POST /alugueres — Criar Reserva (Ajustado com DateTimeFormat para o HTML)
    @PostMapping
    public ResponseEntity<Aluguer> criarReserva(
            @RequestParam Long idVeiculo,
            @RequestParam Long idCliente,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime dataLevantamento,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime dataDevolucao) {

        Veiculo veiculo = veiculoService.buscarId(idVeiculo);
        Utilizador cliente = utilizadorService.buscarId(idCliente);

        return ResponseEntity.ok(aluguerService.criarReserva(veiculo, cliente, dataLevantamento, dataDevolucao));
    }

    // PUT /alugueres/{id}/confirmar — Passa de PENDENTE para LEVANTAR
    @PutMapping("/{id}/confirmar")
    public ResponseEntity<Aluguer> confirmar(@PathVariable long id) {
        return ResponseEntity.ok(aluguerService.confirmarLevantamento(id));
    }

    // PUT /alugueres/{id}/levantar — Passa de LEVANTAR para ATIVA
    @PutMapping("/{id}/levantar")
    public ResponseEntity<Aluguer> levantar(@PathVariable long id) {
        return ResponseEntity.ok(aluguerService.registarLevantamento(id));
    }

    // PUT /alugueres/{id}/devolver — Passa de ATIVA para DEVOLVER
    @PutMapping("/{id}/devolver")
    public ResponseEntity<Aluguer> devolver(@PathVariable long id) {
        return ResponseEntity.ok(aluguerService.pedirDevolucao(id));
    }

    // PUT /alugueres/{id}/manutencao — Passa de DEVOLVER para MANUTENCAO
    @PutMapping("/{id}/manutencao")
    public ResponseEntity<Aluguer> enviarParaManutencao(@PathVariable long id) {
        return ResponseEntity.ok(aluguerService.enviarParaManutencao(id));
    }

    // PUT /alugueres/{id}/concluir — Passa de DEVOLVER ou MANUTENCAO para CONCLUIDA
    @PutMapping("/{id}/concluir")
    public ResponseEntity<Aluguer> concluir(@PathVariable long id) {
        return ResponseEntity.ok(aluguerService.concluir(id));
    }

    // PUT /alugueres/{id}/cancelar — Cancelar reserva
    @PutMapping("/{id}/cancelar")
    public ResponseEntity<Aluguer> cancelar(@PathVariable long id) {
        return ResponseEntity.ok(aluguerService.cancelar(id));
    }
}
