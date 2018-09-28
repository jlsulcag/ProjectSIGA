package org.siga.ctrl;

import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.siga.be.Pedido;
import org.siga.be.PedidoDetalle;
import org.siga.be.PedidoSeguimiento;
import org.siga.be.Usuario;
import org.siga.bl.PedidoBl;
import org.siga.bl.PedidoDetalleBl;
import org.siga.bl.PedidoSeguimientoBl;

@ManagedBean
@ViewScoped
public class PedidoPorUsuarioBean {

    @ManagedProperty(value = "#{pedidoBl}")
    private PedidoBl pedidoBl;
    @ManagedProperty(value = "#{pedido}")
    private Pedido pedido;

    @ManagedProperty(value = "#{pedidoDetalleBl}")
    private PedidoDetalleBl pedidoDetalleBl;
    @ManagedProperty(value = "#{pedidoDetalle}")
    private PedidoDetalle pedidoDetalle;

    @ManagedProperty(value = "#{pedidoSeguimiento}")
    private PedidoSeguimiento pedidoSeguimiento;
    @ManagedProperty(value = "#{pedidoSeguimientoBl}")
    private PedidoSeguimientoBl pedidoSeguimientoBl;

    private List<Pedido> listPedidos;

    private Pedido selectedPedido;

    public PedidoPorUsuarioBean() {
    }

    @PostConstruct
    public void listarPedidos() {
        listPedidos = new LinkedList<>();
        //HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        Usuario user = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        if (user != null) {
            for (Pedido obj : pedidoBl.listarPedidoxUser(user.getIdusuario())) {
                pedidoSeguimiento = pedidoSeguimientoBl.buscarxidPedido(obj.getIdpedido());
                if (pedidoSeguimiento != null) {
                    obj.setEstado(pedidoSeguimiento.getEstado().getDescripcion());
                }
                listPedidos.add(obj);
            }
            setListPedidos(listPedidos);
        }

    }

    public void onRowSelect(SelectEvent event) {
        //FacesMessage msg = new FacesMessage("Car Selected", ((Pedido)event.getObject()).getObservacion());
        //FacesContext.getCurrentInstance().addMessage(null, msg);
        //setListPedidoDetalle(pedidoDetalleBl.listarxIdPedido(((Pedido) event.getObject()).getIdpedido()));
    }

    public void onRowUnselect(UnselectEvent event) {
        FacesMessage msg = new FacesMessage("Car Unselected", ((Pedido) event.getObject()).getObservacion());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public String redirigir() {
        HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        httpSession.setAttribute("idPedido", getPedido().getIdpedido());
        return "PedidosporUsuarioDetalle?faces-redirect=true";

    }

    /*Metodos de acceso y seteo*/
    public PedidoBl getPedidoBl() {
        return pedidoBl;
    }

    public void setPedidoBl(PedidoBl pedidoBl) {
        this.pedidoBl = pedidoBl;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public PedidoDetalleBl getPedidoDetalleBl() {
        return pedidoDetalleBl;
    }

    public void setPedidoDetalleBl(PedidoDetalleBl pedidoDetalleBl) {
        this.pedidoDetalleBl = pedidoDetalleBl;
    }

    public PedidoDetalle getPedidoDetalle() {
        return pedidoDetalle;
    }

    public void setPedidoDetalle(PedidoDetalle pedidoDetalle) {
        this.pedidoDetalle = pedidoDetalle;
    }

    public PedidoSeguimiento getPedidoSeguimiento() {
        return pedidoSeguimiento;
    }

    public void setPedidoSeguimiento(PedidoSeguimiento pedidoSeguimiento) {
        this.pedidoSeguimiento = pedidoSeguimiento;
    }

    public PedidoSeguimientoBl getPedidoSeguimientoBl() {
        return pedidoSeguimientoBl;
    }

    public void setPedidoSeguimientoBl(PedidoSeguimientoBl pedidoSeguimientoBl) {
        this.pedidoSeguimientoBl = pedidoSeguimientoBl;
    }

    public List<Pedido> getListPedidos() {
        return listPedidos;
    }

    public void setListPedidos(List<Pedido> listPedidos) {
        this.listPedidos = listPedidos;
    }

    public Pedido getSelectedPedido() {
        return selectedPedido;
    }

    public void setSelectedPedido(Pedido selectedPedido) {
        this.selectedPedido = selectedPedido;
    }

}
