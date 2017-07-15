
package org.siga.ctrl;

import java.util.LinkedList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.siga.be.Producto;
import org.siga.bl.ProductoBl;

@ManagedBean
@ViewScoped
public class ProductoBean {
    @ManagedProperty(value = "#{productoBl}")
    private ProductoBl productoBl;
    @ManagedProperty(value = "#{producto}")
    private Producto producto;

    private List<Producto> listaProductos = new LinkedList<>();

    public ProductoBean() {
    }
    
    
}
