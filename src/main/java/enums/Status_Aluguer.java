package enums;

public enum Status_Aluguer {
    PENDENTE,       // Reserva criada, aguarda confirmação
    LEVANTAR,       // Confirmada, pronta para levantamento
    ATIVA,          // Carro levantado, em uso
    DEVOLVER,       // Cliente pediu devolução
    MANUTENCAO,     // Carro em manutenção após devolução
    CONCLUIDA,      // Aluguer terminado, carro disponível
    CANCELADO       // Aluguer cancelado
}
