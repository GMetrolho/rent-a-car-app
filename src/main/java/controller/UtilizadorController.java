package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    // POST /utilizadores/login — login
    @PostMapping("/login")
    public ResponseEntity<Utilizador> login(@RequestParam String email, @RequestParam String password) {
        return ResponseEntity.ok(utilizadorService.login(email, password));
    }

    // DELETE /utilizadores/{id} — apagar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagar(@PathVariable long id) {
        utilizadorService.apagar(id);
        return ResponseEntity.noContent().build();
    }
}
