package service;

import model.Utilizador;

public class MenuService {

        public void ShowMenu(Utilizador user){
            System.out.println("--------------------------------- BEM-VINDO AO STAND AUTOMOVEL ---------------------------------"); 
            System.out.println("Olá " + user.getCargo() + "" + user.getNome() + ", seja bem-vindo ao nosso stand de automóveis!");
            System.out.println("------------------------------------------------------------------------------------------------");


            switch (user.getCargo()) {
                case ADMIN -> menuAdmin();
                case FUNCIONARIO -> menuFuncionario();
                case CLIENTE -> menuCliente();
                default -> System.out.println("Cargo desconhecido. Acesso negado.");
            }
        }


        //A definir, falar com o Rocha para perceber o que é necessário para cada menu
        private void menuAdmin(){
            System.out.println("Menu Admin:");
            System.out.println("1. Gerir Veículos");
            System.out.println("2. Gerir Utilizadores");
            System.out.println("3. Gerir Clientes");
            System.out.println("4. Gerir Alugueres");
            System.out.println("5. Sair");
        }

        private void menuFuncionario(){
            System.out.println("Menu Funcionário:");
            System.out.println("1. Gerir Veículos");
            System.out.println("2. Gerir Alugueres");
            System.out.println("3. Sair");
        }

        private void menuCliente(){
            System.out.println("Menu Cliente:");
            System.out.println("1. Ver Veículos Disponíveis");
            System.out.println("2. Fazer Reserva");
            System.out.println("3. Sair");
        }
    }

