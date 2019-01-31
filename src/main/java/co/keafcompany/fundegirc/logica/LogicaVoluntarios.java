/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.keafcompany.fundegirc.logica;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author wilme
 */
@Stateless
public class LogicaVoluntarios implements Serializable {

    public Boolean pre_envio(String nombre, String email, String telefono, String mensaje) {
        try {
            final String mensajehtml = "<p>Cordial saludo</p>\n"
                    + "<p>&nbsp;Se recibe este correo porque a trav&eacute;s de la p&aacute;gina fundegirc.org en la secci&oacute;n de voluntarios se ha digitado la siguiente informaci&oacute;n:</p>\n"
                    + "<p>Fecha: " + new Date().toString() + "</p>\n"
                    + "<p>Nombre: " + nombre + "</p>\n"
                    + "<p>Celular: " + telefono + "</p>\n"
                    + "<p>Email: " + email + "</p>\n"
                    + "<p>Mensaje: " + mensaje + "</p>\n"
                    + "<p>&nbsp;</p>\n"
                    + "<p>Muchas gracias</p>";
            final List<String> destinarios = new ArrayList<>();
            destinarios.add("gerencia@fundegirc.org");
            destinarios.add("cuases@hotmail.com");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    enviarCorreoelectronico(destinarios, new ArrayList<String>(), new ArrayList<String>(), "Voluntarios - Actividad en la página Fundegirc", mensajehtml);
                }
            }).start();

            return Boolean.TRUE;
        } catch (Exception e) {
            System.out.println("*************************************************");
            System.out.println("Error: " + e.getMessage());
            return Boolean.FALSE;
        }
    }

    public Boolean pre_envio_contacto(String nombre, String email, String mensaje) {
        try {
            final String mensajehtml = "<p>Cordial saludo</p>\n"
                    + "<p>&nbsp;Se recibe este correo porque a trav&eacute;s de la p&aacute;gina fundegirc.org en la secci&oacute;n de Contacto se ha digitado la siguiente informaci&oacute;n:</p>\n"
                    + "<p>Fecha: " + new Date().toString() + "</p>\n"
                    + "<p>Nombre: " + nombre + "</p>\n"
                    + "<p>Email: " + email + "</p>\n"
                    + "<p>Mensaje: " + mensaje + "</p>\n"
                    + "<p>&nbsp;</p>\n"
                    + "<p>Muchas gracias</p>";
            final List<String> destinarios = new ArrayList<>();
            destinarios.add("gerencia@fundegirc.org");
            destinarios.add("cuases@hotmail.com");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    enviarCorreoelectronico(destinarios, new ArrayList<String>(), new ArrayList<String>(), "Contacto - Actividad en la página Fundegirc", mensajehtml);
                }
            }).start();

            return Boolean.TRUE;
        } catch (Exception e) {
            System.out.println("*************************************************");
            System.out.println("Error: " + e.getMessage());
            return Boolean.FALSE;
        }
    }

    private void enviarCorreoelectronico(List<String> destinatarioTo, List<String> destinatarioCC, List<String> destinatarioBCC, String asunto, String cuerpoMensaje) {
        try {
            Properties props = new Properties();
            String host = "smtp.gmail.com";
            String username = "gerencia@fundegirc.org";
            String password = "Vale5981";
            props.put("mail.smtps.auth", "true");
            Session session = Session.getDefaultInstance(props);
            session.setDebug(false);

            MimeMessage message = new MimeMessage(session);
            message.setHeader("Content-Type", "text/html");
            // Quien envia el correo
            message.setFrom(new InternetAddress("gerencia@fundegirc.org", "Fundegirc.org"));

            for (int i = 0; i < destinatarioTo.size(); i++) {
                if (destinatarioTo.get(i) != null) {
                    message.addRecipient(Message.RecipientType.TO,
                            new InternetAddress(destinatarioTo.get(i)));
                }
            }

            for (int i = 0; i < destinatarioCC.size(); i++) {
                if (destinatarioCC.get(i) != null) {
                    message.addRecipient(Message.RecipientType.CC,
                            new InternetAddress(destinatarioCC.get(i)));
                }
            }
            for (int i = 0; i < destinatarioBCC.size(); i++) {
                if (destinatarioBCC.get(i) != null) {
                    message.addRecipient(Message.RecipientType.BCC,
                            new InternetAddress(destinatarioBCC.get(i)));
                }
            }
            message.setSubject(asunto);
            message.setContent(cuerpoMensaje, "text/html");
            Transport t = session.getTransport("smtps");
            t.connect(host, username, password);
            t.sendMessage(message, message.getAllRecipients());
            t.close();
        } catch (Exception e) {
            System.out.println("**************************************");
            System.out.println("Error: " + e.getMessage());
        } finally {
        }

    }
}
