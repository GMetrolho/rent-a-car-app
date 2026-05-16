package controler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import enums.Status;
import model.Veiculo;
import service.VeiculoService;

@RestController
@RequestMapping("/veiculos")
public class VeiculoController {

    @Autowired
    private VeiculoService veiculoService;

    // GET /veiculos — listar todos
    @GetMapping
    public List<Veiculo> listarTodos() {
        return veiculoService.listarTodos();
    }

    // GET /veiculos/disponiveis — listar disponíveis
    @GetMapping("/disponiveis")
    public List<Veiculo> listarDisponiveis() {
        return veiculoService.listarDisponiveis();
    }

    // GET /veiculos/{id} — buscar por id
    @GetMapping("/{id}")
    public ResponseEntity<Veiculo> buscarPorId(@PathVariable long id) {
        return ResponseEntity.ok(veiculoService.buscarPorId(id));
    }

    // POST /veiculos — adicionar
    @PostMapping
    public ResponseEntity<Veiculo> adicionar(@RequestBody Veiculo veiculo) {
        return ResponseEntity.ok(veiculoService.adicionar(veiculo));
    }

    // PUT /veiculos/{id}/status — atualizar status
    @PutMapping("/{id}/status")
    public ResponseEntity<Veiculo> atualizarStatus(@PathVariable long id, @RequestParam Status status) {
        return ResponseEntity.ok(veiculoService.atualizarStatus(id, status));
    }

    // DELETE /veiculos/{id} — apagar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagar(@PathVariable long id) {
        veiculoService.apagar(id);
        return ResponseEntity.noContent().build();
    }
}