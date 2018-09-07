package org.siga.util;

import java.math.BigDecimal;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class MensajeView {
    public static final BigDecimal IGV = new BigDecimal("0.18");
    public static final BigDecimal IGV_DIV = new BigDecimal("1.18");
    public static final int ENTRADA = 1, SALIDA = 2;

    public static void registroError(Exception e) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error de registro \n "+e);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public static void autorizarOrdenCompra() {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Atención", "Orden Compra Aprobada");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    String mensaje = "";

    public static void registroCorrecto() {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Atención", "Registro correcto");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    public static void registroError() {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error de registro");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    public static void actCorrecto() {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Atención", "Actualización correcta");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    public static void actError() {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error de Actualización");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    public static void eliminacionCorrecta() {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Atención", "Se eliminó correctamente el registro");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    public static void eliminacionErronea() {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error al eliminar el registro");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    public static void listVacia() {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No existen items");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
}
