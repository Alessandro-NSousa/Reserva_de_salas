package com.alura.reservas.reserva.domain.enumeration;

public enum StatusReserva {

    ATIVA("Ativo"),
    CANCELADA("Cancelado");

    private String status;

    StatusReserva(String status){
        this.status = status;
    }

    public String getStatus(){
        return status;
    }

    public static StatusReserva fromString(String text) {
        for (StatusReserva status : StatusReserva.values()) {
            if (status.status.equalsIgnoreCase(text)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Nenhum status encontrado: " + text);
    }

}
