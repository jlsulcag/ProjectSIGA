
package org.siga.ctrl;

import java.util.LinkedList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import org.siga.be.PedidoEstados;
import org.siga.bl.PedidoEstadoBl;

@ManagedBean
@ViewScoped
public class PedidoEstadoBean {
    @ManagedProperty(value = "#{pedidoEstados}")
    private PedidoEstados pedidoEstados;
    @ManagedProperty(value = "#{pedidoEstadoBl}")
    private PedidoEstadoBl pedidoEstadoBl;
    
    private List<SelectItem> selectOneItemsPedidoEstado;

    public PedidoEstados getPedidoEstados() {
        return pedidoEstados;
    }

    public void setPedidoEstados(PedidoEstados pedidoEstados) {
        this.pedidoEstados = pedidoEstados;
    }

    public PedidoEstadoBl getPedidoEstadoBl() {
        return pedidoEstadoBl;
    }

    public void setPedidoEstadoBl(PedidoEstadoBl pedidoEstadoBl) {
        this.pedidoEstadoBl = pedidoEstadoBl;
    }

    public List<SelectItem> getSelectOneItemsPedidoEstado() {
        this.selectOneItemsPedidoEstado= new LinkedList<SelectItem>();
        for (PedidoEstados obj : pedidoEstadoBl.listar()) {
            this.setPedidoEstados(obj);
            SelectItem selectItem = new SelectItem(obj.getIdpedidoestado(), obj.getDescripcion());
            this.selectOneItemsPedidoEstado.add(selectItem);
        }
        return selectOneItemsPedidoEstado;
    }

    public void setSelectOneItemsPedidoEstado(List<SelectItem> selectOneItemsPedidoEstado) {
        this.selectOneItemsPedidoEstado = selectOneItemsPedidoEstado;
    }
}
