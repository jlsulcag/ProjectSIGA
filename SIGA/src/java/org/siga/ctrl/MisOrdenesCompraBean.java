package org.siga.ctrl;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import org.primefaces.event.SelectEvent;
import org.siga.be.Equivalencia;
import org.siga.be.OrdenCompra;
import org.siga.be.OrdenCompraDetalle;
import org.siga.be.OrdenCompraSeguimiento;
import org.siga.be.Producto;
import org.siga.be.Proveedor;
import org.siga.bl.EquivalenciaBl;
import org.siga.bl.OrdenCompraBl;
import org.siga.bl.OrdenCompraDetalleBl;
import org.siga.bl.OrdenCompraEstadosBl;
import org.siga.bl.OrdenCompraSeguimientoBl;
import org.siga.bl.ProductoBl;
import org.siga.bl.ProveedorBl;
import org.siga.ds.DSConeccion;
import org.siga.util.MensajeView;
import org.siga.util.Utilitarios;
import org.siga.util.Variables;

@ManagedBean
@ViewScoped
public class MisOrdenesCompraBean {

    @ManagedProperty(value = "#{ordenCompra}")
    private OrdenCompra ordenCompra;
    @ManagedProperty(value = "#{ordenCompraBl}")
    private OrdenCompraBl ordenCompraBl;
    @ManagedProperty(value = "#{ordenCompraDetalle}")
    private OrdenCompraDetalle ordenCompraDetalle;
    @ManagedProperty(value = "#{ordenCompraDetalleBl}")
    private OrdenCompraDetalleBl ordenCompraDetalleBl;
    
    @ManagedProperty(value = "#{proveedorBl}")
    private ProveedorBl proveedorBl;
    @ManagedProperty(value = "#{producto}")
    private Producto producto;
    @ManagedProperty(value = "#{productoBl}")
    private ProductoBl productoBl;
    @ManagedProperty(value = "#{equivalencia}")
    private Equivalencia equivalencia;
    @ManagedProperty(value = "#{equivalenciaBl}")
    private EquivalenciaBl equivalenciaBl;

    @ManagedProperty(value = "#{ordenCompraSeguimientoBl}")
    private OrdenCompraSeguimientoBl ordenCompraSeguimientoBl;
    @ManagedProperty(value = "#{ordenCompraSeguimiento}")
    private OrdenCompraSeguimiento ordenCompraSeguimiento;

    @ManagedProperty(value = "#{ordenCompraEstadosBl}")
    private OrdenCompraEstadosBl ordenCompraEstadosBl;

    private List<SelectItem> selectOneItemsOrdenCompra;
    private List<OrdenCompra> listOrdenCompra;
    private List<OrdenCompraDetalle> listOrdenCompraDetalles = new LinkedList<>();
    private List<OrdenCompraSeguimiento> listOrdenCompraSeguimiento;
    private List<OrdenCompraDetalle> listOrdenCompraDetalle;
    private OrdenCompra selectedOrdenCompra;
    private long res;
    private double totalProductos;
    //variables temporales
    private BigDecimal subTotalItem;
    private BigDecimal totalTemp;
    private BigDecimal valorBruto;
    private BigDecimal totalDescuento;
    private BigDecimal valorNeto;
    private BigDecimal montoIgv;

    public MisOrdenesCompraBean() {
    }

    @PostConstruct
    public void listarOrdenCompra() {
        inicio();
        listOrdenCompra = new ArrayList<>();
        for (OrdenCompra obj : ordenCompraBl.listarFull("")) {
            //Para cada orden compra retornar su ultimo estado del historial
            ordenCompraSeguimiento = ordenCompraSeguimientoBl.buscarxidCompra(obj.getIdordencompra());
            if (ordenCompraSeguimiento != null) {
                obj.setEstado(ordenCompraSeguimiento.getOrdenCompraEstados().getDescripcion());
            }
            listOrdenCompra.add(obj);
        }
        setListOrdenCompra(listOrdenCompra);
    }

    public void buscarProducto() {
        producto = productoBl.buscarxID(ordenCompraDetalle.getProducto().getIdproducto());

        //System.out.println("Fraccion ---- "+producto.getFraccion());
    }

    public void calcularTotalProductos() {
        //Obtener su fraccion para determinar la cantidad total
        if (producto != null) {
            equivalencia = equivalenciaBl.buscarxIdUnidadMedida(producto.getIdproducto(), producto.getIdproducto());
        }

    }

