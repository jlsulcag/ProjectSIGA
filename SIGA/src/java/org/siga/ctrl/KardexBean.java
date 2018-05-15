
package org.siga.ctrl;

import java.util.LinkedList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import org.siga.be.Almacen;
import org.siga.be.Kardex;
import org.siga.be.Producto;
import org.siga.bl.AlmacenBl;
import org.siga.bl.KardexBl;
import org.siga.bl.ProductoBl;

/**
 *
 * @author JSULCAG
 */
@ManagedBean
@ViewScoped
public class KardexBean {

    @ManagedProperty(value = "#{kardexBl}")
    private KardexBl kardexBl;    
    @ManagedProperty(value = "#{kardex}")
    private Kardex kardex;
    
    @ManagedProperty(value = "#{producto}")
    private Producto producto;
    @ManagedProperty(value = "#{productoBl}")
    private ProductoBl productobl;
    
    @ManagedProperty(value = "#{almacen}")
    private Almacen almacen;
    @ManagedProperty(value = "#{almacenBl}")
    private AlmacenBl almacenBl;
    
    private List<SelectItem> selectItemsKardex;
    private List<Kardex> listKardex;
    
    public KardexBean() {
    }
    
    public void generarKardex(){
        System.out.println("id producto : "+producto.getIdproducto());
        System.out.println("id almacen : "+almacen.getIdalmacen());
        setListKardex(kardexBl.generarKardex(producto.getIdproducto(), almacen.getIdalmacen()));
    }

    public KardexBl getKardexBl() {
        return kardexBl;
    }

    public void setKardexBl(KardexBl kardexBl) {
        this.kardexBl = kardexBl;
    }

    public Kardex getKardex() {
        return kardex;
    }

    public void setKardex(Kardex kardex) {
        this.kardex = kardex;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public ProductoBl getProductobl() {
        return productobl;
    }

    public void setProductobl(ProductoBl productobl) {
        this.productobl = productobl;
    }

    public List<SelectItem> getSelectItemsKardex() {
        selectItemsKardex = new LinkedList<>();
        for (Kardex selectItem : listKardex) {
            
        }
        return selectItemsKardex;
    }

    public void setSelectItemsKardex(List<SelectItem> selectItemsKardex) {
        this.selectItemsKardex = selectItemsKardex;
    }

    public List<Kardex> getListKardex() {
        return listKardex;
    }

    public void setListKardex(List<Kardex> listKardex) {
        this.listKardex = listKardex;
    }

    public Almacen getAlmacen() {
        return almacen;
    }

    public void setAlmacen(Almacen almacen) {
        this.almacen = almacen;
    }

    public AlmacenBl getAlmacenBl() {
        return almacenBl;
    }

    public void setAlmacenBl(AlmacenBl almacenBl) {
        this.almacenBl = almacenBl;
    }
    
}
