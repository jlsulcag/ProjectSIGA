package org.siga.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class Utilitario {
    public static Tarea setTareaEvento(Tarea a) {
        FacesContext context = FacesContext.getCurrentInstance(); 
        if(a.getRepuesta()>=0){            
            a.proceso();          
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"ATENCION",a.getAccion()+" CORRECTO"));  
        }else{
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ATENCION","ERROR DE "+a.getAccion()));           
        }
        return a;
    }
}
