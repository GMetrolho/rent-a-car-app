package model;

import enums.Status;
import enums.Tipo_Caixa;
import enums.Tipo_Combustao;
import enums.Tipo_Motor;
import jakarta.persistence.*;

@Entity
@Table(name = "veiculos")
public class Veiculo {

    //#region Variables
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "matricula", unique = true, nullable = false)
    private String matricula;
    @Column(name = "marca", nullable = false)
    private String marca;
    @Column(name = "modelo", nullable = false)
    private String modelo;
    @Column(name = "ano", nullable = false)
    private int ano;
    @Column(name = "preco_diario")
    private double precoDiario;

    // ── Novos campos (Fase 1 — ponto 15) ──
    @Column(name = "cilindrada")
    private Integer cilindrada;         // ex: 1600 (cm³)

    @Column(name = "potencia")
    private Integer potencia;           // ex: 150 (cv)

    @Column(name = "co2")
    private Integer co2;                // ex: 120 (g/km)
    // ──────────────────────────────────────

    @Enumerated(EnumType.STRING)
    private Status status;
    @Enumerated(EnumType.STRING)
    private Tipo_Caixa tipoCaixa;
    @Enumerated(EnumType.STRING)
    private Tipo_Combustao tipoCombustao;
    @Enumerated(EnumType.STRING)
    private Tipo_Motor tipoMotor;

    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;
    //#endregion


    //#region Constructors
    public Veiculo() {}

    public Veiculo(Long id, String matricula, String marca, String modelo, int ano,
                   double precoDiario, Integer cilindrada, Integer potencia, Integer co2,
                   Status status, Tipo_Caixa tipoCaixa, Tipo_Combustao tipoCombustao,
                   Tipo_Motor tipoMotor, Categoria categoria) {
        this.id           = id;
        this.matricula    = matricula;
        this.marca        = marca;
        this.modelo       = modelo;
        this.ano          = ano;
        this.precoDiario  = precoDiario;
        this.cilindrada   = cilindrada;
        this.potencia     = potencia;
        this.co2          = co2;
        this.status       = status;
        this.tipoCaixa    = tipoCaixa;
        this.tipoCombustao = tipoCombustao;
        this.tipoMotor    = tipoMotor;
        this.categoria    = categoria;
    }
    //#endregion

    //#region Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public double getPrecoDiario() {
        return precoDiario;
    }

    public void setPrecoDiario(double precoDiario) {
        this.precoDiario = precoDiario;
    }

    public Integer getCilindrada() {
        return cilindrada;
    }

    public void setCilindrada(Integer cilindrada) {
        this.cilindrada = cilindrada;
    }

    public Integer getPotencia() {
        return potencia;
    }

    public void setPotencia(Integer potencia) {
        this.potencia = potencia;
    }

    public Integer getCo2() {
        return co2;
    }

    public void setCo2(Integer co2) {
        this.co2 = co2;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Tipo_Caixa getTipoCaixa() {
        return tipoCaixa;
    }

    public void setTipoCaixa(Tipo_Caixa tipoCaixa) {
        this.tipoCaixa = tipoCaixa;
    }

    public Tipo_Combustao getTipoCombustao() {
        return tipoCombustao;
    }

    public void setTipoCombustao(Tipo_Combustao tipoCombustao) {
        this.tipoCombustao = tipoCombustao;
    }

    public Tipo_Motor getTipoMotor() {
        return tipoMotor;
    }

    public void setTipoMotor(Tipo_Motor tipoMotor) {
        this.tipoMotor = tipoMotor;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    //#endregion
}
