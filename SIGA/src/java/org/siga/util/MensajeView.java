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
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Atención", "Error de registro");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
