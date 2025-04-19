package co.edu.uniquindio.ShedulePro.controladores;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;

@RestController
public class Test {

    @GetMapping("/test")
    public String isKeepAlive(){
        return "The server is alive " + LocalTime.now();
    }
}
