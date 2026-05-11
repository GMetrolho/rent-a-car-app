import model.Utilizador;
import service.MenuService;
import repository.UtilizadorRepository;

public class Main {
    public static void main(String[] args) {
        UtilizadorRepository repo = new UtilizadorRepository();

        // Vamos tentar fazer login com os dados que inseriste no MySQL
        Utilizador user = repo.login("adminteste123@gmail.com", "12345");

        if (user != null) {
            System.out.println("Bem-vindo, " + user.getNome() + "!");
            System.out.println("Teu cargo é: " + user.getCargo());
            MenuService menuService = new MenuService();
            menuService.ShowMenu(user);
        } else {
            System.out.println("Email ou password incorretos!");
        }


    }
}
