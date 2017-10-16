
package org.siga.ctrl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;
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
    private OrdenCompraDetalleBl ordenCompraDetalleBl; ;
    @ManagedProperty(value = "#{proveedorBl}")
    private ProveedorBl proveedorBl;
    @ManagedProperty(value = "#{producto}")
    private Producto producto;
    @ManagedProperty(value = "#{productoBl}")
    private ProductoBl productoBl;
    
    private List<SelectItem> selectOneItemsOrdenCompra;
    private List<OrdenCompra> listOrdenCompra;
    private List<OrdenCompraDetalle> listOrdenCompraDetalles = new LinkedList<>();
    private List<Proveedor> listProveedores;
    private long res;
    
    
    public OrdenCompraBean() {
    }
    
    @PostConstruct
    public void listarOrdenCompra(){
        limpiar();
        listOrdenCompra = new ArrayList<>();
        for (OrdenCompra obj : ordenCompraBl.listarFull("")) {
            //obj.setProveedor(proveedorBl.buscar(obj.getIdProveedor()));
            listOrdenCompra.add(obj);
        }
        setListOrdenCompra(listOrdenCompra);
//        setListOrdenCompra(ordenCompraBl.listarFull(""));
    }
    public void registrar(){
        //buscar  proveedor por razon social
        //System.out.println("proveedor "+ordenCompra.getProveedor().getRazonSocial());
        //ordenCompra.setProveedor(proveedorBl.buscarXNombre(proveedor.getRazonSocial()));
        //ordenCompra.setIdProveedor((int) proveedor.getIdproveedor());
        ordenCompra.setObservacion(ordenCompra.getObservacion().toUpperCase());
        ordenCompra.setEstado("APROBADO");
        ordenCompra.setHoraRegistro(Utilitarios.horaActual());
        ordenCompra.setValorBruto(BigDecimal.ZERO);
        ordenCompra.setMontoDesc(BigDecimal.ZERO);
        ordenCompra.setValorNeto(BigDecimal.ZERO);
        ordenCompra.setMontoIgv(BigDecimal.ZERO);
        ordenCompra.setMontoTotal(BigDecimal.ZERO);
        res = ordenCompraBl.registrar(ordenCompra);
        if (res == 0) {
            MensajeView.registroCorrecto();
        } else {
            MensajeView.registroError();
        }
        listarOrdenCompra();
    }
    
    public void actualizar(){
        OrdenCompra temp = new OrdenCompra();
        temp = buscarId();
        temp.setFecha(ordenCompra.getFecha());
        temp.setProveedor(ordenCompra.getProveedor());
        temp.setFechaEntrega(ordenCompra.getFechaEntrega());
        temp.setLugarEntrega(ordenCompra.getLugarEntrega().toUpperCase());
        temp.setObservacion(ordenCompra.getObservacion().toUpperCase());
        res = ordenCompraBl.actualizar(temp);
        if (res == 0) {
            MensajeView.actCorrecto();
        } else {
            MensajeView.actError();
        }
        listarOrdenCompra();
    }
    
    public void limpiar(){
        ordenCompra.setIdordencompra(0);
        ordenCompra.setNumero(maxNumero()+1);
        ordenCompra.setFecha(new Date());
        ordenCompra.setFechaEntrega(null);
        ordenCompra.setLugarEntrega("");
        ordenCompra.setObservacion("");
        invalidarSesionOrdenCompra();
    }
    
    public long maxNumero(){
        return ordenCompraBl.buscarUltimoNumero();
    }
    
    public String redirigir() {
        HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        httpSession.setAttribute("idOrdenCompra", getOrdenCompra().getIdordencompra());
        return "RegistrarOrdenCompraDetalle";
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

    private OrdenCompra buscarId() {
        return ordenCompraBl.buscar(ordenCompra.getIdordencompra());
    }

    public List<SelectItem> getSelectOneItemsOrdenCompra() {
        this.selectOneItemsOrdenCompra= new LinkedList<SelectItem>();
        for (OrdenCompra obj : listOrdenCompraXEstado("APROBADO")) {
            this.setOrdenCompra(obj);
            SelectItem selectItem = new SelectItem(ordenCompra.getIdordencompra(), ordenCompra.getNumero()+"");
            this.selectOneItemsOrdenCompra.add(selectItem);
        }
        return selectOneItemsOrdenCompra;
    }
    
    private List<OrdenCompra> listOrdenCompraXEstado(String estado){
        return ordenCompraBl.listOrdenCompraXEstado(estado);
    }

    public void setSelectOneItemsOrdenCompra(List<SelectItem> selectOneItemsOrdenCompra) {
        this.selectOneItemsOrdenCompra = selectOneItemsOrdenCompra;
    }

    private void invalidarSesionOrdenCompra() {
        HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        httpSession.getAttribute("idOrdenCompra");
        httpSession.invalidate();
    }
    
    public void limpiarNew() {        
        //setListOrdenCompraDetalles(new LinkedList<>());
        producto.setFraccion(0);
        producto.getUnidadMedida().setDescripcion("");
    }

    public List<OrdenCompraDetalle> getListOrdenCompraDetalles() {
        return listOrdenCompraDetalles;
    }

    public void setListOrdenCompraDetalles(List<OrdenCompraDetalle> listOrdenCompraDetalles) {
        this.listOrdenCompraDetalles = listOrdenCompraDetalles;
    }
    
}
