package model;

import jakarta.persistence.*;

@Entity
@Table (name = "categoria")
public class Categoria {

    //#region Variables
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nome", nullable = false)
    private String nome;
    @Column(name = "descricao")
    private String descricao;
    //#endregion

    //#region Constructors
    public Categoria() {
    }

    public Categoria(Long id, String nome, String descricao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
    }
    //#endregion

    //#region Getters and Setters
    public Long getID() {
        return id;
    }

    public void setID(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    //#endregion
}
