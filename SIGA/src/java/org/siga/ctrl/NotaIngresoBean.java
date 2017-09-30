
package org.siga.ctrl;

import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.siga.be.NotaEntrada;
import org.siga.be.NotaEntradaDetalle;
import org.siga.be.OrdenCompra;
import org.siga.be.OrdenCompraDetalle;
import org.siga.be.Proveedor;
import org.siga.be.TipoMovimiento;
import org.siga.bl.NotaIngresoBl;
import org.siga.bl.NotaIngresoDetalleBl;
import org.siga.bl.OrdenCompraDetalleBl;

@ManagedBean
@ViewScoped
public class NotaIngresoBean {
    @ManagedProperty(value = "#{notaEntrada}")
    private NotaEntrada notaEntrada;
    @ManagedProperty(value = "#{notaIngresoBl}")
    private NotaIngresoBl notaIngresoBl;
    @ManagedProperty(value = "#{notaEntradaDetalle}")
    private NotaEntradaDetalle notaEntradaDetalle;
    @ManagedProperty(value = "#{notaIngresoDetalleBl}")
    private NotaIngresoDetalleBl notaIngresoDetalleBl;
    @ManagedProperty(value = "#{ordenCompraDetalleBl}")
    private OrdenCompraDetalleBl ordenCompraDetalleBl;
    //private List<NotaEntrada> listNotaEntrada;
    private List<NotaEntradaDetalle> listNotaEntradaDetalle;
    private List<OrdenCompraDetalle> listOrdenCompraDetalle;
    
    public NotaIngresoBean() {
    }
    
    @PostConstruct
    public void limpiar(){
        getNotaEntrada().setIdnotaentrada(0);
        getNotaEntrada().setNumero(maxNumero()+1);
        getNotaEntrada().setFechaReg(new Date());
        getNotaEntrada().setOrdenCompra(new OrdenCompra());
        getNotaEntrada().setFechaDocref(null);
        getNotaEntrada().setNroDocref("");
        getNotaEntrada().setProveedor(new Proveedor());
        getNotaEntrada().setTipoMovimiento(new TipoMovimiento());
        getNotaEntrada().setObservacion("");
    }

    private long maxNumero() {
        return getNotaIngresoBl().buscarUltimoNumero();
    }
    
    public void listarDetalleOrdenCompra(){
        System.out.println("id orden compra "+notaEntrada.getOrdenCompra().getIdordencompra());
        //Obtener la lista de DetalleOrdenCompra
        long id = notaEntrada.getOrdenCompra().getIdordencompra();
        listOrdenCompraDetalle = ordenCompraDetalleBl.listarXIdOrdenCompra(id);
        setListNotaEntradaDetalle(new ArrayList<>());
        for (OrdenCompraDetalle obj : listOrdenCompraDetalle) {
            notaEntradaDetalle.setNotaEntrada(notaEntrada);
            notaEntradaDetalle.setProducto(obj.getProducto());
            notaEntradaDetalle.setCantidad(obj.getCantidad());
            notaEntradaDetalle.setLote(obj.getLote());
            notaEntradaDetalle.setFechaVencimiento(obj.getFechaVencimiento());
            notaEntradaDetalle.setValorCompra(obj.getValorCompra());
            notaEntradaDetalle.setPrecioCompra(obj.getPrecioCompra());
            notaEntradaDetalle.setDesc1(obj.getDesc1());
            notaEntradaDetalle.setDesc2(obj.getDesc2());
            notaEntradaDetalle.setUnidadMedida(obj.getUnidadMedida());
            notaEntradaDetalle.setMontoDescitem(obj.getMontoDescitem());
            
            getListNotaEntradaDetalle().add(notaEntradaDetalle);
        }
    }

    public NotaEntrada getNotaEntrada() {
        return notaEntrada;
    }

    public void setNotaEntrada(NotaEntrada notaEntrada) {
        this.notaEntrada = notaEntrada;
    }

    public NotaIngresoBl getNotaIngresoBl() {
        return notaIngresoBl;
    }

    public void setNotaIngresoBl(NotaIngresoBl notaIngresoBl) {
        this.notaIngresoBl = notaIngresoBl;
    }

    public NotaEntradaDetalle getNotaEntradaDetalle() {
        return notaEntradaDetalle;
    }

    public void setNotaEntradaDetalle(NotaEntradaDetalle notaEntradaDetalle) {
        this.notaEntradaDetalle = notaEntradaDetalle;
    }

    public NotaIngresoDetalleBl getNotaIngresoDetalleBl() {
        return notaIngresoDetalleBl;
    }

    public void setNotaIngresoDetalleBl(NotaIngresoDetalleBl notaIngresoDetalleBl) {
        this.notaIngresoDetalleBl = notaIngresoDetalleBl;
    }

    public List<OrdenCompraDetalle> getListOrdenCompraDetalle() {
        return listOrdenCompraDetalle;
    }

    public void setListOrdenCompraDetalle(List<OrdenCompraDetalle> listOrdenCompraDetalle) {
        this.listOrdenCompraDetalle = listOrdenCompraDetalle;
    }

    public OrdenCompraDetalleBl getOrdenCompraDetalleBl() {
        return ordenCompraDetalleBl;
    }

    public void setOrdenCompraDetalleBl(OrdenCompraDetalleBl ordenCompraDetalleBl) {
        this.ordenCompraDetalleBl = ordenCompraDetalleBl;
    }

    public List<NotaEntradaDetalle> getListNotaEntradaDetalle() {
        return listNotaEntradaDetalle;
    }

    public void setListNotaEntradaDetalle(List<NotaEntradaDetalle> listNotaEntradaDetalle) {
        this.listNotaEntradaDetalle = listNotaEntradaDetalle;
    }
}
