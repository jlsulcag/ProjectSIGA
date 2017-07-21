package org.siga.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class MensajeView {

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
}
