package org.siga.ctrl;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import org.siga.be.Clase;
import org.siga.be.Producto;
import org.siga.be.UnidadMedida;
import org.siga.bl.ProductoBl;
import org.siga.util.MensajeView;
import org.siga.util.Tarea;
import static org.siga.util.Utilitario.setTareaEvento;

@ManagedBean
@ViewScoped
public class ProductoBean {

    @ManagedProperty(value = "#{productoBl}")
    private ProductoBl productoBl;
    @ManagedProperty(value = "#{producto}")
    private Producto producto;
    private String txtBusqueda;
    private List<Producto> listaProductos;
    private long res;

    //Metodos transaccionales
    @PostConstruct
    public void listar() {
        setListaProductos(productoBl.listarFull(""));
    }

    public void registrar() {
        producto.setDescripcion(producto.getDescripcion().toUpperCase());
        producto.setCodigo(producto.getCodigo().toUpperCase());
        producto.setFechaReg(new Date());
        producto.setEstado("ACT");
        res = productoBl.registrar(producto);
        if (res == 0) {
            MensajeView.registroCorrecto();
        } else {
            MensajeView.registroError();
        }
        producto = new Producto();
        listar();
        
    }
    
    public void actualizar(){
        Producto temp = new Producto();
        temp = buscarId();
        temp.setDescripcion(producto.getDescripcion().toUpperCase());
        temp.setCodigo(producto.getCodigo().toUpperCase());
        temp.setFechaReg(new Date());
        temp.setEstado(producto.getEstado());
        res = productoBl.actualizar(temp);
        if (res == 0) {
            MensajeView.actCorrecto();
        } else {
            MensajeView.actError();
        }
        listar();
    }
    
    public void limpiar(){
        producto.setIdproducto(0);
        producto.setDescripcion("");
        producto.setCodigo("");
        producto.setStockMinimo(0);
        producto.setStockMaximo(0);
        producto.setUnidadMedida(new UnidadMedida());
        producto.setEstado("");
        producto.setFechaReg(null);
        producto.setClase(new Clase());
    }
    
    public void buscarRef(){
        setListaProductos(productoBl.listarRef(getTxtBusqueda().toUpperCase()));
    }
    //fin Metodos

    public ProductoBean() {
    }

    public ProductoBl getProductoBl() {
        return productoBl;
    }

    public void setProductoBl(ProductoBl productoBl) {
        this.productoBl = productoBl;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public String getTxtBusqueda() {
        return txtBusqueda;
    }

    public void setTxtBusqueda(String txtBusqueda) {
        this.txtBusqueda = txtBusqueda;
    }

    public List<Producto> getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(List<Producto> listaProductos) {
        this.listaProductos = listaProductos;
    }

    private Producto buscarId() {
        return productoBl.buscar(producto.getIdproducto());
    }

}
