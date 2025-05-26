package co.edu.uniquindio.ShedulePro.services.implementaciones;
import co.edu.uniquindio.ShedulePro.dto.email.EmailDTO;
import co.edu.uniquindio.ShedulePro.model.documents.ReporteNomina;
import co.edu.uniquindio.ShedulePro.model.documents.TurnoTrabajo;
import co.edu.uniquindio.ShedulePro.model.documents.Usuario;
import co.edu.uniquindio.ShedulePro.model.enums.Estado;
import co.edu.uniquindio.ShedulePro.model.enums.EstadoTurno;
import co.edu.uniquindio.ShedulePro.repositories.ReporteNominaRepo;
import co.edu.uniquindio.ShedulePro.repositories.TurnoRepo;
import co.edu.uniquindio.ShedulePro.repositories.UsuarioRepo;
import co.edu.uniquindio.ShedulePro.services.interfaces.ReporteNominaServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReporteNominaImpl implements ReporteNominaServicio {
    private final UsuarioRepo usuarioRepo;
    private final TurnoRepo turnoRepo;
    private final ReporteNominaRepo reporteNominaRepo;
    private final EmailServicioImpl emailServicio;
    private final PDFServicioImpl pdfServicio;


    @Override
    @Scheduled(cron = "0 0 0 * * MON")
    public String generarReporteNominaGeneral() {
        List<Usuario> usuarios = usuarioRepo.findAllByEstado(Estado.ACTIVO); // Empleados activos
        List<TurnoTrabajo> turnos = turnoRepo.findAllByEstado(EstadoTurno.INACTIVO); // Turnos que ya pasaron

        // Aquí guardaremos los reportes generados por empleado
        List<ReporteNomina> reportes = new ArrayList<>();

        for (Usuario empleado : usuarios) {
            // Filtrar turnos que pertenecen a este empleado
            List<TurnoTrabajo> turnosEmpleado = turnos.stream()
                    .filter(t -> t.getEmpleadoId().equals(empleado.getId()))
                    .collect(Collectors.toList());

            if (turnosEmpleado.isEmpty()) continue; // saltar si no tiene turnos

            // Calcular las horas trabajadas y el total
            int horasTotales = calcularHoras(turnosEmpleado); // Función que sumarías
            int diasTrabajados = turnosEmpleado.size();
            int salarioTotal = horasTotales * empleado.getPrecioHora();
            ReporteNomina reporte = ReporteNomina.builder()
                    .idEmpleado(empleado.getId())
                    .nombreEmpleado(empleado.getNombre())
                    .cedulaEmpleado(empleado.getCedula())
                    .diasTrabajados(diasTrabajados)
                    .horasTrabajadas(horasTotales)
                    .valorHora(empleado.getPrecioHora())
                    .salarioTotal(salarioTotal)
                    .fechaGeneracion(LocalDate.now())
                    .build();

            reportes.add(reporte);
        }
        reporteNominaRepo.saveAll(reportes);
        byte[] pdfBytes = pdfServicio.generarReporteGeneral(reportes);
        try {
            emailServicio.enviarCorreoConAdjunto(
                    new EmailDTO("Reporte Nomina", "Adjunto encontrará el reporte de nómina semanal.", "barbelaezaguirre@gmail.com"),
                    pdfBytes,
                    "ReporteNominaGeneral.pdf"
            );
            return "Reporte de nómina general generado y enviado por correo.";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private int calcularHoras(List<TurnoTrabajo> turnosEmpleado) {
        return turnosEmpleado.stream()
                .mapToInt(turno -> turno.getHoraSalida().getHour() - turno.getHoraEntrada().getHour())
                .sum();
    }


    @Override
    public void generarReporteNominaEmpleado() {

    }
}
