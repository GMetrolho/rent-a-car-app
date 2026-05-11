import model.Utilizador;
import repository.UtilizadorRepository;

public class Main {
    public static void main(String[] args) {
        UtilizadorRepository repo = new UtilizadorRepository();
        
        // Vamos tentar fazer login com os dados que inseriste no MySQL
        Utilizador user = repo.login("adminteste123@gmail.com", "12345");
        
        if (user != null) {
            System.out.println("Bem-vindo, " + user.getNome() + "!");
            System.out.println("Teu cargo é: " + user.getCargo());
        } else {
            System.out.println("Email ou password incorretos!");
        }
    }
}
