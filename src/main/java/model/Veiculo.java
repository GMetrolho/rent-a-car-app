package model;

//imports dos enums
import enums.Status;
import enums.Tipo_Caixa;
import enums.Tipo_Combustao;
import enums.Tipo_Motor;


public class Veiculo {

    //#region Variables
    private int Id;
    private String matricula;
    private String marca;
    private String modelo;
    private int ano;
    private double preco_diario;
  
    //Enums
    private Status status;
    private Tipo_Caixa tipo_caixa;
    private Tipo_Combustao tipo_combustao;
    private Tipo_Motor tipo_motor;
    //#endregion


    //#region Contructors

    //Usado para o Java/Framework trabalhar sozinho com a base de dados
    public Veiculo() {
    }

    //Usado diretamento no código
    public Veiculo(int id, String matricula, String marca, String modelo, int ano, double preco_diario, Status status,
            Tipo_Caixa tipo_caixa, Tipo_Combustao tipo_combustao, Tipo_Motor tipo_motor) {
        this.Id = id;
        this.matricula = matricula;
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.preco_diario = preco_diario;

        this.status = status;
        this.tipo_caixa = tipo_caixa;
        this.tipo_combustao = tipo_combustao;
        this.tipo_motor = tipo_motor;
    
    }
    //#endregion


    //#region Getters and Setters

    //ID
    public int getId() {
        return Id;
    }
    public void setId(int id) {
        this.Id = id;
    }

    //Matricula
    public String getMatricula() {
        return matricula;
    }
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    //Marca
    public String getMarca() {
        return marca;
    }
    public void setMarca(String marca) {
        this.marca = marca;
    }

    //Modelo
    public String getModelo() {
        return modelo;
    }
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    //Ano
    public int getAno() {
        return ano;
    }
    public void setAno(int ano) {
        this.ano = ano;
    }

    //Preço Diário
    public double getPreco_diario() {
        return preco_diario;
    }
    public void setPreco_diario(double preco_diario) {
        this.preco_diario = preco_diario;
    }

    //Enums
    public Tipo_Caixa getTipo_caixa() {
        return tipo_caixa;
    }
    public void setTipo_caixa(Tipo_Caixa tipo_caixa) {
        this.tipo_caixa = tipo_caixa;
    }

    public Tipo_Combustao getTipo_combustao() {
        return tipo_combustao;
    }
    public void setTipo_combustao(Tipo_Combustao tipo_combustao) {
        this.tipo_combustao = tipo_combustao;
    }

    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }

    public Tipo_Motor getTipo_motor() {
        return tipo_motor;
    }
    public void setTipo_motor(Tipo_Motor tipo_motor) {
        this.tipo_motor = tipo_motor;
    }

    //#endregion


}

    
