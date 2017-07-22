package org.siga.ctrl;

import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.siga.be.Categoria;
import org.siga.bl.CategoriaBl;
import org.siga.util.MensajeView;

@ManagedBean
@ViewScoped
public class CategoriaBean {

    @ManagedProperty(value = "#{categoriaBl}")
    private CategoriaBl categoriaBl;

    @ManagedProperty(value = "#{categoria}")
    private Categoria categoria;
    private String txtBusqueda;
    private long res;

    private List<Categoria> listCategorias = new LinkedList<>();

    public CategoriaBean() {
    }

    public void registrar() {
        categoria.setDescripcion(categoria.getDescripcion().toUpperCase());
        res = getCategoriaBl().registrar(getCategoria());
        if (res == 0) {
            MensajeView.registroCorrecto();
        } else {
            MensajeView.registroError();
        }
        listar();
    }

    public void actualizar() {
        Categoria temp = new Categoria();
        temp = buscarId();
        temp.setDescripcion(getCategoria().getDescripcion());
        res = getCategoriaBl().actualizar(temp);
        if (res == 0) {
            MensajeView.actCorrecto();
        } else {
            MensajeView.actError();
        }
        listar();
    }
    
    public void eliminar() {
        Categoria temp = new Categoria();
        temp = buscarId();
        res = categoriaBl.eliminar(temp);
        if(res == 0){
            MensajeView.eliminacionCorrecta();
        }else{
            MensajeView.eliminacionErronea();
        }
        listar();
    }

    public void limpiar() {
        categoria.setIdcategoria(0);
        categoria.setDescripcion("");
    }

    @PostConstruct
    public void listar() {
        setListCategorias(categoriaBl.listar());
    }
    
    public void listarRef(){
        setListCategorias(categoriaBl.listar(getTxtBusqueda()));
    }

    public CategoriaBl getCategoriaBl() {
        return categoriaBl;
    }

    public void setCategoriaBl(CategoriaBl categoriaBl) {
        this.categoriaBl = categoriaBl;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public List<Categoria> getListCategorias() {
        return listCategorias;
    }

    public void setListCategorias(List<Categoria> listCategorias) {
        this.listCategorias = listCategorias;
    }

    private Categoria buscarId() {
        return categoriaBl.buscar(categoria.getIdcategoria());
    }

    public String getTxtBusqueda() {
        return txtBusqueda;
    }

    public void setTxtBusqueda(String txtBusqueda) {
        this.txtBusqueda = txtBusqueda;
    }

}
