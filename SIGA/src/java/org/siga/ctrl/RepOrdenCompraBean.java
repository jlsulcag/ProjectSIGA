
package org.siga.ctrl;

import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.view.ViewScoped;
import org.siga.be.NotaEntrada;
import org.siga.be.NotaEntradaDetalle;
import org.siga.bl.NotaIngresoBl;

/**
 *
 * @author JSULCAG
 */
@ManagedBean
@ViewScoped
public class RepOrdenCompraBean {

    @ManagedProperty(value = "#{notaEntrada}")
    private NotaEntrada notaEntrada;
    @ManagedProperty(value = "#{notaIngresoBl}")
    private NotaIngresoBl notaEntradaBl;
    
    @ManagedProperty(value = "#{notaEntradaDetalle}")
    private NotaEntradaDetalle notaEntradaDetalle;
    @ManagedProperty(value = "#{notaEntradaDetalleBl}")
    private NotaIngresoBl notaEntradaDetalleBl;
    
    private List<NotaEntrada> listNotaEntradas;
    private List<NotaEntradaDetalle> listNotaEntradasDetalles;
    
    public RepOrdenCompraBean() {
    }
    
    @PostConstruct
    public void listarCompras(){
        listNotaEntradas = new LinkedList<>();
        listNotaEntradas = notaEntradaBl.listarCompras();
    }

    public NotaEntrada getNotaEntrada() {
        return notaEntrada;
    }

    public void setNotaEntrada(NotaEntrada notaEntrada) {
        this.notaEntrada = notaEntrada;
    }

    public NotaIngresoBl getNotaEntradaBl() {
        return notaEntradaBl;
    }

    public void setNotaEntradaBl(NotaIngresoBl notaEntradaBl) {
        this.notaEntradaBl = notaEntradaBl;
    }

    public NotaEntradaDetalle getNotaEntradaDetalle() {
        return notaEntradaDetalle;
    }

    public void setNotaEntradaDetalle(NotaEntradaDetalle notaEntradaDetalle) {
        this.notaEntradaDetalle = notaEntradaDetalle;
    }

    public NotaIngresoBl getNotaEntradaDetalleBl() {
        return notaEntradaDetalleBl;
    }

    public void setNotaEntradaDetalleBl(NotaIngresoBl notaEntradaDetalleBl) {
        this.notaEntradaDetalleBl = notaEntradaDetalleBl;
    }

    public List<NotaEntrada> getListNotaEntradas() {
        return listNotaEntradas;
    }

    public void setListNotaEntradas(List<NotaEntrada> listNotaEntradas) {
        this.listNotaEntradas = listNotaEntradas;
    }

    public List<NotaEntradaDetalle> getListNotaEntradasDetalles() {
        return listNotaEntradasDetalles;
    }

    public void setListNotaEntradasDetalles(List<NotaEntradaDetalle> listNotaEntradasDetalles) {
        this.listNotaEntradasDetalles = listNotaEntradasDetalles;
    }
    
}
