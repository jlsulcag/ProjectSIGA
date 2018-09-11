package org.siga.ctrl;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.omnifaces.util.Faces;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;
import org.siga.be.Menu;
import org.siga.be.Usuario;
import org.siga.be.UsuarioRol;
import org.siga.bl.MenuBl;
import org.siga.bl.UsuarioRolBl;

@ManagedBean
@SessionScoped
public class MenuCtrl {

    @ManagedProperty(value = "#{menuBl}")
    private MenuBl menuBl;
    @ManagedProperty(value = "#{menu}")
    private Menu menu;

    @ManagedProperty(value = "#{usuarioRolBl}")
    private UsuarioRolBl usuarioRolBl;

    private List<Menu> listMenu;
    private MenuModel model;

    public MenuCtrl() {
    }

    @PostConstruct
    public void init() {
        listarMenus();
        setModel(new DefaultMenuModel());
        this.establecerPermisos();
    }

    public void listarMenus() {
        try {
            listMenu = menuBl.list();
        } catch (Exception e) {
            //mensaje jsf
        }
    }

    public void establecerPermisos() {
        Usuario us = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        if (us != null) {
            UsuarioRol ur = getUsuarioRolBl().buscarxIdUsuario(us.getIdusuario());
            for (Menu m : listMenu) {
                if (m.getTipo().equals("S") && m.getRol().getIdrol() == ur.getRol().getIdrol()) {
                    DefaultSubMenu firstSubMenu = new DefaultSubMenu(m.getNombre());
                    for (Menu i : listMenu) {
                        Menu subMenu = i.getMenu();
                        if (subMenu != null) {
                            if (subMenu.getIdmenu() == m.getIdmenu()) {
                                DefaultMenuItem item = new DefaultMenuItem(i.getNombre());
                                item.setUrl(i.getUrl());
                                firstSubMenu.addElement(item);
                            }
                        }
                    }
                    model.addElement(firstSubMenu);
                } else if (m.getMenu() == null && m.getRol().getIdrol() == ur.getRol().getIdrol()) {
                    DefaultMenuItem item = new DefaultMenuItem(m.getNombre());
                    item.setUrl(m.getUrl());
                    model.addElement(item);
                }
            }
        } else {
            try {
                Faces.redirect("/page/login_1?faces-redirect=true", "Prueba");
            } catch (IOException ex) {
                Logger.getLogger(MenuCtrl.class.getName()).log(Level.SEVERE, null, ex);
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

    public UsuarioRolBl getUsuarioRolBl() {
        return usuarioRolBl;
    }

    public void setUsuarioRolBl(UsuarioRolBl usuarioRolBl) {
        this.usuarioRolBl = usuarioRolBl;
    }

}
