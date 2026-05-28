package controller;

import model.Utilizador;
import service.JwtService;
import repository.UtilizadorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class AutenticacaoController {

    @Autowired
    private UtilizadorRepository utilizadorRepository;

    @Autowired
    private JwtService jwtService;

    // CORRIGIDO: era /login, agora bate certo com SecurityConfig e login.html
    @PostMapping("/utilizadores/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        Utilizador utilizador = utilizadorRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new RuntimeException("Utilizador não encontrado"));

        if (!request.getPassword().equals(utilizador.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
        }

        String token = jwtService.gerarToken(utilizador.getEmail(), utilizador.getCargo().name(), utilizador.getId());

        Map<String, Object> resposta = new HashMap<>();
        resposta.put("token", token);
        resposta.put("id", utilizador.getId());
        resposta.put("nome", utilizador.getNome());
        resposta.put("cargo", utilizador.getCargo());

        return ResponseEntity.ok(resposta);
    }
}

class LoginRequest {
    private String email;
    private String password;

    public LoginRequest() {}

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
