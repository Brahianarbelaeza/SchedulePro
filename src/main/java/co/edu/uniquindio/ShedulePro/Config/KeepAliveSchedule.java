package co.edu.uniquindio.ShedulePro.Config;

import co.edu.uniquindio.ShedulePro.services.implementaciones.IKeepAliveService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@AllArgsConstructor
public class KeepAliveSchedule {

    private final IKeepAliveService keepAliveService;

    /**
     * Método que se encarga de enviar una petición de keep alive al backend cada 3 segundos.
     */
    @Scheduled(fixedRate = 3000)
    public void keepBackendAlive() {
        keepAliveService.sendKeepAliveRequest();
    }
}
