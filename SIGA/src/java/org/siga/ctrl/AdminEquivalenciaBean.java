
package org.siga.ctrl;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
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
    
    public AdminEquivalenciaBean() {
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
    
    private void listarXid(long idunidadmedida) {
        setListEquivalencias(equivalenciaBl.listarXidUnidadMedida(idunidadmedida));
    }

    public Equivalencia getEquivalencia() {
        return equivalencia;
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

    
    
}
