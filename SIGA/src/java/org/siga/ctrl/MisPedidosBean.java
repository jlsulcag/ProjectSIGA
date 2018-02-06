
package org.siga.ctrl;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.siga.be.Pedido;
import org.siga.be.PedidoDetalle;
import org.siga.bl.PedidoBl;
import org.siga.bl.PedidoDetalleBl;

@ManagedBean
@ViewScoped
public class MisPedidosBean {

    @ManagedProperty(value = "#{pedidoBl}")
    private PedidoBl pedidoBl;
    @ManagedProperty(value = "#{pedido}")
    private Pedido pedido;
    
    private Pedido selectedPedido;
    private PedidoDetalle selectPedidoDetalle;
    
    private List<Pedido> listPedidos;
    private List<Pedido> listPedidosTemp;
    
    @ManagedProperty(value = "#{pedidoDetalleBl}")
    private PedidoDetalleBl pedidoDetalleBl;
    @ManagedProperty(value = "#{pedidoDetalle}")
    private PedidoDetalle pedidoDetalle;
    private List<PedidoDetalle> listPedidoDetalle;
    
    public MisPedidosBean() {
    }
    @PostConstruct
    public void listarPedidos(){
        setListPedidos(pedidoBl.listarFull(""));
    }
    
    public void onRowSelect(SelectEvent event) {
        //FacesMessage msg = new FacesMessage("Car Selected", ((Pedido)event.getObject()).getObservacion());
        //FacesContext.getCurrentInstance().addMessage(null, msg);
        setListPedidoDetalle(pedidoDetalleBl.listarxIdPedido(((Pedido)event.getObject()).getIdpedido()));
    }
 
    public void onRowUnselect(UnselectEvent event) {
        FacesMessage msg = new FacesMessage("Car Unselected", ((Pedido)event.getObject()).getObservacion());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
         System.out.println("valor antiguo........... "+oldValue);
         System.out.println("valor nuevo........... "+newValue);
         
         //pedidoDetalle = (PedidoDetalle) ((DataTable)event.getComponent()).getRowData();
         
         System.out.println("valor nuevo........... "+pedidoDetalle.getCantidadAutorizada());
        if(newValue != null && !newValue.equals(oldValue)) {
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