    public void calcularValorCompra() {
        ordenCompraDetalle.setValorCompra((ordenCompraDetalle.getPrecioCompra().divide(MensajeView.IGV_DIV, 2, RoundingMode.HALF_UP)));
    }

    public void calcularPrecioCompra() {
        ordenCompraDetalle.setPrecioCompra(ordenCompraDetalle.getValorCompra().add((ordenCompraDetalle.getValorCompra().multiply(MensajeView.IGV))).setScale(2, RoundingMode.HALF_UP));
    }

    public void agregar() {
        OrdenCompraDetalle temp = new OrdenCompraDetalle();
//        temp = this.ordenCompraDetalle;
        HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        if (httpSession.getAttribute("idOrdenCompra") != null) {
            temp.setOrdenCompra(ordenCompraBl.buscar(Long.parseLong(httpSession.getAttribute("idOrdenCompra").toString())));
        }
        temp.setProducto(producto);
        temp.setCantidad(ordenCompraDetalle.getCantidad());
        temp.setObservacion("");
        temp.setLote(ordenCompraDetalle.getLote().toUpperCase());
        temp.setFechaVencimiento(ordenCompraDetalle.getFechaVencimiento());
        temp.setValorCompra(ordenCompraDetalle.getValorCompra());
        temp.setPrecioCompra(ordenCompraDetalle.getPrecioCompra());
        temp.setDesc1(ordenCompraDetalle.getDesc1());
        temp.setDesc2(ordenCompraDetalle.getDesc2());

        //realizar los calculos con el valor de compra, para  obtener el sub total por item
        temp.setSubTotal(ordenCompraDetalle.getValorCompra().multiply(new BigDecimal(ordenCompraDetalle.getCantidad())));
        double du;
        du = calcularDescItem(ordenCompraDetalle.getDesc1(), ordenCompraDetalle.getDesc2());
        temp.setMontoDescitem(ordenCompraDetalle.getValorCompra().multiply(new BigDecimal(du).divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP)));

