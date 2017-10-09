
package org.siga.ctrl;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.view.ViewScoped;
import org.siga.be.AlmacenProducto;
import org.siga.be.NotaEntrada;
import org.siga.be.Producto;
import org.siga.bl.AlmacenProductoBl;
import org.siga.bl.NotaIngresoBl;

@ManagedBean
@ViewScoped
public class AlmacenProductoBean {
    @ManagedProperty(value = "#{almacenProducto}")
    private AlmacenProducto almacenProducto;
    @ManagedProperty(value = "#{almacenProductoBl}")
    private AlmacenProductoBl almacenProductoBl;
    @ManagedProperty(value = "#{notaEntrada}")
    private NotaEntrada notaEntrada;
    @ManagedProperty(value = "#{notaIngresoBl}")
    private NotaIngresoBl notaIngresoBl;
    @ManagedProperty(value = "#{producto}")
    private Producto producto;
    
    public AlmacenProductoBean() {
    }

    public AlmacenProducto getAlmacenProducto() {
        return almacenProducto;
    }

    public void setAlmacenProducto(AlmacenProducto almacenProducto) {
        this.almacenProducto = almacenProducto;
    }

    public AlmacenProductoBl getAlmacenProductoBl() {
        return almacenProductoBl;
    }

    public void setAlmacenProductoBl(AlmacenProductoBl almacenProductoBl) {
        this.almacenProductoBl = almacenProductoBl;
    }

    public NotaEntrada getNotaEntrada() {
        return notaEntrada;
    }

    public void setNotaEntrada(NotaEntrada notaEntrada) {
        this.notaEntrada = notaEntrada;
    }

    public NotaIngresoBl getNotaIngresoBl() {
        return notaIngresoBl;
    }

    public void setNotaIngresoBl(NotaIngresoBl notaIngresoBl) {
        this.notaIngresoBl = notaIngresoBl;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
    
}
