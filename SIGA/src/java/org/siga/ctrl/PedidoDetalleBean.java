
package org.siga.ctrl;

import java.util.LinkedList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.siga.be.PedidoDetalle;
import org.siga.be.Producto;
import org.siga.bl.PedidoBl;
import org.siga.bl.ProductoBl;

@ManagedBean
@ViewScoped
public class PedidoDetalleBean {

    @ManagedProperty(value = "#{pedidoBl}")
    private PedidoBl pedidoBl;
    @ManagedProperty(value = "{pedidoDetalle}")
    private PedidoDetalle pedidoDetalle;
    @ManagedProperty(value = "#{producto}")
    private Producto producto;
    @ManagedProperty(value = "#{productoBl}")
    private ProductoBl productoBl;
    
    private List<PedidoDetalle> listPedidoDetalle = new LinkedList<>(); 
    
    public PedidoDetalleBean() {
    }
    public void agregar(){ 
        PedidoDetalle temp = new PedidoDetalle();
        
        HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        if (httpSession.getAttribute("idPedido") != null) {
            temp.setPedido(getPedidoBl().buscar(Long.parseLong(httpSession.getAttribute("idPedido").toString())));  
        }
        temp.setProducto(producto);
        temp.setCantidadSolicitada(pedidoDetalle.getCantidadSolicitada());
        temp.setCantidadAutorizada(pedidoDetalle.getCantidadAutorizada());
        temp.setCantidadSurtida(pedidoDetalle.getCantidadSurtida());
        
        getListPedidoDetalle().add(temp);
    }
    
    public void limpiar(){
       pedidoDetalle.setIdpedidodetalle(0);
       
    }
    
    
    // getter and setter
        public PedidoBl getPedidoBl() {
        return pedidoBl;
    }

    public void setPedidoBl(PedidoBl pedidoBl) {
        this.pedidoBl = pedidoBl;
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

    public List<PedidoDetalle> getListPedidoDetalle() {
        return listPedidoDetalle;
    }

    public void setListPedidoDetalle(List<PedidoDetalle> listPedidoDetalle) {
        this.listPedidoDetalle = listPedidoDetalle;
    }
        
    //-----
}
