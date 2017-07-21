
package org.siga.ctrl;

import java.util.LinkedList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.siga.be.Categoria;
import org.siga.bl.CategoriaBl;

@ManagedBean
@ViewScoped
public class CategoriaBean {
    @ManagedProperty(value = "#{categoriaBl}")
    private CategoriaBl categoriaBl;
    
    @ManagedProperty(value = "#{categoria}")
    private Categoria categoria;
    
    private List<Categoria> listCategorias = new LinkedList<>();
    
    public CategoriaBean() {
    }
    
    public void listar(){
        setListCategorias(categoriaBl.listar());
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
    
}
