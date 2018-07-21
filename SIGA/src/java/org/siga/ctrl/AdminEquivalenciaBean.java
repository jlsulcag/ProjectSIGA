
package org.siga.ctrl;

import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import org.siga.be.Equivalencia;
import org.siga.be.UnidadMedida;
import org.siga.bl.EquivalenciaBl;
import org.siga.util.MensajeView;

@ManagedBean
@ViewScoped
public class AdminEquivalenciaBean {

    @ManagedProperty(value = "#{equivalencia}")
    private Equivalencia equivalencia;
    @ManagedProperty(value = "#{equivalenciaBl}")
    private EquivalenciaBl equivalenciaBl;
    
    
    private List<Equivalencia> listEquivalencias;
    private List<SelectItem> selectOneItemsEquivalencia;
    private long res;
    
    public AdminEquivalenciaBean() {
    }
    
    @PostConstruct
    public void listarFull(){
        setListEquivalencias(equivalenciaBl.listarFull());
        limpiar();
    }
    
    public void registrar(){
        long res;
        if(equivalencia.getInterpretacion() != null){
            equivalencia.setInterpretacion(equivalencia.getInterpretacion().toUpperCase());
        }else{
            equivalencia.setInterpretacion("");
        }     
        res = equivalenciaBl.registrar(equivalencia);
        if(res == 0){
            listarXid(equivalencia.getUnidadMedida().getIdunidadmedida());
            MensajeView.registroCorrecto();
            limpiar();
        }else{
            MensajeView.registroError();
        }
        
    }
    
    public void actualizar(){
        Equivalencia temp = new Equivalencia();
        temp = buscarId();
        
        if(temp.getInterpretacion() != null){
            temp.setInterpretacion(temp.getInterpretacion().toUpperCase());
        }else{
            temp.setInterpretacion("");
        }
        System.out.println("factir "+ equivalencia.getFactor());
        temp.setUnidadMedida(equivalencia.getUnidadMedida());
        temp.setUnidadEquivalente(equivalencia.getUnidadEquivalente());
        temp.setFactor(equivalencia.getFactor());
        res = equivalenciaBl.actualizar(temp);
        if (res == 0) {
            //listarXid(temp.getUnidadMedida().getIdunidadmedida());
            MensajeView.actCorrecto();
        } else {
            MensajeView.actError();
        }
    }
    
    private void listarXid(long idunidadmedida) {
        setListEquivalencias(equivalenciaBl.listarXidUnidadMedida(idunidadmedida));
    }

    public Equivalencia getEquivalencia() {
        return equivalencia;
    }
    
    public List<SelectItem> getSelectOneItemsEquivalencia() {
        return selectOneItemsEquivalencia;
    }
    
    public List<SelectItem> listarEquivalencias(long idUnidadMedida) {
        this.selectOneItemsEquivalencia = new LinkedList<SelectItem>();
        for (Equivalencia obj : listarEquivalenciaxUnidadMedida(idUnidadMedida)) {
            this.setEquivalencia(obj);
            SelectItem selectItem = new SelectItem(equivalencia.getIdequivalencia(), getEquivalencia().getUnidadEquivalente().getDescripcion());
            this.selectOneItemsEquivalencia.add(selectItem);
        }
        return selectOneItemsEquivalencia;
    }
    
    private List<Equivalencia> listarEquivalenciaxUnidadMedida(long idunidadmedida) {
        listEquivalencias = new LinkedList<>();
        listEquivalencias = equivalenciaBl.listarEquivalenciaxUnidadMedida(idunidadmedida);
        return listEquivalencias;
    }

    public void setEquivalencia(Equivalencia equivalencia) {
        this.equivalencia = equivalencia;
    }

    public EquivalenciaBl getEquivalenciaBl() {
        return equivalenciaBl;
    }

    public void setEquivalenciaBl(EquivalenciaBl equivalenciaBl) {
        this.equivalenciaBl = equivalenciaBl;
    }

    public List<Equivalencia> getListEquivalencias() {
        return listEquivalencias;
    }

    public void setListEquivalencias(List<Equivalencia> listEquivalencias) {
        this.listEquivalencias = listEquivalencias;
    }

    public void limpiar() {
        equivalencia.setIdequivalencia(0);
        equivalencia.setFactor(0);
        equivalencia.setUnidadMedida(new UnidadMedida());
        equivalencia.setUnidadEquivalente(new UnidadMedida());
        equivalencia.setInterpretacion("");        
    }

    private Equivalencia buscarId() {
        return equivalenciaBl.buscar(equivalencia.getIdequivalencia());
    }

    
    
}
