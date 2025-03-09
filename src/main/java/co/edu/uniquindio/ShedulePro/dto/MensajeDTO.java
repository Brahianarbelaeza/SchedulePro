package co.edu.uniquindio.ShedulePro.dto;

public record MensajeDTO<T>(
        boolean error,
        T respuesta
) {
}
