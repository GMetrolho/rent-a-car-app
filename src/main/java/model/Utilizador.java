package model;

import enums.Cargo;
import enums.Tipo_Conta;
import jakarta.persistence.*;

@Entity
@Table(name = "utilizadores")
public class Utilizador {

    //#region Variables
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

    // ── Novos campos (Fase 1 — ponto 5) ──
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_conta")
    protected Tipo_Conta tipoConta;      // PESSOAL ou EMPRESA

    @Column(name = "nif")
    protected String nif;               // Obrigatório para EMPRESA, opcional para PESSOAL

    @Column(name = "telefone")
    protected String telefone;
    // ──────────────────────────────────────
    //#endregion


    //#region Constructors
    public Utilizador() {}

    public Utilizador(Long id, String nome, String email, String password,
                      Cargo cargo, Tipo_Conta tipoConta, String nif, String telefone) {
        this.id        = id;
        this.nome      = nome;
        this.email     = email;
        this.password  = password;
        this.cargo     = cargo;
        this.tipoConta = tipoConta;
        this.nif       = nif;
        this.telefone  = telefone;
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

    public Tipo_Conta getTipoConta() {
        return tipoConta;
    }

    public void setTipoConta(Tipo_Conta tipoConta) {
        this.tipoConta = tipoConta;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    //#endregion
}
