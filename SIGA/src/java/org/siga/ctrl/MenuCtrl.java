
package org.siga.ctrl;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;
import org.siga.be.Menu;
import org.siga.bl.MenuBl;

@ManagedBean
@SessionScoped
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
        setModel(new DefaultMenuModel());
        this.establecerPermisos();
    }

    public void listarMenus(){
        try {
            listMenu = menuBl.list();
        } catch (Exception e) {
            //mensaje jsf
        }
    }
    
    public void  establecerPermisos(){
        for(Menu m : listMenu){            
            if(m.getTipo().equals("S")){
                DefaultSubMenu firstSubMenu = new DefaultSubMenu(m.getNombre());
                for(Menu i : listMenu){
                    Menu subMenu = i.getMenu();
                    if(subMenu != null){
                        if(subMenu.getIdmenu() == m.getIdmenu()){
                            DefaultMenuItem item = new DefaultMenuItem(i.getNombre());
                            firstSubMenu.addElement(item);
                        }
                    }
                }
                model.addElement(firstSubMenu);
            }else if(m.getMenu() == null){
                DefaultMenuItem item = new DefaultMenuItem(m.getNombre());
                model.addElement(item);
            }
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
