
package org.siga.ctrl;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.siga.be.Almacen;
import org.siga.be.AlmacenProducto;
import org.siga.be.NotaSalida;
import org.siga.be.NotaSalidaDetalle;
import org.siga.be.Producto;
import org.siga.bl.AlmacenProductoBl;
import org.siga.bl.NotaSalidaBl;
import org.siga.bl.NotaSalidaDetalleBl;
import org.siga.bl.ProductoBl;
import org.siga.util.MensajeView;

@ManagedBean
@ViewScoped
public class NotaSalidaBean {
    
    @ManagedProperty(value = "#{notaSalida}")
    private NotaSalida notaSalida;
    @ManagedProperty(value = "#{notaSalidaBl}")
    private NotaSalidaBl notaSalidaBl;
    
    @ManagedProperty(value = "#{notaSalidaDetalle}")
    private NotaSalidaDetalle notaSalidaDetalle;
    @ManagedProperty(value = "#{notaSalidaDetalleBl}")
    private NotaSalidaDetalleBl notaSalidaDetalleBl;
    
    @ManagedProperty(value = "#{producto}")
    private Producto producto;
    @ManagedProperty(value = "#{productoBl}")
    private ProductoBl productoBl;
    
    @ManagedProperty(value = "#{almacenProducto}")
    private AlmacenProducto almacenProducto;
    @ManagedProperty(value = "#{almacenProductoBl}")
    private AlmacenProductoBl almacenProductoBl;
    
    private List<NotaSalidaDetalle> listNotaSalidas = new LinkedList<>();
    private boolean pedidoxUnidad;
    private int totalProductos;
    
    public NotaSalidaBean() {
    }
    
    @PostConstruct
    public void inicio(){
        notaSalida.setNumero(maxNumero()+1);
        notaSalida.setFechaReg(new Date());
        listNotaSalidas.clear();
        notaSalida.setObservacion("");
        notaSalida.setAlmacenOrigen(new Almacen());
        notaSalida.setAlmacenDestino(new Almacen());
    }
    
    public int maxNumero(){
        return notaSalidaBl.maxNumero();
    }
    
    public void buscarProducto() {
        producto = getProductoBl().buscarxID(notaSalidaDetalle.getProducto().getIdproducto());
    }
    
    public void setIsPedidoUnitario() {
        setPedidoxUnidad(pedidoxUnidad);
        if (notaSalidaDetalle.getCantidad() > 0) {
            calcularTotalProductos();
        }
    }

    public void calcularTotalProductos() {
        if (pedidoxUnidad) {
            setTotalProductos(notaSalidaDetalle.getCantidad());
        } else {
            setTotalProductos(notaSalidaDetalle.getCantidad() * producto.getFraccion());
        }
    }
    
    public void agregar() {
        NotaSalidaDetalle temp = new NotaSalidaDetalle();
        temp.setProducto(producto);
        temp.setCantidad(notaSalidaDetalle.getCantidad());
        if (pedidoxUnidad) {
            temp.setUnidadmedida("UNIDAD");
        } else {
            temp.setUnidadmedida(producto.getUnidadMedida().getDescripcion());
        }
        getListNotaSalidas().add(temp);

    }
    
    public void registrar() {
        long id = -1;
        long id2 = -1;
        if (!listNotaSalidas.isEmpty()) {
            id = registrarNotaSalida();
            if (id > 0) {
                id2 = registrarNotaSalidaDetalle();
                if (id2 > 0) {
                    MensajeView.registroCorrecto();
//                    actualizarStock();
                    //actuyalizar el estado del pedido si fuese el caso
                    inicio();
                } else {
                    MensajeView.registroError();
                }
            } else {
                MensajeView.registroError();
            }
        } else {
            MensajeView.listVacia();
        }

    }
    
    public long registrarNotaSalida() {
        notaSalida.setIdUserReg(0);
        notaSalida.setTipomovimiento(null);
        notaSalida.setPedido(null);
        notaSalida.setObservacion(notaSalida.getObservacion().toUpperCase());
        notaSalidaBl.registrar(notaSalida);
        return notaSalida.getIdnotasalida();
    }
    
