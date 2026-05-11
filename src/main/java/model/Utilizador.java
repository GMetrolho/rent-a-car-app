package model;

import enums.Cargo;

public class Utilizador {

    //region Variables
    protected int ID;
    protected String nome;
    protected String email;
    protected String password;
    protected Cargo cargo;
    //#endregion

    //#region Contructors
    public Utilizador() {
    }

    public Utilizador(int ID, String nome, String email, String password, Cargo cargo) {
        this.ID = ID;
        this.nome = nome;
        this.email = email;
        this.password = password;
        this.cargo = cargo;
    }
    //#endregion

    //#region Getters and Setters
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }
    //#endregion

}
