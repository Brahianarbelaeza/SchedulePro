package co.edu.uniquindio.ShedulePro.services.implementaciones;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class IKeepAliveService {

    private final RestTemplate restTemplate;
    private final String selfUrl;

    /**
     * Este metodo realiza una petición GET al propio backend
     * para mantenerlo activo en Render.
     * @param restTemplate Conifguracion de RestTemplate para realizar peticiones HTTP
     * @param selfUrl URL del backend
     */
    public IKeepAliveService(RestTemplate restTemplate, @Value("${app.self-url}") String selfUrl) {
        this.restTemplate = restTemplate;
        this.selfUrl = selfUrl;
    }

    /**
     * Este metodo realiza una petición GET al propio backend
     * para mantenerlo activo en Render.
     */
    public void sendKeepAliveRequest() {
        try {
            // Realizamos la petición GET al propio backend
            ResponseEntity<String> response = restTemplate.getForEntity(selfUrl, String.class);
            //log.info("Respuesta del servidor: " + response.getBody());
        } catch (Exception e) {
            //log.error("Error al enviar la petición: " + e.getMessage());
        }
    }
}
