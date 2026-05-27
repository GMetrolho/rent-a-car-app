package service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import enums.Status;
import enums.Tipo_Caixa;
import enums.Tipo_Combustao;
import enums.Tipo_Motor;
import model.Categoria;
import model.Veiculo;
import repository.VeiculoRepository;

@Service
public class VeiculoService {

  @Autowired
  private VeiculoRepository veiculoRepository;

  // Adicionar
    public Veiculo adicionar(Veiculo veiculo) {
        if (veiculo.getMatricula() == null || veiculo.getMatricula().isBlank())
            throw new RuntimeException("Matrícula é obrigatória!");
        if (veiculo.getMarca() == null || veiculo.getMarca().isBlank())
            throw new RuntimeException("Marca é obrigatória!");
        if (veiculo.getModelo() == null || veiculo.getModelo().isBlank())
            throw new RuntimeException("Modelo é obrigatório!");
        if (veiculo.getPrecoDiario() <= 0)
            throw new RuntimeException("Preço diário tem de ser positivo!");

        return veiculoRepository.save(veiculo);
    }

     // Listar todos
    public List<Veiculo> listarTodos() {
        return veiculoRepository.findAll();
    }

    // Listar disponíveis
    public List<Veiculo> listarDisponiveis() {
        return veiculoRepository.findByStatus(Status.DISPONIVEL);
    }

    // Buscar por ID
    public Veiculo buscarId(long id) {
        return veiculoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Veículo não encontrado!"));
    }

    // Filtros
    public List<Veiculo> filtrarPorMarca(String marca) {
        return veiculoRepository.findByMarca(marca);
    }

    public List<Veiculo> filtrarPorCategoria(Categoria categoria) {
        return veiculoRepository.findByCategoria(categoria);
    }

    public List<Veiculo> filtrarPorTipoCaixa(Tipo_Caixa tipoCaixa) {
        return veiculoRepository.findByTipoCaixa(tipoCaixa);
    }

    public List<Veiculo> filtrarPorTipoCombustao(Tipo_Combustao tipoCombustao) {
        return veiculoRepository.findByTipoCombustao(tipoCombustao);
    }

    public List<Veiculo> filtrarPorTipoMotor(Tipo_Motor tipoMotor) {
        return veiculoRepository.findByTipoMotor(tipoMotor);
    }


    // Atualizar status
    public Veiculo atualizarStatus(long id, Status novoStatus) {
        Veiculo veiculo = buscarId(id);
        veiculo.setStatus(novoStatus);
        return veiculoRepository.save(veiculo);
    }

    public Veiculo atualizar(long id, Veiculo dadosAtualizados) {
    Veiculo veiculoExistente = buscarId(id);

    // 2. Validações básicas idênticas às do adicionar
    if (dadosAtualizados.getMatricula() == null || dadosAtualizados.getMatricula().isBlank())
        throw new RuntimeException("Matrícula é obrigatória!");
    if (dadosAtualizados.getMarca() == null || dadosAtualizados.getMarca().isBlank())
        throw new RuntimeException("Marca é obrigatória!");
    if (dadosAtualizados.getModelo() == null || dadosAtualizados.getModelo().isBlank())
        throw new RuntimeException("Modelo é obrigatório!");
    if (dadosAtualizados.getPrecoDiario() <= 0)
        throw new RuntimeException("Preço diário tem de ser positivo!");

    // 3. Atualiza os dados do veículo existente com os novos dados vindos do formulário
    veiculoExistente.setMatricula(dadosAtualizados.getMatricula());
    veiculoExistente.setMarca(dadosAtualizados.getMarca());
    veiculoExistente.setModelo(dadosAtualizados.getModelo());
    veiculoExistente.setAno(dadosAtualizados.getAno());
    veiculoExistente.setPrecoDiario(dadosAtualizados.getPrecoDiario());
    veiculoExistente.setStatus(dadosAtualizados.getStatus());
    veiculoExistente.setTipoMotor(dadosAtualizados.getTipoMotor());
    veiculoExistente.setTipoCaixa(dadosAtualizados.getTipoCaixa());
    veiculoExistente.setTipoCombustao(dadosAtualizados.getTipoCombustao());
    veiculoExistente.setCategoria(dadosAtualizados.getCategoria());

    // Atualiza também os novos campos técnicos (Cilindrada, CO2, Potência)
    veiculoExistente.setCilindrada(dadosAtualizados.getCilindrada());
    veiculoExistente.setCo2(dadosAtualizados.getCo2());
    veiculoExistente.setPotencia(dadosAtualizados.getPotencia());

    // 4. Guarda o objeto atualizado (o JPA vai fazer o UPDATE por causa do ID fixado)
    return veiculoRepository.save(veiculoExistente);
    }

    // Apagar
    public void apagar(long id) {
        if (!veiculoRepository.existsById(id))
            throw new RuntimeException("Veículo não encontrado!");
        veiculoRepository.deleteById(id);
    }

    public String guardarImagem(long id, MultipartFile ficheiro) throws IOException {
        Veiculo veiculo = buscarId(id);

        String nomeImagem = veiculo.getMarca().toLowerCase() + "_" +
                            veiculo.getModelo().toLowerCase().replace(" ", "_") + ".png";

        java.nio.file.Path path = java.nio.file.Paths.get("src/main/resources/static/img/", nomeImagem);
        java.nio.file.Files.createDirectories(path.getParent());
        java.nio.file.Files.write(path, ficheiro.getBytes());

        return nomeImagem;
    }


}
