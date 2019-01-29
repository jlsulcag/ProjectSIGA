package org.siga.util;

import java.math.BigDecimal;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.hibernate.HibernateException;

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

    public static void seEncuentraAutorizada() {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Atención", "La Orden de Compra ya esta Aprobada");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    public static void pedidoEstado(String estado) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Atención", "El pedido debe estar en estado "+estado);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public static void noImprimeOrdenCompra() {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atención", "La Orden de Compra no se encuentra Aprobada");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    public static void verificarPedido() {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Atención", "El pedido fue verificado");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public static void pedidoAprobado() {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Atención", "El pedido fue aprobado");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public static void recepcionarPedido() {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Atención", "El pedido fue recepcionado con éxito");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public static void noExisteNotaEntrada(long numero) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error", "No existe nota de entrada para el pedido N° "+numero);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public static void seleccioneTabla() {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error", "Seleccione un elemento de la tabla");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public static void docEstaCerrado() {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error", "El Documento se encuentra en estado CERRADO");
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
    
    public static void errorFatal(HibernateException he) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error Fatal : "+he);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    public static void errorFatal(Exception e) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error Fatal : "+e);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
}
