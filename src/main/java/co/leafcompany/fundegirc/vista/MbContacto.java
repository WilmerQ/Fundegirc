/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.leafcompany.fundegirc.vista;

import co.keafcompany.fundegirc.logica.LogicaVoluntarios;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author wilme
 */
@ViewScoped
@ManagedBean(name = "MbContacto")
public class MbContacto implements Serializable {

    public MbContacto() {
    }

    private String nombre;
    private String email;
    private String mensaje;
    @EJB
    private LogicaVoluntarios lv;

    @PostConstruct
    public void init() {
        nombre = "";
        email = "";
        mensaje = "";
    }

    public void accionGuardar() {
        if (VerificarFormulario()) {
            try {
                if (lv.pre_envio_contacto(nombre, email, mensaje)) {
                    mostrarMensaje(FacesMessage.SEVERITY_INFO, "Exitoso", "Se ha registrado");
                    init();
                }
            } catch (Exception e) {
                mostrarMensaje(FacesMessage.SEVERITY_ERROR, "ERROR", "No se ha registrado");
            }
        }
    }

    public Boolean VerificarFormulario() {
        Boolean resultado = Boolean.TRUE;
        if (nombre.trim().length() == 0) {
            resultado = Boolean.FALSE;
            mostrarMensaje(FacesMessage.SEVERITY_ERROR, "ERROR", "Ingrese su nombre.");
        }

        if (email.trim().length() == 0) {
            resultado = Boolean.FALSE;
            mostrarMensaje(FacesMessage.SEVERITY_ERROR, "ERROR", "Ingrese su correo electrónico.");
        }

        if (mensaje.trim().length() == 0) {
            resultado = Boolean.FALSE;
            mostrarMensaje(FacesMessage.SEVERITY_ERROR, "ERROR", "Ingrese un mensaje.");
        }

        return resultado;
    }

    public void mostrarMensaje(FacesMessage.Severity icono, String titulo, String mensaje) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(icono, titulo, mensaje));
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

}
