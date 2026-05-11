package model;

import java.time.LocalDateTime;

import enums.Status_Aluguer;

public class Aluguer {

  // #region Variables
  private int idAluguer;
  private LocalDateTime dataReserva;
  private LocalDateTime dataLevantamento;
  private LocalDateTime dataDevolucao;
  private double valorFinalPrevisto;

  private Veiculo veiculo;          // INFO: FK
  private Utilizador cliente;       // INFO: FK
  private Utilizador funcionario;   // INFO: FK

  // ENUMs
  private Status_Aluguer statusAluguer;
  // #endregion

  // Construtor Vazio
  public Aluguer() {}

  public Aluguer(int idAluguer, LocalDateTime dataReserva, LocalDateTime dataLevantamento, LocalDateTime dataDevolucao, double valorFinalPrevisto, Veiculo veiculo,              Utilizador cliente, Utilizador funcionario, Status_Aluguer statusAluguer) {
    this.idAluguer = idAluguer;
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
    public int getIdAluguer() {
        return idAluguer;
    }

    public void setIdAluguer(int idAluguer) {
        this.idAluguer = idAluguer;
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