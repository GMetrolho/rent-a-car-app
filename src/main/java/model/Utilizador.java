package model;

import enums.Cargo;
import jakarta.persistence.*;

@Entity
@Table(name = "utilizadores")
public class Utilizador {

    //region Variables
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long ID;
    @Column(name = "name", nullable = false)
    protected String nome;
    @Column(name = "email", nullable = false, unique = true)
    protected String email;
    @Column(name = "passowrd", nullable = false)
    protected String password;
    @Enumerated(EnumType.STRING)
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
    public long getID() {
        return ID;
    }

    public void setID(long ID) {
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
