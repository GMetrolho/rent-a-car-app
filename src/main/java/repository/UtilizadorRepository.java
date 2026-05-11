package repository;

import java.sql.*;
import database.Conexao;
import enums.Cargo;
import model.Utilizador;

public class UtilizadorRepository {

    public Utilizador login(String email, String passoword){
        String sql = "SELECT * FROM utilizador WHERE email = ? AND password = ?";

        try (Connection conn = Conexao.getConexao()){
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, passoword);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Utilizador utilizador = new Utilizador();
                utilizador.setID(rs.getInt("ID"));
                utilizador.setNome(rs.getString("nome"));
                utilizador.setEmail(rs.getString("email"));
                utilizador.setPassword(rs.getString("password"));
                utilizador.setCargo(Cargo.valueOf(rs.getString("cargo")));
                return utilizador;
            }
            
        } catch (Exception e) {
            System.out.println("Erro ao realizar login: " + e.getMessage());
        }
        return null;
    }

    
}
