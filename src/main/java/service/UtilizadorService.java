package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import model.Utilizador;
import repository.UtilizadorRepository;

@Service
public class UtilizadorService {

  @Autowired
  private UtilizadorRepository utilizadorRepository;

  public Utilizador registar(Utilizador utilizador) {
    // Verificações Obrigatórias
    if (utilizador.getNome() == null || utilizador.getNome().isBlank()) {
        throw new RuntimeException("Nome é obrigatório!");
    }
    if (utilizador.getEmail() == null || utilizador.getEmail().isBlank()) {
        throw new RuntimeException("Email é obrigatório!");
    }
    if (utilizador.getPassword() == null || utilizador.getPassword().isBlank()) {
        throw new RuntimeException("Password é obrigatória!");
    }

    if (utilizadorRepository.existsByEmail(utilizador.getEmail())) {
      throw new RuntimeException("Email já registado.");
    }
    
    return utilizadorRepository.save(utilizador);
  }

  public Utilizador login(String email, String password) {
    // Verificações Obrigatórias
    if (email == null || email.isBlank()) {
      throw new RuntimeException("Email é obrigatório!");
    }
    if (password == null || password.isBlank()){
      throw new RuntimeException("Password é obrigatória!");
    }
    if (password.length() <= 7) {
      throw new RuntimeException("Password curta. Mínimo 8 caracteres!");
    }

    Utilizador utilizador = utilizadorRepository.findByEmail(email)
      .orElseThrow(() -> new RuntimeException("Email não encontrado!"));

    if (!utilizador.getPassword().equals(password)) {
      throw new RuntimeException("Password Incorreta!");
    }
    return utilizador;
  }

  public List<Utilizador> listarTodos() {
    return utilizadorRepository.findAll();
  }

  public Utilizador buscarId(long id) {
    return utilizadorRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Utilizador não encontrado!"));
  }

  public Utilizador buscarEmail(String email) {
    return utilizadorRepository.findByEmail(email)
      .orElseThrow(() -> new RuntimeException("Email não encontrado"));
  }

  public void apagar(long id) {
    if (!utilizadorRepository.existsById(id)) {
      throw new RuntimeException("Este ID não esta atribuido a nenhum Utilizador");
    }
    utilizadorRepository.deleteById(id);
  }

}
