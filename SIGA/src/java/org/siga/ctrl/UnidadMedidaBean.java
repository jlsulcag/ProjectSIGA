
package org.siga.ctrl;

import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import org.primefaces.context.RequestContext;
import org.siga.be.UnidadMedida;
import org.siga.bl.UnidadMedidaBl;
import org.siga.util.MensajeView;

@ManagedBean
@ViewScoped
public class UnidadMedidaBean {
    @ManagedProperty(value = "#{unidadMedidaBl}")
    private UnidadMedidaBl unidadMedidaBl;
    @ManagedProperty(value = "#{unidadMedida}")
    private UnidadMedida unidadMedida;

    private List<UnidadMedida> listaUnidadMedidas = new LinkedList<>();
    private List<SelectItem> selectOneItemsUnidadMedida;
    private long res;
    
    public UnidadMedidaBean() {
    }
    
    public void registrar(){
        res = getUnidadMedidaBl().registrar(getUnidadMedida());
        if(res == 0){
            MensajeView.registroCorrecto();
        }else{
            MensajeView.registroError();
        }
        listar();
    }
    
    public void actualizar(){
        UnidadMedida temp = new UnidadMedida();
        temp = buscarId();
        temp.setDescripcion(getUnidadMedida().getDescripcion().toUpperCase());
        res = getUnidadMedidaBl().actualizar(temp);
        if(res == 0){
            MensajeView.actCorrecto();
        }else{
            MensajeView.actError();
        }
        listar();
    }
    
    public void eliminar() {
        UnidadMedida temp = new UnidadMedida();
        temp = buscarId();
        res = getUnidadMedidaBl().eliminar(temp);
        if(res == 0){
            MensajeView.eliminacionCorrecta();
        }else{
            MensajeView.eliminacionErronea();
        }
        listar();
    }
    
    public void limpiar(){
        unidadMedida.setIdunidadmedida(0);
        unidadMedida.setDescripcion("");
        
    }
    
    public void reset(){
        RequestContext.getCurrentInstance().reset("formNew:dlgNew");
    }
    
    @PostConstruct
    private void listar() {
        setListaUnidadMedidas(unidadMedidaBl.listar());
    }

    public List<UnidadMedida> getListaUnidadMedidas() {
        return listaUnidadMedidas;
    }

    public void setListaUnidadMedidas(List<UnidadMedida> listaUnidadMedidas) {
        this.listaUnidadMedidas = listaUnidadMedidas;
    }

    public List<SelectItem> getSelectOneItemsUnidadMedida() {
        return selectOneItemsUnidadMedida;
    }

    public void setSelectOneItemsUnidadMedida(List<SelectItem> selectOneItemsUnidadMedida) {
        this.selectOneItemsUnidadMedida = selectOneItemsUnidadMedida;
    }

    public UnidadMedidaBl getUnidadMedidaBl() {
        return unidadMedidaBl;
    }

    public void setUnidadMedidaBl(UnidadMedidaBl unidadMedidaBl) {
        this.unidadMedidaBl = unidadMedidaBl;
    }

    public UnidadMedida getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(UnidadMedida unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    private UnidadMedida buscarId() {
        return unidadMedidaBl.buscar(getUnidadMedida().getIdunidadmedida());
    }
    
}
