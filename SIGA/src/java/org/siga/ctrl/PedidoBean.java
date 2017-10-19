
package org.siga.ctrl;

import java.util.LinkedList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.siga.be.Pedido;
import org.siga.be.PedidoDetalle;
import org.siga.be.Producto;
import org.siga.bl.ProductoBl;

@ManagedBean
@ViewScoped
public class PedidoBean {
    @ManagedProperty(value = "#{pedido}")
    private Pedido pedido;
    @ManagedProperty(value = "#{pedidoDetalle}")
    private PedidoDetalle pedidoDetalle;
    @ManagedProperty(value = "#{producto}")
    private Producto producto;
    @ManagedProperty(value = "#{productoBl}")
    private ProductoBl productoBl;
    
    private List<PedidoDetalle> listPedidoDetalle = new LinkedList<>();
    private boolean pedidoxUnidad;
    private int totalProductos;

    public PedidoBean() {
    }
    
    public void agregar(){
        PedidoDetalle temp = new PedidoDetalle();
        temp.setProducto(producto);
        temp.setCantidadSolicitada(pedidoDetalle.getCantidadSolicitada());
        System.out.println("cantidad "+ temp.getCantidadSolicitada());
//        temp.setCantidadSurtida(pedidoDetalle.getCantidadSurtida());
//        temp.setCantidadAutorizada(pedidoDetalle.getCantidadAutorizada());
        if (pedidoxUnidad) {
            temp.setUnidadMedida("UNIDAD");
        }else{
            temp.setUnidadMedida(producto.getUnidadMedida().getDescripcion());
        }
        listPedidoDetalle.add(temp);
        
    }
    
    public void inicioNew(){
        
    }
    
    public void buscarProducto() {
        producto = getProductoBl().buscarxID(pedidoDetalle.getProducto().getIdproducto());
    }
    
    public void setIsPedidoUnitario() {
        setPedidoxUnidad(pedidoxUnidad);
        if (pedidoDetalle.getCantidadSolicitada()>0) {
            calcularTotalProductos();
        }
    }
    public void calcularTotalProductos() {
        if (pedidoxUnidad) {
            
            setTotalProductos(pedidoDetalle.getCantidadSolicitada());
        } else {
            setTotalProductos(pedidoDetalle.getCantidadSolicitada() * producto.getFraccion());
        }
    }
    
    /*get and set*/
    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public PedidoDetalle getPedidoDetalle() {
        return pedidoDetalle;
    }

    public void setPedidoDetalle(PedidoDetalle pedidoDetalle) {
        this.pedidoDetalle = pedidoDetalle;
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

    public List<PedidoDetalle> getListPedidoDetalle() {
        return listPedidoDetalle;
    }

    public void setListPedidoDetalle(List<PedidoDetalle> listPedidoDetalle) {
        this.listPedidoDetalle = listPedidoDetalle;
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

    
    
    
}
