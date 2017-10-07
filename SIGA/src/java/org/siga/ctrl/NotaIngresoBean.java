package org.siga.ctrl;

import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.RowEditEvent;
import org.siga.be.NotaEntrada;
import org.siga.be.NotaEntradaDetalle;
import org.siga.be.OrdenCompra;
import org.siga.be.OrdenCompraDetalle;
import org.siga.be.Producto;
import org.siga.be.Proveedor;
import org.siga.be.TipoMovimiento;
import org.siga.bl.NotaIngresoBl;
import org.siga.bl.NotaIngresoDetalleBl;
import org.siga.bl.OrdenCompraBl;
import org.siga.bl.OrdenCompraDetalleBl;
import org.siga.util.MensajeView;

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
    @ManagedProperty(value = "#{ordenCompra}")
    private OrdenCompra ordenCompra;
    @ManagedProperty(value = "#{ordenCompraBl}")
    private OrdenCompraBl ordenCompraBl;

    private NotaEntradaDetalle notaEntradaDetalleTemp;

    //private List<NotaEntrada> listNotaEntrada;
    private List<NotaEntradaDetalle> listNotaEntradaDetalle;
    private List<OrdenCompraDetalle> listOrdenCompraDetalle;

    private long res;

    public NotaIngresoBean() {
    }

    public void registrar() {
        notaEntrada.setOrdenCompra(notaEntrada.getOrdenCompra());
        //notaEntrada.setIdAlmacendestino(0);
        notaEntrada.setIdUserReg(0);
        notaEntrada.setObservacion("");
        //notaEntrada.setTipoIngreso("");

        res = notaIngresoBl.registrar(notaEntrada);
        //Registrar Nota Entrada Detalle
        if (res == 0) {
            for (NotaEntradaDetalle obj : listNotaEntradaDetalle) {
                obj.setNotaEntrada(notaEntrada);
                notaIngresoDetalleBl.registrar(obj);
            }
        }
        //Actualizar el estado de  la orden de compra
        long res = -1;
        res = actualizarEstadoOrdenCompra();
        if (res == 0) {
            MensajeView.actCorrecto();
        } else {
            MensajeView.actError();
        }
    }

    @PostConstruct
    public void iniciar() {
        notaEntrada.setIdnotaentrada(0);
        notaEntrada.setNumero(maxNumero() + 1);
        notaEntrada.setFechaReg(new Date());
        notaEntrada.setOrdenCompra(new OrdenCompra());
        notaEntrada.setFechaDocref(null);
        notaEntrada.setNroDocref("");
        notaEntrada.setObservacion("");
        notaEntrada.setTipoIngreso("");
        ordenCompra.setEstado("");
        if(listNotaEntradaDetalle == null){
        
        }else{
            listNotaEntradaDetalle.clear();
        }
        
        
    }

    private long maxNumero() {
        return getNotaIngresoBl().buscarUltimoNumero();
    }

    public void listarDetalleOrdenCompra() {
        //Obtener la lista de DetalleOrdenCompra
        long id = notaEntrada.getOrdenCompra().getIdordencompra();
        listOrdenCompraDetalle = ordenCompraDetalleBl.listarXIdOrdenCompra(id);
        listNotaEntradaDetalle = new ArrayList<>();
        for (OrdenCompraDetalle obj : listOrdenCompraDetalle) {
            //notaEntradaDetalle.setNotaEntrada(notaEntrada);
            NotaEntradaDetalle notaED = new NotaEntradaDetalle();
            notaED.setProducto(obj.getProducto());
            notaED.setLote(obj.getLote());
            notaED.setFechaVencimiento(obj.getFechaVencimiento());
            notaED.setValorCompra(obj.getValorCompra());
            notaED.setPrecioCompra(obj.getPrecioCompra());
            notaED.setDesc1(obj.getDesc1());
            notaED.setDesc2(obj.getDesc2());
            notaED.setUnidadMedida(obj.getUnidadMedida());
            notaED.setMontoDescitem(obj.getMontoDescitem());

            notaED.setCantSolicitada(obj.getCantidad());
            //buscar la suma de la cantidad ingresada hasta ese momento.. buscar por orden de compra y producto
            notaED.setCantRecibida((int) notaIngresoDetalleBl.getCantIngresada(obj.getProducto().getIdproducto(), id));
            notaED.setCantPendiente(notaED.getCantSolicitada() - notaED.getCantRecibida());
            notaED.setCantIngreso(notaED.getCantPendiente());
            //validar que solo se agreguen los productos que faltan recepcionar
            //if (notaED.getCantRecibida() < notaED.getCantSolicitada()) {
            listNotaEntradaDetalle.add(notaED);
            //}
        }
    }

    public void onRowEdit(RowEditEvent event) {
        notaEntradaDetalleTemp = new NotaEntradaDetalle();
        String msg = "";
        notaEntradaDetalleTemp.setProducto(((NotaEntradaDetalle) event.getObject()).getProducto());
        notaEntradaDetalleTemp.setCantIngreso(((NotaEntradaDetalle) event.getObject()).getCantIngreso());
        for (NotaEntradaDetalle obj : listNotaEntradaDetalle) {
            //obj.setCantPendiente(notaEntradaDetalle.getCantPendiente());
            if (obj.getProducto() == notaEntradaDetalleTemp.getProducto()) {

                if (notaEntradaDetalleTemp.getCantIngreso() > obj.getCantPendiente()) {
                    //la cantidad ingresa se debe mantener
                    obj.setCantIngreso(obj.getCantPendiente());
                    msg = "La cantidad ingresada supera a la cantidad pendiente";
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atención", msg);
                    FacesContext.getCurrentInstance().addMessage(null, message);
                }
            }
        }

    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edicion cancelada", null);
        FacesContext.getCurrentInstance().addMessage(null, msg);
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

    public OrdenCompra getOrdenCompra() {
        return ordenCompra;
    }

    public void setOrdenCompra(OrdenCompra ordenCompra) {
        this.ordenCompra = ordenCompra;
    }

    public OrdenCompraBl getOrdenCompraBl() {
        return ordenCompraBl;
    }

    public void setOrdenCompraBl(OrdenCompraBl ordenCompraBl) {
        this.ordenCompraBl = ordenCompraBl;
    }

    public NotaEntradaDetalle getNotaEntradaDetalleTemp() {
        return notaEntradaDetalleTemp;
    }

    public void setNotaEntradaDetalleTemp(NotaEntradaDetalle notaEntradaDetalleTemp) {
        this.notaEntradaDetalleTemp = notaEntradaDetalleTemp;
    }

    private long actualizarEstadoOrdenCompra() {
        OrdenCompra temp = new OrdenCompra();
        if (notaEntrada.getOrdenCompra() != null) {
            temp = ordenCompraBl.buscar(notaEntrada.getOrdenCompra().getIdordencompra());
            //temp = notaEntrada.getOrdenCompra();
//            temp.setIdordencompra(notaEntrada.getOrdenCompra().getIdordencompra());            
//            temp.setNumero(notaEntrada.getOrdenCompra().getNumero());
//            temp.setFecha(notaEntrada.getOrdenCompra().getFecha());
//            temp.setProveedor(notaEntrada.getOrdenCompra().getProveedor());
//            temp.setFechaEntrega(notaEntrada.getOrdenCompra().getFechaEntrega());
//            temp.setLugarEntrega(notaEntrada.getOrdenCompra().getLugarEntrega());
//            temp.setObservacion(notaEntrada.getOrdenCompra().getObservacion());            
              temp.setEstado(ordenCompra.getEstado());
//            temp.setHoraRegistro(notaEntrada.getOrdenCompra().getHoraRegistro());
//            temp.setFechaRecepcion(new Date());
//            temp.setIdAlmacenDestino(0);
//            temp.setIdMoneda(0);
//            temp.setDocReferencia("");
//            temp.setValorBruto(notaEntrada.getOrdenCompra().getValorBruto());
//            temp.setMontoDesc(notaEntrada.getOrdenCompra().getMontoDesc());
//            temp.setValorNeto(notaEntrada.getOrdenCompra().getValorNeto());
//            temp.setMontoIgv(notaEntrada.getOrdenCompra().getMontoIgv());
//            temp.setMontoTotal(notaEntrada.getOrdenCompra().getMontoTotal());
            
            return ordenCompraBl.actualizar(temp);
        }else{
            return -1;
        }

    }
}
