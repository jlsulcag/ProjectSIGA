
package org.siga.ctrl;

import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import org.siga.be.Dependencia;
import org.siga.bl.DependenciaBl;
import org.siga.util.MensajeView;

@ManagedBean
@ViewScoped
public class DependenciaBean {
    @ManagedProperty(value = "#{dependenciaBl}")
    private DependenciaBl dependenciaBl;
    @ManagedProperty(value = "#{dependencia}")
    private Dependencia dependencia;
    private String txtBusqueda;
    private List<Dependencia> listDependencia;
    private List<SelectItem> selectOneItemsDependencia;
    private long res;
    
    public DependenciaBean() {
    }
    
    @PostConstruct
    public void listar(){
        setListDependencia(dependenciaBl.listar());
    }
    public void registrar(){
        dependencia.setDescripcion(dependencia.getDescripcion().toUpperCase());
        dependencia.setEstado("ACT");
        res = dependenciaBl.registrar(dependencia);
        if (res == 0) {
            MensajeView.registroCorrecto();
        } else {
            MensajeView.registroError();
        }
        listar();
    }
    public void actualizar(){
        Dependencia temp = new Dependencia();
        temp = buscarId();
        temp.setDescripcion(dependencia.getDescripcion().toUpperCase());
        temp.setEstado(dependencia.getEstado().toUpperCase());
        res = dependenciaBl.actualizar(temp);
        if (res == 0) {
            MensajeView.actCorrecto();
        } else {
            MensajeView.actError();
        }
        listar();
    }
    public void eliminar(){
        Dependencia temp = new Dependencia();
        temp = buscarId();
        res = dependenciaBl.eliminar(temp);
        if(res == 0){
            MensajeView.eliminacionCorrecta();
        }else{
            MensajeView.eliminacionErronea();
        }
        listar();
    }
    public Dependencia buscarId(){
        return dependenciaBl.buscar(dependencia.getIddependencia());
    }
    public void limpiar(){
        dependencia.setIddependencia(0);
        dependencia.setDescripcion("");
        dependencia.setEstado("");
    }
    public void listarRef(){
        setListDependencia(dependenciaBl.listarRef(getTxtBusqueda().toUpperCase()));
    }

    public DependenciaBl getDependenciaBl() {
        return dependenciaBl;
    }

    public void setDependenciaBl(DependenciaBl dependenciaBl) {
        this.dependenciaBl = dependenciaBl;
    }

    public Dependencia getDependencia() {
        return dependencia;
    }

    public void setDependencia(Dependencia dependencia) {
        this.dependencia = dependencia;
    }

    public String getTxtBusqueda() {
        return txtBusqueda;
    }

    public void setTxtBusqueda(String txtBusqueda) {
        this.txtBusqueda = txtBusqueda;
    }

    public List<Dependencia> getListDependencia() {
        return listDependencia;
    }

    public void setListDependencia(List<Dependencia> listDependencia) {
        this.listDependencia = listDependencia;
    }

    public List<SelectItem> getSelectOneItemsDependencia() {
        this.selectOneItemsDependencia = new LinkedList<SelectItem>();
        for (Dependencia obj : listarDependencia()) {
            this.setDependencia(obj);
            SelectItem selectItem = new SelectItem(dependencia.getIddependencia(), dependencia.getDescripcion());
            this.selectOneItemsDependencia.add(selectItem);
        }
        return selectOneItemsDependencia;
    }

    public void setSelectOneItemsDependencia(List<SelectItem> selectOneItemsDependencia) {
        this.selectOneItemsDependencia = selectOneItemsDependencia;
    }

    private List<Dependencia> listarDependencia() {
        return dependenciaBl.listar();
    }
    
}
