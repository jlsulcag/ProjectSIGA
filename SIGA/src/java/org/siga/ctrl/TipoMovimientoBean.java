
package org.siga.ctrl;

import java.util.LinkedList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import org.siga.be.TipoMovimiento;
import org.siga.bl.TipoMovimientoBl;

@ManagedBean
@ViewScoped
public class TipoMovimientoBean {

    @ManagedProperty(value = "#{tipoMovimiento}")
    private TipoMovimiento tipoMovimiento;
    @ManagedProperty(value = "#{tipoMovimientoBl}")
    private TipoMovimientoBl tipoMovimientoBl;
    
    private List<SelectItem> selectOneItemsTipoMovimiento;
    
    public TipoMovimientoBean() {
    }

    public TipoMovimiento getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(TipoMovimiento tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public TipoMovimientoBl getTipoMovimientoBl() {
        return tipoMovimientoBl;
    }

    public void setTipoMovimientoBl(TipoMovimientoBl tipoMovimientoBl) {
        this.tipoMovimientoBl = tipoMovimientoBl;
    }

    public List<SelectItem> getSelectOneItemsTipoMovimiento() {
        this.selectOneItemsTipoMovimiento= new LinkedList<SelectItem>();
        for (TipoMovimiento obj : tipoMovimientoBl.listar()) {
            this.setTipoMovimiento(obj);
            SelectItem selectItem = new SelectItem(obj.getIdtipomov(), obj.getDescripcion());
            this.selectOneItemsTipoMovimiento.add(selectItem);
        }
        return selectOneItemsTipoMovimiento;
    }

    public void setSelectOneItemsTipoMovimiento(List<SelectItem> selectOneItemsTipoMovimiento) {
        this.selectOneItemsTipoMovimiento = selectOneItemsTipoMovimiento;
    }
    
}
