
package org.siga.ctrl;

import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import org.siga.be.Familia;
import org.siga.bl.FamiliaBl;
import org.siga.util.MensajeView;

@ManagedBean
@ViewScoped
public class FamiliaBean {
    @ManagedProperty(value = "#{familiaBl}")
    private FamiliaBl familiaBl;
    @ManagedProperty(value = "#{familia}")
    private Familia familia;
    
    private String txtBusqueda;
    private long res;
    private List<Familia> listFamilias;
    private List<SelectItem> selectOneItemsFamilia;
    
    public FamiliaBean() {
    }
    
    @PostConstruct
    public void listar(){
        setListFamilias(familiaBl.listar());
    }
    
    public void registrar(){
        familia.setDescripcion(familia.getDescripcion().toUpperCase());
        familia.setEstado("ACT");
        res = familiaBl.registrar(familia);
        if (res == 0) {
            MensajeView.registroCorrecto();
        } else {
            MensajeView.registroError();
        }
        listar();
    }
    
    public void actualizar(){
        Familia temp = new Familia();
        temp = buscarId();
        temp.setDescripcion(familia.getDescripcion().toUpperCase());
        temp.setEstado(familia.getEstado().toUpperCase());
        res = familiaBl.actualizar(temp);
        if (res == 0) {
            MensajeView.actCorrecto();
        } else {
            MensajeView.actError();
        }
        listar();
    }
    
    public Familia buscarId(){
        return familiaBl.buscar(familia.getIdfamilia());
    }
    
    public void limpiar(){
        familia.setIdfamilia(0);
        familia.setDescripcion("");
        familia.setEstado("");
    }
    
    public void listarRef(){
        setListFamilias(familiaBl.listarRef(txtBusqueda));
    }

    public FamiliaBl getFamiliaBl() {
        return familiaBl;
    }

    public void setFamiliaBl(FamiliaBl familiaBl) {
        this.familiaBl = familiaBl;
    }

    public Familia getFamilia() {
        return familia;
    }

    public void setFamilia(Familia familia) {
        this.familia = familia;
    }

    public String getTxtBusqueda() {
        return txtBusqueda;
    }

    public void setTxtBusqueda(String txtBusqueda) {
        this.txtBusqueda = txtBusqueda;
    }

    public List<Familia> getListFamilias() {
        return listFamilias;
    }

    public void setListFamilias(List<Familia> listFamilias) {
        this.listFamilias = listFamilias;
    }

    public List<SelectItem> getSelectOneItemsFamilia() {
       this.selectOneItemsFamilia= new LinkedList<SelectItem>();
        for (Familia fam : listFamilias) {
            this.setFamilia(fam);
            SelectItem selectItem = new SelectItem(familia.getIdfamilia(), familia.getDescripcion());
            this.selectOneItemsFamilia.add(selectItem);
        }
        return selectOneItemsFamilia;
    }

    public void setSelectOneItemsFamilia(List<SelectItem> selectOneItemsFamilia) {
        this.selectOneItemsFamilia = selectOneItemsFamilia;
    }
    
}
