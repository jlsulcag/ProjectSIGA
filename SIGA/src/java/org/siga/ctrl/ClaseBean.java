package org.siga.ctrl;

import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import org.siga.be.Clase;
import org.siga.be.Familia;
import org.siga.bl.ClaseBl;
import org.siga.util.MensajeView;

@ManagedBean
@ViewScoped
public class ClaseBean {

    @ManagedProperty(value = "#{claseBl}")
    private ClaseBl claseBl;

    @ManagedProperty(value = "#{clase}")
    private Clase clase;
    private String txtBusqueda;
    private long res;

    private List<Clase> listClases = new LinkedList<>();
    private List<SelectItem> selectOneItemsClase;

    public ClaseBean() {
    }

    public void registrar() {
        clase.setDescripcion(clase.getDescripcion().toUpperCase());
        clase.setEstado("ACT");
        res = getClaseBl().registrar(getClase());
        if (res == 0) {
            MensajeView.registroCorrecto();
        } else {
            MensajeView.registroError();
        }
        listar();
    }

    public void actualizar() {
        Clase temp = new Clase();
        temp = buscarId();
        temp.setDescripcion(clase.getDescripcion().toUpperCase());
        temp.setEstado(clase.getEstado().toUpperCase());
        temp.setFamilia(clase.getFamilia());
        res = getClaseBl().actualizar(temp);
        if (res == 0) {
            MensajeView.actCorrecto();
        } else {
            MensajeView.actError();
        }
        listar();
    }
    
    public void eliminar() {
        Clase temp = new Clase();
        temp = buscarId();
        res = claseBl.eliminar(temp);
        if(res == 0){
            MensajeView.eliminacionCorrecta();
        }else{
            MensajeView.eliminacionErronea();
        }
        listar();
    }

    public void limpiar() {
        clase.setIdclase(0);
        clase.setFamilia(new Familia());
        clase.setDescripcion("");
    }

    @PostConstruct
    public void listar() {
        setListClases(claseBl.listarFull(""));
    }
    
    public void listarClasexIdFamilia(){
        
    }
    
    public void listarRef(){
        setListClases(claseBl.listar(getTxtBusqueda()));
    }

    public ClaseBl getClaseBl() {
        return claseBl;
    }

    public void setClaseBl(ClaseBl claseBl) {
        this.claseBl = claseBl;
    }

    public Clase getClase() {
        return clase;
    }

    public void setClase(Clase clase) {
        this.clase = clase;
    }

    public List<Clase> getListClases() {
        return listClases;
    }

    public void setListClases(List<Clase> listClases) {
        this.listClases = listClases;
    }

    private Clase buscarId() {
        return claseBl.buscar(clase.getIdclase());
    }

    public String getTxtBusqueda() {
        return txtBusqueda;
    }

    public void setTxtBusqueda(String txtBusqueda) {
        this.txtBusqueda = txtBusqueda;
    }

    public List<SelectItem> getSelectOneItemsClase() {
        this.selectOneItemsClase= new LinkedList<SelectItem>();
        for (Clase fam : listClases) {
            this.setClase(fam);
            SelectItem selectItem = new SelectItem(clase.getIdclase(), clase.getDescripcion());
            this.selectOneItemsClase.add(selectItem);
        }
        return selectOneItemsClase;
    }

    public void setSelectOneItemsClase(List<SelectItem> selectOneItemsClase) {
        this.selectOneItemsClase = selectOneItemsClase;
    }

}
