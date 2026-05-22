package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import enums.Tipo_Conta;
import model.Utilizador;
import repository.UtilizadorRepository;

@Service
public class UtilizadorService {

    @Autowired
    private UtilizadorRepository utilizadorRepository;

    // ─────────────────────────────────────────
    // REGISTAR
    // ─────────────────────────────────────────
    public Utilizador registar(Utilizador utilizador) {
        if (utilizador.getNome() == null || utilizador.getNome().isBlank())
            throw new RuntimeException("Nome é obrigatório!");
        if (utilizador.getEmail() == null || utilizador.getEmail().isBlank())
            throw new RuntimeException("Email é obrigatório!");
        if (utilizador.getPassword() == null || utilizador.getPassword().isBlank())
            throw new RuntimeException("Password é obrigatória!");
        if (utilizador.getPassword().length() < 8)
            throw new RuntimeException("Password demasiado curta. Mínimo 8 caracteres!");
        if (utilizador.getTipoConta() == null)
            throw new RuntimeException("Tipo de conta é obrigatório!");

        // Empresa obriga NIF
        if (utilizador.getTipoConta() == Tipo_Conta.EMPRESA) {
            if (utilizador.getNif() == null || utilizador.getNif().isBlank())
                throw new RuntimeException("NIF é obrigatório para contas de empresa!");
        }

        if (utilizadorRepository.existsByEmail(utilizador.getEmail()))
            throw new RuntimeException("Email já registado!");

        return utilizadorRepository.save(utilizador);
    }

    // ─────────────────────────────────────────
    // LOGIN
    // ─────────────────────────────────────────
    public Utilizador login(String email, String password) {
        if (email == null || email.isBlank())
            throw new RuntimeException("Email é obrigatório!");
        if (password == null || password.isBlank())
            throw new RuntimeException("Password é obrigatória!");

        Utilizador utilizador = utilizadorRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Email não encontrado!"));

        if (!utilizador.getPassword().equals(password))
            throw new RuntimeException("Password incorreta!");

        return utilizador;
    }

    // ─────────────────────────────────────────
    // CRUD
    // ─────────────────────────────────────────
    public List<Utilizador> listarTodos() {
        return utilizadorRepository.findAll();
    }

    public Utilizador buscarId(long id) {
        return utilizadorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Utilizador não encontrado!"));
    }

    public Utilizador buscarEmail(String email) {
        return utilizadorRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Email não encontrado!"));
    }

    public Utilizador atualizarCargo(long id, enums.Cargo novoCargo) {
        Utilizador utilizador = buscarId(id);
        utilizador.setCargo(novoCargo);
        return utilizadorRepository.save(utilizador);
    }

    public void apagar(long id) {
        if (!utilizadorRepository.existsById(id))
            throw new RuntimeException("Utilizador não encontrado!");
        utilizadorRepository.deleteById(id);
        }
}
