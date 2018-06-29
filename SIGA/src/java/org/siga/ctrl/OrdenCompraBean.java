package org.siga.ctrl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.siga.be.Almacen;
import org.siga.be.Equivalencia;
import org.siga.be.OrdenCompra;
import org.siga.be.OrdenCompraDetalle;
import org.siga.be.Producto;
import org.siga.be.Proveedor;
import org.siga.be.UnidadMedida;
import org.siga.bl.EquivalenciaBl;
import org.siga.bl.OrdenCompraBl;
import org.siga.bl.OrdenCompraDetalleBl;
import org.siga.bl.ProductoBl;
import org.siga.util.MensajeView;
import org.siga.util.Utilitarios;

@ManagedBean
@ViewScoped
public class OrdenCompraBean {

    @ManagedProperty(value = "#{ordenCompra}")
    private OrdenCompra ordenCompra;
    @ManagedProperty(value = "#{ordenCompraDetalle}")
    private OrdenCompraDetalle ordenCompraDetalle;
    @ManagedProperty(value = "#{ordenCompraDetalleBl}")
    private OrdenCompraDetalleBl ordenCompraDetalleBl;
    @ManagedProperty(value = "#{producto}")
    private Producto producto;
    @ManagedProperty(value = "#{productoBl}")
    private ProductoBl productoBl;
    @ManagedProperty(value = "#{ordenCompraBl}")
    private OrdenCompraBl ordenCompraBl;
    @ManagedProperty(value = "#{equivalencia}")
    private Equivalencia equivalencia;
    @ManagedProperty(value = "#{equivalenciaBl}")
    private EquivalenciaBl equivalenciaBl;

    private List<OrdenCompraDetalle> listOrdenCompraDetalles = new LinkedList<>();
    private long res = -1;
    private long res2 = -1;
    private boolean compraxUnidad;
    private int totalProductos;
    //variables temporales
    private BigDecimal subTotalItem;
    private BigDecimal totalTemp;
    private BigDecimal valorBruto;
    private BigDecimal totalDescuento;
    private BigDecimal valorNeto;
    private BigDecimal montoIgv;

    public OrdenCompraBean() {
    }

    @PostConstruct
    public void inicio() {
        ordenCompra.setIdordencompra(0);
        ordenCompra.setNumero(maxNumero() + 1);
        ordenCompra.setFecha(new Date());
        ordenCompra.setFechaEntrega(null);
        ordenCompra.setLugarEntrega("");
        ordenCompra.setObservacion("");
        ordenCompra.setDocReferencia("");
        ordenCompra.setProveedor(new Proveedor());
        ordenCompra.setAlmacenDestino(new Almacen());
        listOrdenCompraDetalles.clear();
        totalTemp = new BigDecimal("0.00");
        valorBruto = new BigDecimal("0.00");
        totalDescuento = new BigDecimal("0.00");
        valorNeto = new BigDecimal("0.00");
        montoIgv = new BigDecimal("0.00");
        //invalidarSesionOrdenCompra();
    }

    public long maxNumero() {
        return ordenCompraBl.buscarUltimoNumero();
    }

    public void agregar() {
        OrdenCompraDetalle temp = new OrdenCompraDetalle();
        
        temp.setProducto(producto);
        temp.setCantidad(ordenCompraDetalle.getCantidad());
        temp.setObservacion("");
        temp.setLote(ordenCompraDetalle.getLote().toUpperCase());
        temp.setFechaVencimiento(ordenCompraDetalle.getFechaVencimiento());
        temp.setValorCompra(ordenCompraDetalle.getValorCompra());
        temp.setPrecioCompra(ordenCompraDetalle.getPrecioCompra());
        temp.setDesc1(ordenCompraDetalle.getDesc1());
        temp.setDesc2(ordenCompraDetalle.getDesc2());
        temp.setUnidadMedida(producto.getUnidadMedida().getDescripcion());
        temp.setIdUnidadmedida(producto.getUnidadMedida().getIdunidadmedida());

        //realizar los calculos con el valor de compra, para  obtener el sub total por item
        temp.setSubTotal((ordenCompraDetalle.getValorCompra().multiply(new BigDecimal(ordenCompraDetalle.getCantidad()))).setScale(2, RoundingMode.HALF_UP));
        double du;
        du = calcularDescItem(ordenCompraDetalle.getDesc1(), ordenCompraDetalle.getDesc2());
        temp.setMontoDescitem(ordenCompraDetalle.getValorCompra().multiply(new BigDecimal(du).divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP)));

