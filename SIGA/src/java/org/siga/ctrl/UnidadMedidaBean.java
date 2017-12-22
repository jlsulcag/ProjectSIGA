package org.siga.ctrl;

import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import org.primefaces.context.RequestContext;
import org.siga.be.Equivalencia;
import org.siga.be.UnidadMedida;
import org.siga.bl.EquivalenciaBl;
import org.siga.bl.UnidadMedidaBl;
import org.siga.util.MensajeView;

@ManagedBean
@ViewScoped
public class UnidadMedidaBean {

    @ManagedProperty(value = "#{unidadMedidaBl}")
    private UnidadMedidaBl unidadMedidaBl;
    @ManagedProperty(value = "#{unidadMedida}")
    private UnidadMedida unidadMedida;

    @ManagedProperty(value = "#{equivalencia}")
    private Equivalencia equivalencia;
    @ManagedProperty(value = "#{equivalenciaBl}")
    private EquivalenciaBl equivalenciaBl;

    private List<UnidadMedida> listaUnidadMedidas = new LinkedList<>();
    private List<SelectItem> selectOneItemsUnidadMedida;
    private long res;
    private String txtBusqueda;

    public UnidadMedidaBean() {
    }

    public void registrar() {
        unidadMedida.setDescripcion(unidadMedida.getDescripcion().toUpperCase());
        unidadMedida.setEstado("ACT");
        res = getUnidadMedidaBl().registrar(getUnidadMedida());
        if (res == 0) {
            //registrar equivalencia para el producto
            long res2 = registrarEquivalencia();
            if (res2 == 0) {
                MensajeView.registroCorrecto();
            } else {
                MensajeView.registroError();
            }
        } else {
            MensajeView.registroError();
        }
        listar();
    }

    public void actualizar() {
        UnidadMedida temp = new UnidadMedida();
        temp = buscarId();
        temp.setDescripcion(unidadMedida.getDescripcion().toUpperCase());
        temp.setEstado(unidadMedida.getEstado().toUpperCase());
        res = getUnidadMedidaBl().actualizar(temp);
        if (res == 0) {
            MensajeView.actCorrecto();
        } else {
            MensajeView.actError();
        }
        listar();
    }

    public void eliminar() {
        UnidadMedida temp = new UnidadMedida();
        temp = buscarId();
        res = getUnidadMedidaBl().eliminar(temp);
        if (res == 0) {
            MensajeView.eliminacionCorrecta();
        } else {
            MensajeView.eliminacionErronea();
        }
        listar();
    }

    public void limpiar() {
        unidadMedida.setIdunidadmedida(0);
        unidadMedida.setDescripcion("");
        equivalencia.setIdequivalencia(0);
        equivalencia.setFactor(0);
    }

    public void listarRef() {
        setListaUnidadMedidas(unidadMedidaBl.listar(getTxtBusqueda()));
    }

    public void reset() {
        RequestContext.getCurrentInstance().reset("formNew:dlgNew");
    }
    
    
    private long registrarEquivalencia() {
        equivalencia.setUnidadMedida(unidadMedida);
        equivalencia.setUnidadEquivalente(unidadMedida);
        equivalencia.setInterpretacion("");
        equivalencia.setEstado(Boolean.TRUE);
        return equivalenciaBl.registrar(equivalencia);
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
        this.selectOneItemsUnidadMedida = new LinkedList<SelectItem>();
        for (UnidadMedida obj : unidadMedidaBl.listar()) {
            this.setUnidadMedida(obj);
            SelectItem selectItem = new SelectItem(unidadMedida.getIdunidadmedida(), unidadMedida.getDescripcion());
            this.selectOneItemsUnidadMedida.add(selectItem);
        }
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

    public String getTxtBusqueda() {
        return txtBusqueda;
    }

    public void setTxtBusqueda(String txtBusqueda) {
        this.txtBusqueda = txtBusqueda;
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

}
