package model;

import java.time.LocalDateTime;

import enums.Status_Aluguer;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "aluguer")
public class Aluguer {

  // #region Variables
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "data_reserva", nullable = false)
  private LocalDateTime dataReserva;            
  @Column(name = "data_levantamento")
  private LocalDateTime dataLevantamento;
  @Column(name = "data_devolucao")
  private LocalDateTime dataDevolucao;
  @Column(name = "valor_final", precision = 10, scale = 2)
  private double valorFinalPrevisto;


  @ManyToOne
  @JoinColumn(name = "id_veiculo", nullable = false)
  private Veiculo veiculo;          // INFO: FK
  @ManyToOne
  @JoinColumn(name = "id_cliente", nullable = false)
  private Utilizador cliente;       // INFO: FK
  @ManyToOne
  @JoinColumn(name = "id_funcionario")
  private Utilizador funcionario;   // INFO: FK

  // ENUMs
  @Enumerated(EnumType.STRING)
  private Status_Aluguer statusAluguer;
  // #endregion

  // Construtor Vazio
  public Aluguer() {}

  public Aluguer(Long id, LocalDateTime dataReserva, LocalDateTime dataLevantamento, LocalDateTime dataDevolucao, double valorFinalPrevisto, Veiculo veiculo,Utilizador cliente, Utilizador funcionario, Status_Aluguer statusAluguer) {
    this.id = id;
    this.dataReserva = dataReserva;
    this.dataLevantamento = dataLevantamento;
    this.dataDevolucao = dataDevolucao;
    this.valorFinalPrevisto = valorFinalPrevisto;
    this.veiculo = veiculo;
    this.cliente = cliente;
    this.funcionario = funcionario;
    this.statusAluguer = statusAluguer;
  }

//#region Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataReserva() {
        return dataReserva;
    }

    public void setDataReserva(LocalDateTime dataReserva) {
        this.dataReserva = dataReserva;
    }

    public LocalDateTime getDataLevantamento() {
        return dataLevantamento;
    }

    public void setDataLevantamento(LocalDateTime dataLevantamento) {
        this.dataLevantamento = dataLevantamento;
    }

    public LocalDateTime getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(LocalDateTime dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public double getValorFinalPrevisto() {
        return valorFinalPrevisto;
    }

    public void setValorFinalPrevisto(double valorFinalPrevisto) {
        this.valorFinalPrevisto = valorFinalPrevisto;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public Utilizador getCliente() {
        return cliente;
    }

    public void setCliente(Utilizador cliente) {
        this.cliente = cliente;
    }

    public Utilizador getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Utilizador funcionario) {
        this.funcionario = funcionario;
    }

    public Status_Aluguer getStatusAluguer() {
        return statusAluguer;
    }

    public void setStatusAluguer(Status_Aluguer statusAluguer) {
        this.statusAluguer = statusAluguer;
    }
    //#endregion




}