        listOrdenCompraDetalles.add(temp);
        calcularTotal(listOrdenCompraDetalles);
    }

    private double calcularDescItem(double desc1, double desc2) {
        return ((desc1 + desc2) - ((desc1 * desc2) / 100));
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
            valorBruto = valorBruto.add(obj.getValorCompra().multiply(new BigDecimal(obj.getCantidad())));
            totalDescuento = totalDescuento.add(obj.getMontoDescitem());
            valorNeto = valorBruto.subtract(totalDescuento);
            montoIgv = valorNeto.multiply(MensajeView.IGV).setScale(2, RoundingMode.HALF_UP);
            if (httpSession.getAttribute("idOrdenCompra") != null) {
                totalTemp = totalTemp.add(obj.getValorCompra().multiply(new BigDecimal(obj.getCantidad())));
            } else {
                //totalTemp = totalTemp.add(obj.getSubTotal());//Antes
                totalTemp = valorNeto.subtract(montoIgv);
            }

        }
    }

    public void registrar() {
        //buscar  proveedor por razon social
        //System.out.println("proveedor "+ordenCompra.getProveedor().getRazonSocial());
        //ordenCompra.setProveedor(proveedorBl.buscarXNombre(proveedor.getRazonSocial()));
        //ordenCompra.setIdProveedor((int) proveedor.getIdproveedor());
        ordenCompra.setObservacion(ordenCompra.getObservacion().toUpperCase());
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

    public void actualizar() {
        OrdenCompra temp = new OrdenCompra();
        temp = buscarId();
        temp.setFecha(ordenCompra.getFecha());
        temp.setProveedor(ordenCompra.getProveedor());
        temp.setAlmacenSolicitante(ordenCompra.getAlmacenSolicitante());
        temp.setFormaPago(ordenCompra.getFormaPago().toUpperCase());
        temp.setFechaEntrega(ordenCompra.getFechaEntrega());
        temp.setDocReferencia(ordenCompra.getDocReferencia().toUpperCase());
        temp.setObservacion(ordenCompra.getObservacion().toUpperCase());
        res = ordenCompraBl.actualizar(temp);
        if (res == 0) {
            MensajeView.actCorrecto();
        } else {
            MensajeView.actError();
        }
        listarOrdenCompra();
    }

    public void inicio() {
        ordenCompra.setIdordencompra(0);
        ordenCompra.setNumero(maxNumero() + 1);
        ordenCompra.setFecha(new Date());
        ordenCompra.setFechaEntrega(null);
        ordenCompra.setPenalidadIncumplimiento("");
        ordenCompra.setObservacion("");
        //invalidarSesionOrdenCompra();
    }

    public long maxNumero() {
        return ordenCompraBl.buscarUltimoNumero();
    }

    public String redirigir() {
        HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        httpSession.setAttribute("idOrdenCompra", getOrdenCompra().getIdordencompra());
        return "ViewOrdenCompraDetalle?faces-redirect=true";
    }

    public void autorizarOrdenCompra() {
        if (getOrdenCompra() != null) {
            ordenCompraSeguimiento = ordenCompraSeguimientoBl.buscarxidCompra(getOrdenCompra().getIdordencompra());
            if (!ordenCompraSeguimiento.getOrdenCompraEstados().getDescripcion().trim().equals("APROBADO")) {
                ordenCompraSeguimiento.setOrdenCompra(getOrdenCompra());
                ordenCompraSeguimiento.setOrdenCompraEstados(getOrdenCompraEstadosBl().buscar(2));
                ordenCompraSeguimiento.setFecha(new Date());
                ordenCompraSeguimiento.setHora(Utilitarios.horaActual());
                ordenCompraSeguimiento.setNumero(ordenCompraSeguimientoBl.maxNumero(ordenCompra.getIdordencompra()) + 1);
                HttpSession sesionUser = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
                if (sesionUser.getAttribute("idUsuario") != null) {
                    ordenCompraSeguimiento.setIdUser(Long.parseLong(sesionUser.getAttribute("idUsuario").toString()));
                } else {
                    ordenCompraSeguimiento.setIdUser(0);
                }
                ordenCompraSeguimiento.setObservacion("");

                res = ordenCompraSeguimientoBl.registrar(ordenCompraSeguimiento);
                if (res == 0) {
                    MensajeView.autorizarOrdenCompra();
                    listarOrdenCompra();
                }
            } else {
                MensajeView.seEncuentraAutorizada();
            }

        }
    }

    public void descargarOrdenCompra() {
        try {
            if (ordenCompra != null) {
                ordenCompraSeguimiento = ordenCompraSeguimientoBl.buscarxidCompra(ordenCompra.getIdordencompra());
                if (ordenCompraSeguimiento.getOrdenCompraEstados().getDescripcion().trim().equals("APROBADO")) {
                    //Mapa de parametros
                    Map<String, Object> parametro = new HashMap<>();
                    //
                    File file = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/WEB-INF/classes/org/siga/reportes/REP-0004-orden_compra.jasper"));
                    //DSConeccion ds = new DSConeccion("192.168.32.33", "5432", "sigadb_desa", "siga%admin", "siga%admin");
                    DSConeccion ds = new DSConeccion(Variables.HOST, Variables.PORT, Variables.DB, Variables.USER, Variables.PASS);
                    //DsConexion ds = new DsConexion();
                    parametro.put("ID_ORDEN_COMPRA", ordenCompra.getIdordencompra());
                    JasperReport reporte = (JasperReport) JRLoader.loadObjectFromFile(file.getPath());
                    JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parametro, ds.getConeccion());

                    HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
                    response.addHeader("Content-disposition", "attachment; filename=Orden_Compra_"+Utilitarios.numberFormat(ordenCompra.getNumero(), "########")+".pdf");
                    ServletOutputStream stream = response.getOutputStream();

                    JasperExportManager.exportReportToPdfStream(jasperPrint, stream);

                    stream.flush();
                    stream.close();
                    FacesContext.getCurrentInstance().responseComplete();
                } else {
                    MensajeView.noImprimeOrdenCompra();
                }

            } else {

            }

        } catch (JRException ex) {
            Logger.getLogger(OrdenCompraBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(OrdenCompraBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String redirigirOrdenCompraDetalles() {
        HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        httpSession.setAttribute("idOrdenCompra", getOrdenCompra().getIdordencompra());
        return "RegistrarOrdenCompraDetalle?faces-redirect=true";
    }

    public List<Proveedor> listProveedoresRef(String ref) {
        return getProveedorBl().buscarRef(ref.toUpperCase());
    }

    public List<Producto> listProductosRef(String ref) {
        return getProductoBl().listarRef(ref.toUpperCase());
    }

    public void onRowSelect(SelectEvent event) {
        /*
         FacesMessage msg = new FacesMessage("Numero Compra", ((OrdenCompra) event.getObject()).getNumero() + "");
         FacesContext.getCurrentInstance().addMessage(null, msg);
         */
        ordenCompra = (OrdenCompra) event.getObject();
        setListOrdenCompraDetalle(ordenCompraDetalleBl.listarXIdOrdenCompra(((OrdenCompra) event.getObject()).getIdordencompra()));
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
        listOrdenCompraSeguimiento = new LinkedList<>();
        listOrdenCompraSeguimiento = ordenCompraSeguimientoBl.listarxEstado(2, 4);
        selectOneItemsOrdenCompra = new LinkedList<>();
        for (OrdenCompraSeguimiento obj : listOrdenCompraSeguimiento) {
            ordenCompra = ordenCompraBl.buscarXId(obj.getOrdenCompra().getIdordencompra());
            SelectItem selectItem = new SelectItem(ordenCompra.getIdordencompra(), ordenCompra.getNumero() + "");
            this.selectOneItemsOrdenCompra.add(selectItem);
        }
        return selectOneItemsOrdenCompra;
    }

    private List<OrdenCompra> listOrdenCompraXEstado(String estado) {
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
        ordenCompraDetalle.setIdordencompradetalle(0);
        //ordenCompraDetalle.setOrdenCompra(new OrdenCompra());
        //ordenCompraDetalle.setProducto(new Producto());
        ordenCompraDetalle.setCantidad(0);
        ordenCompraDetalle.setObservacion("");
        ordenCompraDetalle.setLote("");
        ordenCompraDetalle.setFechaVencimiento(null);
        ordenCompraDetalle.setValorCompra(BigDecimal.ZERO);
        ordenCompraDetalle.setPrecioCompra(BigDecimal.ZERO);
        ordenCompraDetalle.setDesc1(0);
        ordenCompraDetalle.setDesc2(0);
//        producto.setFraccion(0);
//        producto.getUnidadMedida().setDescripcion("");
    }

    public List<OrdenCompraDetalle> getListOrdenCompraDetalles() {
        return listOrdenCompraDetalles;
    }

    public void setListOrdenCompraDetalles(List<OrdenCompraDetalle> listOrdenCompraDetalles) {
        this.listOrdenCompraDetalles = listOrdenCompraDetalles;
    }

    public double getTotalProductos() {
        return totalProductos;
    }

    public void setTotalProductos(double totalProductos) {
        this.totalProductos = totalProductos;
    }

    public BigDecimal getSubTotalItem() {
        return subTotalItem;
    }

    public void setSubTotalItem(BigDecimal subTotalItem) {
        this.subTotalItem = subTotalItem;
    }

    public BigDecimal getTotalTemp() {
        return totalTemp;
    }

    public void setTotalTemp(BigDecimal totalTemp) {
        this.totalTemp = totalTemp;
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

    public List<OrdenCompraSeguimiento> getListOrdenCompraSeguimiento() {
        return listOrdenCompraSeguimiento;
    }

    public void setListOrdenCompraSeguimiento(List<OrdenCompraSeguimiento> listOrdenCompraSeguimiento) {
        this.listOrdenCompraSeguimiento = listOrdenCompraSeguimiento;
    }

    public OrdenCompraSeguimientoBl getOrdenCompraSeguimientoBl() {
        return ordenCompraSeguimientoBl;
    }

    public void setOrdenCompraSeguimientoBl(OrdenCompraSeguimientoBl ordenCompraSeguimientoBl) {
        this.ordenCompraSeguimientoBl = ordenCompraSeguimientoBl;
    }

    public List<OrdenCompraDetalle> getListOrdenCompraDetalle() {
        return listOrdenCompraDetalle;
    }

    public void setListOrdenCompraDetalle(List<OrdenCompraDetalle> listOrdenCompraDetalle) {
        this.listOrdenCompraDetalle = listOrdenCompraDetalle;
    }

    public OrdenCompra getSelectedOrdenCompra() {
        return selectedOrdenCompra;
    }

    public void setSelectedOrdenCompra(OrdenCompra selectedOrdenCompra) {
        this.selectedOrdenCompra = selectedOrdenCompra;
    }

    public OrdenCompraSeguimiento getOrdenCompraSeguimiento() {
        return ordenCompraSeguimiento;
    }

    public void setOrdenCompraSeguimiento(OrdenCompraSeguimiento ordenCompraSeguimiento) {
        this.ordenCompraSeguimiento = ordenCompraSeguimiento;
    }

    public OrdenCompraEstadosBl getOrdenCompraEstadosBl() {
        return ordenCompraEstadosBl;
    }

    public void setOrdenCompraEstadosBl(OrdenCompraEstadosBl ordenCompraEstadosBl) {
        this.ordenCompraEstadosBl = ordenCompraEstadosBl;
    }

}
