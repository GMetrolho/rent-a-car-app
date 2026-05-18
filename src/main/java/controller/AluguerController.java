package controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    // GET /alugueres — listar todos
    @GetMapping
    public List<Aluguer> listarTodos() {
        return aluguerService.listarTodos();
    }

    // GET /alugueres/{id} — buscar por id
    @GetMapping("/{id}")
    public ResponseEntity<Aluguer> buscarPorId(@PathVariable long id) {
        return ResponseEntity.ok(aluguerService.buscarPorId(id));
    }

    // POST /alugueres — criar reserva
    @PostMapping
    public ResponseEntity<Aluguer> criarReserva(
            @RequestParam Long idVeiculo,
            @RequestParam Long idCliente,
            @RequestParam String dataLevantamento,
            @RequestParam String dataDevolucao) {

        Veiculo veiculo = veiculoService.buscarId(idVeiculo);
        Utilizador cliente = utilizadorService.buscarId(idCliente);
        LocalDateTime levantamento = LocalDateTime.parse(dataLevantamento);
        LocalDateTime devolucao = LocalDateTime.parse(dataDevolucao);

        return ResponseEntity.ok(aluguerService.criarReserva(veiculo, cliente, levantamento, devolucao));
    }

    // PUT /alugueres/{id}/confirmar — confirmar levantamento
    @PutMapping("/{id}/confirmar")
    public ResponseEntity<Aluguer> confirmar(@PathVariable long id) {
        return ResponseEntity.ok(aluguerService.confirmarLevantamento(id));
    }

    // PUT /alugueres/{id}/devolver — devolver
    @PutMapping("/{id}/devolver")
    public ResponseEntity<Aluguer> devolver(@PathVariable long id) {
        return ResponseEntity.ok(aluguerService.devolver(id));
    }

    // PUT /alugueres/{id}/cancelar — cancelar
    @PutMapping("/{id}/cancelar")
    public ResponseEntity<Aluguer> cancelar(@PathVariable long id) {
        return ResponseEntity.ok(aluguerService.cancelar(id));
    }
}
