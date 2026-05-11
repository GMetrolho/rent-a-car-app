package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    public static Connection getConexao() {
        try {
            // URL do MySQL Workbench
            String url = "jdbc:mysql://localhost:3306/stand_automovel?useSSL=false&serverTimezone=UTC";
            return DriverManager.getConnection(url, "root", "");

        } catch (SQLException e) {
            System.err.println("Erro ao ligar ao MySQL: " + e.getMessage());
            return null;
        }
    }
}
