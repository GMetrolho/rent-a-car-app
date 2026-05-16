package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import model.Categoria;
import repository.CategoriaRepository;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Categoria adicionar(Categoria categoria) {
        if (categoria.getNome() == null || categoria.getNome().isBlank())
            throw new RuntimeException("Nome é obrigatório!");
        return categoriaRepository.save(categoria);
    }

    public List<Categoria> listarTodas() {
        return categoriaRepository.findAll();
    }

    public Categoria buscarPorId(long id) {
        return categoriaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Categoria não encontrada!"));
    }

    public void apagar(long id) {
        if (!categoriaRepository.existsById(id))
            throw new RuntimeException("Categoria não encontrada!");
        categoriaRepository.deleteById(id);
    }
}