        listOrdenCompraDetalles.add(temp);
        calcularTotal(listOrdenCompraDetalles);
    }

    public void registrar() {
        if (!listOrdenCompraDetalles.isEmpty()) {
            res = registrarOrdenCompra();
            for (OrdenCompraDetalle obj : listOrdenCompraDetalles) {
                obj.setOrdenCompra(ordenCompra);
                res2 = ordenCompraDetalleBl.registrar(obj);
            }
            if (res2 == 0) {
                MensajeView.registroCorrecto();
                inicio();
            } else {
                MensajeView.registroError();
            }
        } else {
            MensajeView.listVacia();
        }

    }

    public long registrarOrdenCompra() {
        ordenCompra.setObservacion(ordenCompra.getObservacion().toUpperCase());
        ordenCompra.setDocReferencia(ordenCompra.getObservacion().toUpperCase());
        ordenCompra.setEstado("APROBADO");
        ordenCompra.setHoraRegistro(Utilitarios.horaActual());
        ordenCompra.setValorBruto(valorBruto);
        ordenCompra.setMontoDesc(totalDescuento);
        ordenCompra.setValorNeto(valorNeto);
        ordenCompra.setMontoIgv(montoIgv);
        ordenCompra.setMontoTotal(totalTemp);

        return ordenCompraBl.registrar(ordenCompra);

    }

    public void inicioNew() {
        ordenCompraDetalle.setIdordencompradetalle(0);
        ordenCompraDetalle.setOrdenCompra(new OrdenCompra());
        ordenCompraDetalle.setProducto(new Producto());
        ordenCompraDetalle.setCantidad(0);
        ordenCompraDetalle.setObservacion("");
        ordenCompraDetalle.setLote("");
        ordenCompraDetalle.setFechaVencimiento(null);
        ordenCompraDetalle.setValorCompra(BigDecimal.ZERO);
        ordenCompraDetalle.setPrecioCompra(BigDecimal.ZERO);
        ordenCompraDetalle.setDesc1(0);
        ordenCompraDetalle.setDesc2(0);
        producto.setUnidadMedida(new UnidadMedida());
    }

    public void listar() {
        HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        if (httpSession.getAttribute("idOrdenCompra") != null) {
            setListOrdenCompraDetalles(ordenCompraDetalleBl.listarXIdOrdenCompra(Long.parseLong(httpSession.getAttribute("idOrdenCompra").toString())));
            calcularTotal(getListOrdenCompraDetalles());
        } else {
            setListOrdenCompraDetalles(ordenCompraDetalleBl.listarFull());
        }
        //calcularTotal(getListOrdenCompraDetalles());
        //setListOrdenCompraDetalles(ordenCompraDetalleBl.listar());
    }

    public void actualizar() {
        OrdenCompraDetalle temp = new OrdenCompraDetalle();
        temp = buscarId();
        temp.setLote(ordenCompraDetalle.getLote().toUpperCase());
        temp.setProducto(ordenCompraDetalle.getProducto());
        temp.setCantidad(ordenCompraDetalle.getCantidad());
        temp.setFechaVencimiento(ordenCompraDetalle.getFechaVencimiento());
        temp.setValorCompra(ordenCompraDetalle.getValorCompra());
        temp.setPrecioCompra(ordenCompraDetalle.getPrecioCompra());
        temp.setDesc1(ordenCompraDetalle.getDesc1());
        temp.setDesc2(ordenCompraDetalle.getDesc2());
        res = ordenCompraDetalleBl.actualizar(temp);
        if (res == 0) {
            MensajeView.actCorrecto();
        } else {
            MensajeView.actError();
        }
        listar();
    }

    public void eliminar() {
        OrdenCompraDetalle temp = new OrdenCompraDetalle();
        temp = buscarId();
        res = ordenCompraDetalleBl.eliminar(temp);
        if (res == 0) {
            MensajeView.eliminacionCorrecta();
        } else {
            MensajeView.eliminacionErronea();
        }
        listar();
    }

    public void buscarProducto() {
        producto = productoBl.buscarxID(ordenCompraDetalle.getProducto().getIdproducto());
        //equivalencia = equivalenciaBl.buscarxIdUnidadMedida(producto.getUnidadMedida().getIdunidadmedida());
    }
    
    /*
    public void setIsCompraUnitaria() {
        setCompraxUnidad(compraxUnidad);
        if (ordenCompraDetalle.getCantidad() > 0) {
            calcularTotalProductos();
        }
    }
    
    
    public void calcularTotalProductos() {
        if (compraxUnidad) {
            setTotalProductos(ordenCompraDetalle.getCantidad());
        } else {
            setTotalProductos(ordenCompraDetalle.getCantidad() * producto.getFraccion());
        }
    }
    */
    public void calcularPrecioCompra() {
        ordenCompraDetalle.setPrecioCompra(ordenCompraDetalle.getValorCompra().add((ordenCompraDetalle.getValorCompra().multiply(MensajeView.IGV))).setScale(2, RoundingMode.HALF_UP));
    }

    public void calcularValorCompra() {
        ordenCompraDetalle.setValorCompra((ordenCompraDetalle.getPrecioCompra().divide(MensajeView.IGV_DIV, 2, RoundingMode.HALF_UP)));
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

    public List<OrdenCompraDetalle> getListOrdenCompraDetalles() {
        return listOrdenCompraDetalles;
    }

    public void setListOrdenCompraDetalles(List<OrdenCompraDetalle> listOrdenCompraDetalles) {
        this.listOrdenCompraDetalles = listOrdenCompraDetalles;
    }

    public OrdenCompraBl getOrdenCompraBl() {
        return ordenCompraBl;
    }

    public void setOrdenCompraBl(OrdenCompraBl ordenCompraBl) {
        this.ordenCompraBl = ordenCompraBl;
    }

    private OrdenCompraDetalle buscarId() {
        return ordenCompraDetalleBl.buscar(ordenCompraDetalle.getIdordencompradetalle());
    }

    public boolean isCompraxUnidad() {
        return compraxUnidad;
    }

    public void setCompraxUnidad(boolean compraxUnidad) {
        this.compraxUnidad = compraxUnidad;
    }

    public int getTotalProductos() {
        return totalProductos;
    }

    public void setTotalProductos(int totalProductos) {
        this.totalProductos = totalProductos;
    }

    public BigDecimal getSubTotalItem() {
        return subTotalItem;
    }

    public void setSubTotalItem(BigDecimal subTotalItem) {
        this.subTotalItem = subTotalItem;
    }

    private double calcularDescItem(double desc1, double desc2) {
        return ((desc1 + desc2) - ((desc1 * desc2) / 100));
    }

    private void listarTemp() {

    }

    public BigDecimal getTotalTemp() {
        return totalTemp;
    }

    public void setTotalTemp(BigDecimal totalTemp) {
        this.totalTemp = totalTemp;
    }

    private void calcularTotal(List<OrdenCompraDetalle> listOrdenCompraDetalles) {
        totalTemp = new BigDecimal(BigInteger.ZERO);
        valorBruto = new BigDecimal(BigInteger.ZERO);
        totalDescuento = new BigDecimal(BigInteger.ZERO);
        valorNeto = new BigDecimal(BigInteger.ZERO);
        montoIgv = new BigDecimal(BigInteger.ZERO);
        HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        for (OrdenCompraDetalle obj : listOrdenCompraDetalles) {
            //Realizar todos los calculos de moneda
            valorBruto = (valorBruto.add(obj.getValorCompra().multiply(new BigDecimal(obj.getCantidad())))).setScale(2, RoundingMode.HALF_UP);
            totalDescuento = (totalDescuento.add(obj.getMontoDescitem())).setScale(2, RoundingMode.HALF_UP);
            valorNeto = (valorBruto.subtract(totalDescuento)).setScale(2, RoundingMode.HALF_UP);
            montoIgv = (valorNeto.multiply(MensajeView.IGV).setScale(2, RoundingMode.HALF_UP)).setScale(2, RoundingMode.HALF_UP);
            if (httpSession.getAttribute("idOrdenCompra") != null) {
                totalTemp = totalTemp.add(obj.getValorCompra().multiply(new BigDecimal(obj.getCantidad())));
            } else {
                //totalTemp = totalTemp.add(obj.getSubTotal());//Antes
                totalTemp = valorNeto.subtract(montoIgv);
            }

        }
    }

    public BigDecimal getValorBruto() {
        return valorBruto;
    }

    public void setValorBruto(BigDecimal valorBruto) {
        this.valorBruto = valorBruto;
    }

    public BigDecimal getTotalDescuento() {
        return totalDescuento;
    }

    public void setTotalDescuento(BigDecimal totalDescuento) {
        this.totalDescuento = totalDescuento;
    }

    public BigDecimal getValorNeto() {
        return valorNeto;
    }

    public void setValorNeto(BigDecimal valorNeto) {
        this.valorNeto = valorNeto;
    }

    public BigDecimal getMontoIgv() {
        return montoIgv;
    }

    public void setMontoIgv(BigDecimal montoIgv) {
        this.montoIgv = montoIgv;
    }

    public OrdenCompra getOrdenCompra() {
        return ordenCompra;
    }

    public void setOrdenCompra(OrdenCompra ordenCompra) {
        this.ordenCompra = ordenCompra;
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
