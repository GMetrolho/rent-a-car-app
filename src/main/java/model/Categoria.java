package model;

public class Categoria {

    //#region Variables
    private int idCategoria;
    private String nome;
    private String descricao;
    //#endregion

    //#region Constructors
    public Categoria() {
    }

    public Categoria(int idCategoria, String nome, String descricao) {
        this.idCategoria = idCategoria;
        this.nome = nome;
        this.descricao = descricao;
    }
    //#endregion

    //#region Getters and Setters
    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
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