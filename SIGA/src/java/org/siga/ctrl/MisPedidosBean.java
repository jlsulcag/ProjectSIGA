package org.siga.ctrl;

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
import org.siga.bl.PedidoBl;
import org.siga.bl.PedidoDetalleBl;
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
    private List<PedidoDetalle> listPedidoDetalle;
    
    private long res;

    public MisPedidosBean() {
    }

    @PostConstruct
    public void listarPedidos() {
        setListPedidos(pedidoBl.listarFull(""));
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
        selectedPedido.setIdpedido(((Pedido)event.getObject()).getIdpedido());
        System.out.println("id pedido .. "+selectedPedido.getIdpedido());
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
        HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        httpSession.setAttribute("idPedido", getPedido().getIdpedido());
        return "PedidoDetalle";
    }
    
    public void actualizarPedidoDetalle(){
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

}
