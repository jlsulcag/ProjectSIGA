package org.siga.ctrl;

import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpSession;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.siga.be.Pedido;
import org.siga.be.PedidoDetalle;
import org.siga.be.PedidoSeguimiento;
import org.siga.be.Rol;
import org.siga.be.Usuario;
import org.siga.be.UsuarioRol;
import org.siga.bl.PedidoBl;
import org.siga.bl.PedidoDetalleBl;
import org.siga.bl.PedidoSeguimientoBl;
import org.siga.bl.RolBl;
import org.siga.bl.UsuarioRolBl;
import org.siga.util.MensajeView;

@ManagedBean
@ViewScoped
public class MisPedidosBean {

    @ManagedProperty(value = "#{pedidoBl}")
    private PedidoBl pedidoBl;
    @ManagedProperty(value = "#{pedido}")
    private Pedido pedido;

    private Pedido selectedPedido;
    private PedidoDetalle temp;
    private PedidoDetalle selectPedidoDetalle;

    private List<Pedido> listPedidos;
    private List<Pedido> listPedidosTemp;

    @ManagedProperty(value = "#{pedidoDetalleBl}")
    private PedidoDetalleBl pedidoDetalleBl;
    @ManagedProperty(value = "#{pedidoDetalle}")
    private PedidoDetalle pedidoDetalle;

    @ManagedProperty(value = "#{pedidoSeguimiento}")
    private PedidoSeguimiento pedidoSeguimiento;
    @ManagedProperty(value = "#{pedidoSeguimientoBl}")
    private PedidoSeguimientoBl pedidoSeguimientoBl;

    private List<PedidoDetalle> listPedidoDetalle;

    private long res;

    public MisPedidosBean() {
    }

    @PostConstruct
    public void listarPedidos() {
        listPedidos = new LinkedList<>();
        for (Pedido obj : pedidoBl.listarFull("")) {
            pedidoSeguimiento = pedidoSeguimientoBl.buscarxidPedido(obj.getIdpedido());
            if (pedidoSeguimiento != null) {
                obj.setEstado(pedidoSeguimiento.getEstado().getDescripcion());
            }
            listPedidos.add(obj);
        }
        setListPedidos(listPedidos);
    }

    public void onRowSelect(SelectEvent event) {
        //FacesMessage msg = new FacesMessage("Car Selected", ((Pedido)event.getObject()).getObservacion());
        //FacesContext.getCurrentInstance().addMessage(null, msg);
        setListPedidoDetalle(pedidoDetalleBl.listarxIdPedido(((Pedido) event.getObject()).getIdpedido()));
    }

    public void onRowUnselect(UnselectEvent event) {
        FacesMessage msg = new FacesMessage("Car Unselected", ((Pedido) event.getObject()).getObservacion());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowEdit(RowEditEvent event) {
        System.out.println("ingreso al metodo ..........");
        selectedPedido = new Pedido();
        selectedPedido.setIdpedido(((Pedido) event.getObject()).getIdpedido());
        System.out.println("id pedido .. " + selectedPedido.getIdpedido());
//        temp = new PedidoDetalle();
//        temp = (PedidoDetalle) event.getObject();
//        System.out.println("pedido detalle ... "+temp.getCantidadSolicitada());
//        String msg = "";
//        temp.setIdpedidodetalle(((PedidoDetalle) event.getObject()).getIdpedidodetalle());
//        System.out.println("ID enviado "+temp.getIdpedidodetalle());
//        PedidoDetalle temp2 = new PedidoDetalle();
//        temp2 = pedidoDetalleBl.buscar(temp.getIdpedidodetalle());
//        System.out.println("Estado modificado..." + getPedidoDetalle().getIdpedidodetalle());
//        temp2.setCantidadAutorizada(getPedidoDetalle().getCantidadAutorizada());
    }

    public String redirigir() {
        String url = "";
        HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        httpSession.setAttribute("idPedido", getPedido().getIdpedido());
        switch (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("rol").toString()) {
            case "ALMACENERO":
                url = "PedidoDetalle";
                break;
            case "JEFE LOGISTICA":
                url = "PedidoDetalleAdmin";
                break;
        }
        return url + "?faces-redirect=true";

    }

    public void actualizarPedidoDetalle() {
        PedidoDetalle temp = new PedidoDetalle();
        temp = buscarId();
        temp.setCantidadAutorizada(pedidoDetalle.getCantidadAutorizada());

        res = pedidoDetalleBl.actualizar(temp);
        if (res == 0) {
            MensajeView.actCorrecto();
        } else {
            MensajeView.actError();
        }
        //listar();
    }

    private PedidoDetalle buscarId() {
        return pedidoDetalleBl.buscarxID(pedidoDetalle.getIdpedidodetalle());
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edicion cancelada", null);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
        PedidoDetalle old = (PedidoDetalle) event.getOldValue();
        System.out.println("valor antiguo........... " + oldValue);
        System.out.println("obj antiguo........... " + old.getCantidadAutorizada());

        System.out.println("valor nuevo........... " + newValue);

        //pedidoDetalle = (PedidoDetalle) ((DataTable)event.getComponent()).getRowData();
        System.out.println("valor nuevo........... " + pedidoDetalle.getCantidadAutorizada());

        //System.out.println("valor nuevo........... "+selectPedidoDetalle.getCantidadAutorizada());
        if (newValue != null && !newValue.equals(oldValue)) {
            System.out.println("dentro del if .................");
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

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

    public List<Pedido> getListPedidos() {
        return listPedidos;
    }

    public void setListPedidos(List<Pedido> listPedidos) {
        this.listPedidos = listPedidos;
    }

    public List<Pedido> getListPedidosTemp() {
        return listPedidosTemp;
    }

    public void setListPedidosTemp(List<Pedido> listPedidosTemp) {
        this.listPedidosTemp = listPedidosTemp;
    }

    public Pedido getSelectedPedido() {
        return selectedPedido;
    }

    public void setSelectedPedido(Pedido selectedPedido) {
        this.selectedPedido = selectedPedido;
    }

    public List<PedidoDetalle> getListPedidoDetalle() {
        return listPedidoDetalle;
    }

    public void setListPedidoDetalle(List<PedidoDetalle> listPedidoDetalle) {
        this.listPedidoDetalle = listPedidoDetalle;
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

    public PedidoDetalle getSelectPedidoDetalle() {
        return selectPedidoDetalle;
    }

    public void setSelectPedidoDetalle(PedidoDetalle selectPedidoDetalle) {
        this.selectPedidoDetalle = selectPedidoDetalle;
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

}
