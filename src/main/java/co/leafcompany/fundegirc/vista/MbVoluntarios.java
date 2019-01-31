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
@ManagedBean(name = "MbVoluntarios")
public class MbVoluntarios implements Serializable {

    private String nombre;
    private String email;
    private String telefono;
    private String mensaje;
    @EJB
    private LogicaVoluntarios lv;

    public MbVoluntarios() {
    }

    @PostConstruct
    public void init() {
        nombre = "";
        email = "";
        telefono = "";
        mensaje = "";
    }

    public void accionGuardar() {
        if (VerificarFormulario()) {
            try {
                if (lv.pre_envio(nombre, email, telefono, mensaje)) {
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

        if (telefono.trim().length() == 0) {
            resultado = Boolean.FALSE;
            mostrarMensaje(FacesMessage.SEVERITY_ERROR, "ERROR", "Ingrese su número de teléfono.");
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

}
