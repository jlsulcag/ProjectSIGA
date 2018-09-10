
package org.siga.ctrl;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import org.siga.be.Usuario;

@ManagedBean
@ViewScoped
public class SesionBean {

    public SesionBean() {
    }
    
    public void verificarSesion(){
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            Usuario us = (Usuario) context.getExternalContext().getSessionMap().get("usuario");
            if(us == null){
                System.out.println("usuario .... "+us.getNombre());
                context.getExternalContext().redirect("Permisos.xhtml");
            }
        } catch (Exception e) {
            //log  para guaradr error
        }
    }
    
}
