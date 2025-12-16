package com.example.demo.controller;

import com.example.demo.dto.ContactoRequest;
import com.example.demo.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contacto")
@CrossOrigin(origins = "*")
public class ContactoController {

    @Autowired
    private EmailService emailService;

    @PostMapping
    public String recibirContacto(@RequestBody ContactoRequest request) {
        try {
            // 1. Te enviamos el correo a TI (Administrador)
            emailService.enviarCorreoContacto(
                request.getNombre(),
                request.getEmail(),
                request.getMensaje()
            );

            // 2. Le enviamos el correo de confirmación AL CLIENTE
            emailService.enviarRespuestaAutomatica(
                request.getNombre(), 
                request.getEmail()
            );
            
            // Mensaje final para la pantalla
            return "¡Mensaje enviado! Revisa tu correo, te hemos enviado una confirmación.";
            
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al enviar el correo: " + e.getMessage();
        }
    }
}