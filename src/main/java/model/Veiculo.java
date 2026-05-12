package model;

//imports dos enums
import enums.Status;
import enums.Tipo_Caixa;
import enums.Tipo_Combustao;
import enums.Tipo_Motor;
// JPA
import jakarta.persistence.*;


@Entity
@Table(name = "veiculo")
public class Veiculo {

    //#region Variables
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "matricula", unique = true, nullable = false)
    private String matricula;
    @Column(name = "marca", unique = false, nullable = false)
    private String marca;
    @Column(name = "modelo", unique = false, nullable = false)
    private String modelo;
    @Column(name = "ano", unique = false, nullable = false)
    private int ano;
    @Column(name = "preco_diario")
    private double preco_diario;

    //Enums
    @Enumerated(EnumType.STRING)
    private Status status;
    @Enumerated(EnumType.STRING)
    private Tipo_Caixa tipo_caixa;
    @Enumerated(EnumType.STRING)
    private Tipo_Combustao tipo_combustao;
    @Enumerated(EnumType.STRING)
    private Tipo_Motor tipo_motor;

    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;
    //#endregion


    //#region Contructors

    //Usado para o Java/Framework trabalhar sozinho com a base de dados
    public Veiculo() {
    }

    //Usado diretamento no código
    public Veiculo(Long id, String matricula, String marca, String modelo, int ano, double preco_diario, Status status,
            Tipo_Caixa tipo_caixa, Tipo_Combustao tipo_combustao, Tipo_Motor tipo_motor, Categoria categoria) {
        this.id = id;
        this.matricula = matricula;
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.preco_diario = preco_diario;

        this.status = status;
        this.tipo_caixa = tipo_caixa;
        this.tipo_combustao = tipo_combustao;
        this.tipo_motor = tipo_motor;

        this.categoria = categoria;

    }
    //#endregion


    //#region Getters and Setters

    //ID
    public Long getID() {
        return id;
    }
    public void setID(Long id) {
        this.id = id;
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

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria){
        this.categoria = categoria;

    }

    //#endregion


}


