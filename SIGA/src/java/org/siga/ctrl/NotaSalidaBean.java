
package org.siga.ctrl;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.siga.be.NotaSalida;
import org.siga.be.NotaSalidaDetalle;
import org.siga.be.Producto;
import org.siga.bl.NotaSalidaBl;
import org.siga.bl.NotaSalidaDetalleBl;
import org.siga.bl.ProductoBl;

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
    
    private List<NotaSalidaDetalle> listNotaSalidas = new LinkedList<>();
    private boolean pedidoxUnidad;
    private int totalProductos;
    
    public NotaSalidaBean() {
    }
    
    @PostConstruct
    public void inicio(){
        notaSalida.setNumero(maxNumero()+1);
        notaSalida.setFechaReg(new Date());
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
    
    public void inicioNew() {
        notaSalidaDetalle.setProducto(new Producto());
        producto.setFraccion(0);
        producto.setUnidadMedida(null);
        setPedidoxUnidad(false);
        notaSalidaDetalle.setCantidad(0);
        setTotalProductos(0);
    }

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
    
}
