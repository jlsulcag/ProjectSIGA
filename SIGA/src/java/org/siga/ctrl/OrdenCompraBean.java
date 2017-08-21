
package org.siga.ctrl;

import java.util.List;
import javax.annotation.PostConstruct;
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
import org.siga.util.MensajeView;
import org.siga.util.Utilitario;
import org.siga.util.Utilitarios;

@ManagedBean
@ViewScoped
public class OrdenCompraBean {
    @ManagedProperty(value = "#{ordenCompra}")
    private OrdenCompra ordenCompra;    
    @ManagedProperty(value = "#{ordenCompraBl}")
    private OrdenCompraBl ordenCompraBl;    
    @ManagedProperty(value = "#{ordenCompraDetalle}")
    private OrdenCompraDetalle ordenCompraDetalle;    
    @ManagedProperty(value = "#{ordenCompraDetalleBl}")
    private OrdenCompraDetalleBl ordenCompraDetalleBl; 
    @ManagedProperty(value = "#{proveedor}")
    private Proveedor proveedor;
    @ManagedProperty(value = "#{proveedorBl}")
    private ProveedorBl proveedorBl;
    @ManagedProperty(value = "#{producto}")
    private Producto producto;
    @ManagedProperty(value = "#{productoBl}")
    private ProductoBl productoBl;
    
    private List<OrdenCompra> listOrdenCompra;
    private List<OrdenCompraDetalle> listOrdenCompraDetalles;
    private long res;
    
    
    public OrdenCompraBean() {
    }
    @PostConstruct
    public void listarOrdenCompra(){
        setListOrdenCompra(ordenCompraBl.listarFull(""));
    }
    public void registrar(){
        System.out.println("proveedor "+proveedor.getRazonSocial());
        ordenCompra.setIdProveedor(1);
        ordenCompra.setObservacion(ordenCompra.getObservacion().toUpperCase());
        ordenCompra.setEstado("REG");
        ordenCompra.setHoraRegistro(Utilitarios.horaActual());
        res = ordenCompraBl.registrar(ordenCompra);
        if (res == 0) {
            MensajeView.registroCorrecto();
        } else {
            MensajeView.registroError();
        }
        listarOrdenCompra();
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

    public OrdenCompra getOrdenCompra() {
        return ordenCompra;
    }

    public void setOrdenCompra(OrdenCompra ordenCompra) {
        this.ordenCompra = ordenCompra;
    }

    public OrdenCompraBl getOrdenCompraBl() {
        return ordenCompraBl;
    }

    public void setOrdenCompraBl(OrdenCompraBl ordenCompraBl) {
        this.ordenCompraBl = ordenCompraBl;
    }

    public OrdenCompraDetalle getOrdenCompraDetalle() {
        return ordenCompraDetalle;
    }

    public void setOrdenCompraDetalle(OrdenCompraDetalle ordenCompraDetalle) {
        this.ordenCompraDetalle = ordenCompraDetalle;
    }

    public OrdenCompraDetalleBl getOrdenCompraDetalleBl() {
        return ordenCompraDetalleBl;
    }

    public void setOrdenCompraDetalleBl(OrdenCompraDetalleBl ordenCompraDetalleBl) {
        this.ordenCompraDetalleBl = ordenCompraDetalleBl;
    }

    public List<OrdenCompra> getListOrdenCompra() {
        return listOrdenCompra;
    }

    public void setListOrdenCompra(List<OrdenCompra> listOrdenCompra) {
        this.listOrdenCompra = listOrdenCompra;
    }
    
}
