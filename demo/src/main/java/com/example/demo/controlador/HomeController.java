package com.example.demo.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class HomeController {
    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    @GetMapping("/")
    public String home() {
        log.info("Cargando página de inicio");
        return "index";
    }

    @GetMapping("/servicios")
    public String servicios() {
        log.info("Cargando página de servicios");
        return "servicios";
    }

    @GetMapping("/contacto")
    public String contacto() {
        log.info("Cargando página de contacto");
        return "contacto";
    }




}
