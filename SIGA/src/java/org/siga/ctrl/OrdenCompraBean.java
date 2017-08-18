
package org.siga.ctrl;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import org.siga.be.OrdenCompra;
import org.siga.be.OrdenCompraDetalle;
import org.siga.be.Producto;
import org.siga.be.Proveedor;
import org.siga.bl.OrdenCompraBl;
import org.siga.bl.OrdenCompraDetalleBl;
import org.siga.bl.ProductoBl;
import org.siga.bl.ProveedorBl;

@ManagedBean
@SessionScoped
public class OrdenCompraBean {
//    @ManagedProperty(value = "#{ordenCompra]")
//    private OrdenCompra ordencompra;    
//    @ManagedProperty(value = "#{ordenCompraBl}")
//    private OrdenCompraBl ordenCompraBl;    
//    @ManagedProperty(value = "#{ordenCompraDetalle}")
//    private OrdenCompraDetalle ordenCompraDetalle;    
//    @ManagedProperty(value = "#{ordenCompraDetalleBl}")
//    private OrdenCompraDetalleBl ordenCompraDetalleBl; 
    @ManagedProperty(value = "#{proveedor}")
    private Proveedor proveedor;
    @ManagedProperty(value = "#{proveedorBl}")
    private ProveedorBl proveedorBl;
    @ManagedProperty(value = "#{producto}")
    private Producto producto;
    @ManagedProperty(value = "#{productoBl}")
    private ProductoBl productoBl;
    
    private List<OrdenCompraDetalle> listOrdenCompraDetalles;
    
    
    public OrdenCompraBean() {
    }
    public List<Proveedor> listProveedoresRef(String ref){
        return getProveedorBl().buscarRef(ref.toUpperCase());
    }
    public List<Producto> listProductosRef(String ref){
        return getProductoBl().listarRef(ref.toUpperCase());
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public ProductoBl getProductoBl() {
        return productoBl;
    }

    public void setProductoBl(ProductoBl productoBl) {
        this.productoBl = productoBl;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public ProveedorBl getProveedorBl() {
        return proveedorBl;
    }

    public void setProveedorBl(ProveedorBl proveedorBl) {
        this.proveedorBl = proveedorBl;
    }
    
}
