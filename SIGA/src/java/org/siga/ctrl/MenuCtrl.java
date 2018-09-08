
package org.siga.ctrl;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import org.primefaces.model.menu.MenuModel;
import org.siga.be.Menu;
import org.siga.bl.MenuBl;

@ManagedBean
@RequestScoped
public class MenuCtrl {

    @ManagedProperty(value = "#{menuBl}")
    private MenuBl menuBl;
    @ManagedProperty(value = "#{menu}")
    private Menu menu;
    
    private List<Menu> listMenu;
    private MenuModel model;
    
    public MenuCtrl() {
    }
    
    @PostConstruct
    public void init(){
        listarMenus();
    }

    public void listarMenus(){
        try {
            listMenu = menuBl.list();
        } catch (Exception e) {
            //mensaje jsf
        }
    }
    
    //Geter y Setter
    public MenuBl getMenuBl() {
        return menuBl;
    }

    public void setMenuBl(MenuBl menuBl) {
        this.menuBl = menuBl;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public List<Menu> getListMenu() {
        return listMenu;
    }

    public void setListMenu(List<Menu> listMenu) {
        this.listMenu = listMenu;
    }

    public MenuModel getModel() {
        return model;
    }

    public void setModel(MenuModel model) {
        this.model = model;
    }
    
}
