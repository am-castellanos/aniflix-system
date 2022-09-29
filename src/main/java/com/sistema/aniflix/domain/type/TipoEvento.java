package com.sistema.aniflix.domain.type;

public enum TipoEvento {

    ENTRADA("Entrada"),
    SALIDA("Salida"),

    DESCONOCIDO("Desconocido");

    private String valor;

    TipoEvento(final String valor) {

        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }

    public static TipoEvento of(final String valor) {

        return switch (valor.toLowerCase()) {

            case "entrada" -> ENTRADA;
            case "salida" -> SALIDA;
            default -> DESCONOCIDO;
        };
    }
}
