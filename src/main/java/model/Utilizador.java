package model;

import enums.Cargo;
import jakarta.persistence.*;

@Entity
@Table(name = "utilizadores")
public class Utilizador {

    //region Variables
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    @Column(name = "nome", nullable = false)
    protected String nome;
    @Column(name = "email", nullable = false, unique = true)
    protected String email;
    @Column(name = "password", nullable = false)
    protected String password;
    @Enumerated(EnumType.STRING)
    protected Cargo cargo;
    //#endregion

    //#region Contructors
    public Utilizador() {
    }

    public Utilizador(Long id, String nome, String email, String password, Cargo cargo) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.password = password;
        this.cargo = cargo;
    }
    //#endregion

    //#region Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
