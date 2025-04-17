package co.edu.uniquindio.ShedulePro.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@AllArgsConstructor
@Setter
@Getter
@ToString
public class Dia {
    private LocalDateTime fecha;
    private LocalDateTime horaEntrada;
    private LocalDateTime horaSalida;
    private String sede;
    private double valorHora;
}
