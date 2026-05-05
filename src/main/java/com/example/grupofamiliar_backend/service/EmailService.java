package com.example.grupofamiliar_backend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    public void enviarRecuperacionContrasena(String toEmail, String nuevaContrasena) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("pablojc3005@gmail.com");
            mailMessage.setTo(toEmail);
            mailMessage.setSubject("Bienvenido - Tu nueva contraseña");
            mailMessage.setText("Hola,\n\n" +
                    "Tu cuenta ha sido creada exitosamente. " +
                    "Puedes acceder a la plataforma con la siguiente contraseña generada automáticamente:\n\n" +
                    nuevaContrasena + "\n\n" +
                    "Te recomendamos cambiar esta contraseña desde tu perfil lo antes posible.\n\n" +
                    "Saludos,\n" +
                    "El equipo de administración.");
            
            javaMailSender.send(mailMessage);
            log.info("Correo con contraseña enviado exitosamente a {}", toEmail);
        } catch (Exception ex) {
            log.error("Error al enviar correo a {}: {}", toEmail, ex.getMessage());
            // No lanzamos excepción para no interrumpir el flujo, pero el usuario no recibirá el email. 
            // Podríamos lanzar una excepción personalizada si el envío es crítico.
        }
    }

    public void enviarReinicioContrasena(String toEmail, String nuevaContrasena) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("pablojc3005@gmail.com");
            mailMessage.setTo(toEmail);
            mailMessage.setSubject("Recuperación de Contraseña");
            mailMessage.setText("Hola,\n\n" +
                    "Hemos recibido una solicitud para restablecer tu contraseña. " +
                    "Tu nueva contraseña es:\n\n" +
                    nuevaContrasena + "\n\n" +
                    "Te recomendamos cambiar esta contraseña desde tu perfil lo antes posible.\n\n" +
                    "Saludos,\n" +
                    "El equipo de administración.");
            
            javaMailSender.send(mailMessage);
            log.info("Correo de recuperación enviado exitosamente a {}", toEmail);
        } catch (Exception ex) {
            log.error("Error al enviar correo de recuperación a {}: {}", toEmail, ex.getMessage());
            throw new RuntimeException("No se pudo enviar el correo de recuperación");
        }
    }

    public void enviarNotificacionAprobacion(String toEmail, String semanaDesde, String semanaHasta) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("pablojc3005@gmail.com");
            mailMessage.setTo(toEmail);
            mailMessage.setSubject("Reporte Aprobado");
            mailMessage.setText("Hola,\n\n" +
                    "Tu reporte correspondiente a la semana del " + semanaDesde + " al " + semanaHasta + " ha sido APROBADO por tu supervisor sectorial.\n\n" +
                    "Gracias por tu puntualidad y esfuerzo.\n\n" +
                    "Saludos,\n" +
                    "El equipo de administración.");
            
            javaMailSender.send(mailMessage);
            log.info("Correo de notificación de aprobación enviado a {}", toEmail);
        } catch (Exception ex) {
            log.error("Error al enviar correo de notificación a {}: {}", toEmail, ex.getMessage());
        }
    }
}
