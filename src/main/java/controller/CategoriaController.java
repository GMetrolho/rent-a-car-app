package controller;

import model.Categoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.CategoriaService;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    // GET /categorias — listar todas
    @GetMapping
    public List<Categoria> listarTodas() {
        return categoriaService.listarTodas();
    }

    // GET /categorias/{id} — buscar por id
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> buscarPorId(@PathVariable long id) {
        return ResponseEntity.ok(categoriaService.buscarPorId(id));
    }

    // POST /categorias — adicionar
    @PostMapping
    public ResponseEntity<Categoria> adicionar(@RequestBody Categoria categoria) {
        return ResponseEntity.ok(categoriaService.adicionar(categoria));
    }

    // DELETE /categorias/{id} — apagar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagar(@PathVariable long id) {
        categoriaService.apagar(id);
        return ResponseEntity.noContent().build();
    }
}
