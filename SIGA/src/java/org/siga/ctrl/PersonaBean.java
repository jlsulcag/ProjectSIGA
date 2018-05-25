
package org.siga.ctrl;

import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.siga.be.Persona;
import org.siga.bl.PersonaBl;

@ManagedBean
@ViewScoped
public class PersonaBean {
    
    @ManagedProperty(value = "#{persona}")
    private Persona persona;
    @ManagedProperty(value = "#{personaBl}")
    private PersonaBl personaBl;
    @ManagedProperty(value = "#{personaSelect}")
    private Persona selectPersona;
    
    private AdminUsuarioBean oAdminUsuarioBean;
    //
    private List<Persona> listPersonas = new LinkedList<>();
    private String txtBusqueda;
    private String txtDni;
    
    public PersonaBean() {
    }
    
    @PostConstruct
    public void listar(){
        setListPersonas(personaBl.listarAll());
    }
    
    public void buscarxNombres(){
        setListPersonas(personaBl.listarRef(txtBusqueda.toUpperCase()));
    }
    
    public void buscarxDni(){
        setListPersonas(personaBl.listarxDni(txtDni));
    }
    
    public void onRowSelect(SelectEvent event) {
//        FacesMessage msg = new FacesMessage("Car Selected", ((Persona) event.getObject()).getIdpersona()+"");
//        FacesContext.getCurrentInstance().addMessage(null, msg);
        System.out.println("ID ... "+((Persona) event.getObject()).getIdpersona());
        //RequestContext.getCurrentInstance().closeDialog("dlgPersonas");
        persona = ((Persona) event.getObject());
        HttpSession sesion = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        sesion.setAttribute("persona", persona);
        RequestContext.getCurrentInstance().execute("PF('dlgPersonas').hide()");
        oAdminUsuarioBean = new AdminUsuarioBean();
        oAdminUsuarioBean.enviaDatosPersona(sesion);
        //RequestContext.getCurrentInstance().update("");
    }
 
    public void onRowUnselect(UnselectEvent event) {
//        FacesMessage msg = new FacesMessage("Car Unselected", ((Persona) event.getObject()).getIdpersona()+"");
//        FacesContext.getCurrentInstance().addMessage(null, msg);
        System.out.println("ID ... "+((Persona) event.getObject()).getIdpersona());
    }
    
    //constructores
    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public PersonaBl getPersonaBl() {
        return personaBl;
    }

    public void setPersonaBl(PersonaBl personaBl) {
        this.personaBl = personaBl;
    }

    public List<Persona> getListPersonas() {
        return listPersonas;
    }

    public void setListPersonas(List<Persona> listPersonas) {
        this.listPersonas = listPersonas;
    }

    public String getTxtBusqueda() {
        return txtBusqueda;
    }

    public void setTxtBusqueda(String txtBusqueda) {
        this.txtBusqueda = txtBusqueda;
    }

    public String getTxtDni() {
        return txtDni;
    }

    public void setTxtDni(String txtDni) {
        this.txtDni = txtDni;
    }

    public Persona getSelectPersona() {
        return selectPersona;
    }

    public void setSelectPersona(Persona selectPersona) {
        this.selectPersona = selectPersona;
    }
    
}
