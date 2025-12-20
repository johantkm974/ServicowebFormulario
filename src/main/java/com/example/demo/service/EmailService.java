package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    // Esto lee tu correo desde application.properties para no escribirlo dos veces
    @Value("${brevo.from.email}")
    private String miCorreo;

    public void enviarCorreoContacto(String nombreCliente, String emailCliente, String mensajeCliente) {
        SimpleMailMessage mail = new SimpleMailMessage();

        // DE: Tu correo (Gmail requiere que seas tú quien envía)
        mail.setFrom(miCorreo);
        
        // PARA: Tu correo (Para que te llegue la notificación a ti)
        mail.setTo(miCorreo);
        
        // ASUNTO
        mail.setSubject("Nuevo Cliente Interesado: " + nombreCliente);
        
        // CUERPO DEL CORREO
        String cuerpo = "Tienes un nuevo mensaje desde la web.\n\n" +
                        "Datos del Cliente:\n" +
                        "------------------\n" +
                        "Nombre: " + nombreCliente + "\n" +
                        "Correo: " + emailCliente + "\n\n" +
                        "Mensaje:\n" + mensajeCliente;

        mail.setText(cuerpo);

        mailSender.send(mail);
    }
    // MÉTODO 2: Correo para EL CLIENTE (Auto-respuesta)
    public void enviarRespuestaAutomatica(String nombreCliente, String emailCliente) {
        SimpleMailMessage mail = new SimpleMailMessage();
        
        mail.setFrom(miCorreo);
        mail.setTo(emailCliente); // <--- AQUI enviamos al correo que escribió el usuario
        mail.setSubject("Hemos recibido tu mensaje - Inventario App");
        
        // El mensaje exacto que pediste
        String cuerpo = "Hola " + nombreCliente + ",\n\n" +
                        "Tu solicitud ha sido enviada. Nos comunicaremos contigo pronto.\n" +
                        "Gracias por confiar en nosotros.\n\n" +
                        "Atentamente,\n" +
                        "El Equipo.";

        mail.setText(cuerpo);
        mailSender.send(mail);
    }

}
