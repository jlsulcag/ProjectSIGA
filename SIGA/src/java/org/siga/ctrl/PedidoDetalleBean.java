package org.siga.ctrl;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.siga.be.Pedido;
import org.siga.be.PedidoDetalle;
import org.siga.be.PedidoSeguimiento;
import org.siga.be.Producto;
import org.siga.bl.PedidoBl;
import org.siga.bl.PedidoDetalleBl;
import org.siga.bl.PedidoSeguimientoBl;
import org.siga.util.Utilitarios;

@ManagedBean
@ViewScoped
public class PedidoDetalleBean {

    @ManagedProperty(value = "#{pedidoBl}")
    private PedidoBl pedidoBl;
    @ManagedProperty(value = "#{pedido}")
    private Pedido pedido;
    @ManagedProperty(value = "#{pedidoDetalle}")
    private PedidoDetalle pedidoDetalle;
    @ManagedProperty(value = "#{pedidoDetalleBl}")
    private PedidoDetalleBl pedidoDetalleBl;
    @ManagedProperty(value = "#{producto}")
    private Producto producto;

    @ManagedProperty(value = "#{pedidoSeguimientoBl}")
    private PedidoSeguimientoBl pedidoSeguimientoBl;
    @ManagedProperty(value = "#{pedidoSeguimiento}")
    private PedidoSeguimiento pedidoSeguimiento;

    private PedidoDetalle selectedPedidoDetalle;
    private int cantAutorizada;
    private List<PedidoDetalle> listPedidoDetalle = new LinkedList<>();

    public PedidoDetalleBean() {
    }

    public void agregar() {
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

    

    @PostConstruct
    public void listar() {
        HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        if (httpSession.getAttribute("idPedido") != null) {
            setListPedidoDetalle(pedidoDetalleBl.listarxIdPedido(Long.parseLong(httpSession.getAttribute("idPedido").toString())));
        }
        //calcularTotal(getListOrdenCompraDetalles());
        //setListOrdenCompraDetalles(ordenCompraDetalleBl.listar());
    }

    public void onRowEdit(RowEditEvent event) {
        selectedPedidoDetalle = new PedidoDetalle();
        selectedPedidoDetalle = pedidoDetalleBl.buscarxID(((PedidoDetalle) event.getObject()).getIdpedidodetalle());
        selectedPedidoDetalle.setCantidadAutorizada(pedidoDetalle.getCantidadAutorizada());

        pedidoDetalleBl.actualizar(selectedPedidoDetalle);

    }

    public void actualizarPedido() {
        HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        if (httpSession.getAttribute("idPedido") != null) {  
            pedido = pedidoBl.buscarXid(Long.parseLong(httpSession.getAttribute("idPedido").toString()));
            pedidoSeguimiento.setPedido(pedido);
            pedidoSeguimiento.setFecha(new Date());
            pedidoSeguimiento.setHora(Utilitarios.horaActual());
            pedidoSeguimiento.setNumero(pedidoSeguimientoBl.maxNumero(pedido.getIdpedido())+ 1);
            pedidoSeguimiento.setObservacion(pedidoSeguimiento.getObservacion().toUpperCase().trim());
            HttpSession sesionUser = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            if (sesionUser.getAttribute("idUsuario") != null) {
                pedidoSeguimiento.setIdUser(Long.parseLong(sesionUser.getAttribute("idUsuario").toString()));
            } else {
                pedidoSeguimiento.setIdUser(0);
            }
            pedidoSeguimientoBl.registrar(pedidoSeguimiento);
            limpiar();
        }
    }
    
    public void limpiar() {
        pedidoSeguimiento.setIdpedidoseguimiento(0);
        pedidoSeguimiento.setObservacion("");
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edicion cancelada", null);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
        System.out.println("valor antiguo........... " + oldValue);
        System.out.println("valor nuevo........... " + newValue);
        if (newValue != null && !newValue.equals(oldValue)) {

        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void onRowSelect(SelectEvent event) {
        System.out.println("pedido detalle seleccionado " + ((PedidoDetalle) event.getObject()).getIdpedidodetalle());
        //FacesMessage msg = new FacesMessage("Car Selected", ((Car) event.getObject()).getId());
        //FacesContext.getCurrentInstance().addMessage(null, msg);
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
    public PedidoDetalleBl getPedidoDetalleBl() {
        return pedidoDetalleBl;
    }

    public void setPedidoDetalleBl(PedidoDetalleBl pedidoDetalleBl) {
        this.pedidoDetalleBl = pedidoDetalleBl;
    }

    public PedidoDetalle getSelectedPedidoDetalle() {
        return selectedPedidoDetalle;
    }

    public void setSelectedPedidoDetalle(PedidoDetalle selectedPedidoDetalle) {
        this.selectedPedidoDetalle = selectedPedidoDetalle;
    }

    public int getCantAutorizada() {
        return cantAutorizada;
    }

    public void setCantAutorizada(int cantAutorizada) {
        this.cantAutorizada = cantAutorizada;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public PedidoSeguimientoBl getPedidoSeguimientoBl() {
        return pedidoSeguimientoBl;
    }

    public void setPedidoSeguimientoBl(PedidoSeguimientoBl pedidoSeguimientoBl) {
        this.pedidoSeguimientoBl = pedidoSeguimientoBl;
    }

    public PedidoSeguimiento getPedidoSeguimiento() {
        return pedidoSeguimiento;
    }

    public void setPedidoSeguimiento(PedidoSeguimiento pedidoSeguimiento) {
        this.pedidoSeguimiento = pedidoSeguimiento;
    }
}