    private long registrarNotaSalidaDetalle() {
        long id = -1;
        for (NotaSalidaDetalle obj : listNotaSalidas) {
            obj.setNotasalida(notaSalida);
            notaSalidaDetalleBl.registrar(obj);
            id = obj.getIdnotasalidadetalle();
        }
        return id;
    }
    
    public void inicioNew() {
        notaSalidaDetalle.setProducto(new Producto());
        producto.setFraccion(0);
        producto.setUnidadMedida(null);
        setPedidoxUnidad(false);
        notaSalidaDetalle.setCantidad(0);
        setTotalProductos(0);
    }
    /*
    public int actualizarStock(){
        System.out.println("pedido    "+notaSalida);
        System.out.println("Detalle    "+listNotaSalidas);
        int res = -1;
        for (NotaSalidaDetalle obj : listNotaSalidas) {
            AlmacenProducto temp = new AlmacenProducto();
            almacenProducto.setProducto(obj.getProducto());
            almacenProducto.setAlmacen(obj.getNotasalida().getAlmacenOrigen());
            almacenProducto.setLote(obj.getProducto().get);
            almacenProducto.setFechaVencimiento(obj.getFechaVencimiento());
            almacenProducto.setValor(obj.getValorCompra());
            temp = almacenProductoBl.buscarProductoxAlmacenyLote(obj.getLote(), obj.getNotaEntrada().getAlmacenDestino().getIdalmacen(), obj.getProducto());
            if (temp != null && temp.getIdalmacenproducto() != 0) {//Actualizar registro existente
                almacenProducto.setIdalmacenproducto(temp.getIdalmacenproducto());
                almacenProducto.setStockActual(obj.getCantIngreso() + temp.getStockActual());
                almacenProductoBl.actualizar(almacenProducto);
                res = 1;
            } else {//Registrar nuevo
                almacenProducto.setProducto(obj.getProducto());
                almacenProducto.setStockActual(obj.getCantIngreso());
                almacenProductoBl.registrar(almacenProducto);
                res = 1;
            }
        }
        return res;
    }
*/
    public NotaSalida getNotaSalida() {
        return notaSalida;
    }

    public void setNotaSalida(NotaSalida notaSalida) {
        this.notaSalida = notaSalida;
    }

    public NotaSalidaBl getNotaSalidaBl() {
        return notaSalidaBl;
    }

    public void setNotaSalidaBl(NotaSalidaBl notaSalidaBl) {
        this.notaSalidaBl = notaSalidaBl;
    }

    public NotaSalidaDetalle getNotaSalidaDetalle() {
        return notaSalidaDetalle;
    }

    public void setNotaSalidaDetalle(NotaSalidaDetalle notaSalidaDetalle) {
        this.notaSalidaDetalle = notaSalidaDetalle;
    }

    public NotaSalidaDetalleBl getNotaSalidaDetalleBl() {
        return notaSalidaDetalleBl;
    }

    public void setNotaSalidaDetalleBl(NotaSalidaDetalleBl notaSalidaDetalleBl) {
        this.notaSalidaDetalleBl = notaSalidaDetalleBl;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public ProductoBl getProductoBl() {
        return productoBl;
    }

    public void setProductoBl(ProductoBl productoBl) {
        this.productoBl = productoBl;
    }

    public boolean isPedidoxUnidad() {
        return pedidoxUnidad;
    }

    public void setPedidoxUnidad(boolean pedidoxUnidad) {
        this.pedidoxUnidad = pedidoxUnidad;
    }

    public int getTotalProductos() {
        return totalProductos;
    }

    public void setTotalProductos(int totalProductos) {
        this.totalProductos = totalProductos;
    }

    public List<NotaSalidaDetalle> getListNotaSalidas() {
        return listNotaSalidas;
    }

    public void setListNotaSalidas(List<NotaSalidaDetalle> listNotaSalidas) {
        this.listNotaSalidas = listNotaSalidas;
    }

    public AlmacenProducto getAlmacenProducto() {
        return almacenProducto;
    }

    public void setAlmacenProducto(AlmacenProducto almacenProducto) {
        this.almacenProducto = almacenProducto;
    }

    public AlmacenProductoBl getAlmacenProductoBl() {
        return almacenProductoBl;
    }

    public void setAlmacenProductoBl(AlmacenProductoBl almacenProductoBl) {
        this.almacenProductoBl = almacenProductoBl;
    }
    
}
