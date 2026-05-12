package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import database.Conexao;
import enums.Status;
import enums.Tipo_Caixa;
import enums.Tipo_Combustao;
import enums.Tipo_Motor;
import model.Veiculo;


public class VeiculoRepositoryAntigo {

    // Método para inserir um novo veículo (Create)
    public void insert_veiculo(Veiculo veiculo) {
        String sql = "INSERT INTO veiculos (matricula, marca, modelo, ano, preco_diario, status, tipo_motor, tipo_combustao, tipo_caixa) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, veiculo.getMatricula());
            stmt.setString(2, veiculo.getMarca());
            stmt.setString(3, veiculo.getModelo());
            stmt.setInt(4, veiculo.getAno());
            stmt.setDouble(5, veiculo.getPreco_diario());
            stmt.setString(6, veiculo.getStatus().name());
            stmt.setString(7, veiculo.getTipo_motor().name());
            stmt.setString(8, veiculo.getTipo_combustao().name());
            stmt.setString(9, veiculo.getTipo_caixa().name());

            stmt.executeUpdate();
            System.out.println("Veículo guardado com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro ao salvar veículo: " + e.getMessage());
        }
    }

    // Método para listar todos os veículos (Read)
    public List<Veiculo> listarTodos() {
        List<Veiculo> lista = new ArrayList<>();
        String sql = "SELECT * FROM veiculos";

        try (Connection conn = Conexao.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Veiculo v = new Veiculo();
                v.setID(rs.getLong("id"));
                v.setMatricula(rs.getString("matricula"));
                v.setMarca(rs.getString("marca"));
                v.setModelo(rs.getString("modelo"));
                v.setAno(rs.getInt("ano"));
                v.setPreco_diario(rs.getDouble("preco_diario"));

                // Converter String da BD de volta para Enum
                v.setStatus(Status.valueOf(rs.getString("status")));
                v.setTipo_motor(Tipo_Motor.valueOf(rs.getString("tipo_motor")));
                v.setTipo_combustao(Tipo_Combustao.valueOf(rs.getString("tipo_combustao")));
                v.setTipo_caixa(Tipo_Caixa.valueOf(rs.getString("tipo_caixa")));

                lista.add(v);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar veículos: " + e.getMessage());
        }
        return lista;
    }
}
