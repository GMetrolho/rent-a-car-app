package controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import model.Utilizador;
import service.UtilizadorService;

@RestController
@RequestMapping("/utilizadores")
public class UtilizadorController {

    @Autowired
    private UtilizadorService utilizadorService;

    // GET /utilizadores — listar todos
    @GetMapping
    public List<Utilizador> listarTodos() {
        return utilizadorService.listarTodos();
    }

    // GET /utilizadores/{id} — buscar por id
    @GetMapping("/{id}")
    public ResponseEntity<Utilizador> buscarPorId(@PathVariable long id) {
        return ResponseEntity.ok(utilizadorService.buscarId(id));
    }

    // POST /utilizadores/registar — registar
    @PostMapping("/registar")
    public ResponseEntity<Utilizador> registar(@RequestBody Utilizador utilizador) {
        return ResponseEntity.ok(utilizadorService.registar(utilizador));
    }

    record LoginRequest(String email, String password) {}

    // Substitui o método login
    @PostMapping("/login")
    public ResponseEntity<Utilizador> login(@RequestBody LoginRequest req) {
        return ResponseEntity.ok(utilizadorService.login(req.email(), req.password()));
}

    // DELETE /utilizadores/{id} — apagar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagar(@PathVariable long id) {
        utilizadorService.apagar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Utilizador> atualizar(@PathVariable long id, @RequestBody Utilizador utilizador) {
        return ResponseEntity.ok(utilizadorService.atualizar(id, utilizador));
    }


    @PutMapping("/{id}/cargo")
    public ResponseEntity<Utilizador> atualizarCargo(@PathVariable long id, @RequestBody Map<String, String> body) {
        String novoCargoStr = body.get("cargo");
        if (novoCargoStr == null || novoCargoStr.isBlank()) {
            throw new RuntimeException("O cargo é obrigatório!");
        }

        try {
            enums.Cargo cargo = enums.Cargo.valueOf(novoCargoStr.toUpperCase());
            return ResponseEntity.ok(utilizadorService.atualizarCargo(id, cargo));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Cargo inválido!");
        }
    }
}
