
package org.siga.ctrl;

import java.util.LinkedList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import org.siga.be.Rol;
import org.siga.bl.RolBl;

@ManagedBean
@ViewScoped
public class RolBean {

    @ManagedProperty(value = "#{rol}")
    private Rol rol;
    @ManagedProperty(value = "#{rolBl}")
    private RolBl rolBl;
    
    private List<Rol> listRoles;
    private List<SelectItem> selectOneItemsRol;
    
    public RolBean() {
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public RolBl getRolBl() {
        return rolBl;
    }

    public void setRolBl(RolBl rolBl) {
        this.rolBl = rolBl;
    }

    public List<Rol> getListRoles() {
        return listRoles;
    }

    public void setListRoles(List<Rol> listRoles) {
        this.listRoles = listRoles;
    }

    public List<SelectItem> getSelectOneItemsRol() {
        this.selectOneItemsRol= new LinkedList<SelectItem>();
        for (Rol obj : listarRoles()) {
            this.setRol(obj);
            SelectItem selectItem = new SelectItem(obj.getIdrol(), obj.getRol());
            this.selectOneItemsRol.add(selectItem);
        }
        return selectOneItemsRol;
    }

    public void setSelectOneItemsRol(List<SelectItem> selectOneItemsRol) {
        this.selectOneItemsRol = selectOneItemsRol;
    }

    private List<Rol> listarRoles() {
        return rolBl.listar();
    }
    
}
