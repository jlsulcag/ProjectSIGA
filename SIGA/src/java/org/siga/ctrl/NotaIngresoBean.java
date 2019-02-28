package org.siga.ctrl;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
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
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;
import org.primefaces.event.RowEditEvent;
import org.siga.be.Almacen;
import org.siga.be.AlmacenProducto;
import org.siga.be.Equivalencia;
import org.siga.be.Kardex;
import org.siga.be.NotaEntrada;
import org.siga.be.NotaEntradaDetalle;
import org.siga.be.OrdenCompra;
import org.siga.be.OrdenCompraDetalle;
import org.siga.be.OrdenCompraSeguimiento;
import org.siga.be.Producto;
import org.siga.be.Proveedor;
import org.siga.be.UnidadMedida;
import org.siga.be.Usuario;
import org.siga.bl.AlmacenProductoBl;
import org.siga.bl.EquivalenciaBl;
import org.siga.bl.KardexBl;
import org.siga.bl.NotaIngresoBl;
import org.siga.bl.NotaIngresoDetalleBl;
import org.siga.bl.OrdenCompraBl;
import org.siga.bl.OrdenCompraDetalleBl;
import org.siga.bl.OrdenCompraEstadosBl;
import org.siga.bl.OrdenCompraSeguimientoBl;
import org.siga.bl.ProductoBl;
import org.siga.bl.UnidadMedidaBl;
import org.siga.ds.DSConeccion;
import org.siga.util.MensajeView;
import org.siga.util.Utilitarios;

@ManagedBean
@ViewScoped
public class NotaIngresoBean {

    @ManagedProperty(value = "#{notaEntrada}")
    private NotaEntrada notaEntrada;
    @ManagedProperty(value = "#{notaIngresoBl}")
    private NotaIngresoBl notaIngresoBl;
    @ManagedProperty(value = "#{notaEntradaDetalle}")
    private NotaEntradaDetalle notaEntradaDetalle;
    @ManagedProperty(value = "#{notaIngresoDetalleBl}")
    private NotaIngresoDetalleBl notaIngresoDetalleBl;
    @ManagedProperty(value = "#{ordenCompraDetalleBl}")
    private OrdenCompraDetalleBl ordenCompraDetalleBl;
    @ManagedProperty(value = "#{ordenCompra}")
    private OrdenCompra ordenCompra;
    @ManagedProperty(value = "#{ordenCompraBl}")
    private OrdenCompraBl ordenCompraBl;
    @ManagedProperty(value = "#{producto}")
    private Producto producto;
    @ManagedProperty(value = "#{productoBl}")
    private ProductoBl productoBl;
    @ManagedProperty(value = "#{almacenProducto}")
    private AlmacenProducto almacenProducto;
    @ManagedProperty(value = "#{almacenProductoBl}")
    private AlmacenProductoBl almacenProductoBl;

    @ManagedProperty(value = "#{equivalencia}")
    private Equivalencia equivalencia;
    @ManagedProperty(value = "#{equivalenciaBl}")
    private EquivalenciaBl equivalenciaBl;

    @ManagedProperty(value = "#{kardex}")
    private Kardex kardex;
    @ManagedProperty(value = "#{kardexBl}")
    private KardexBl kardexBl;

    @ManagedProperty(value = "#{unidadMedida}")
    private UnidadMedida unidadMedida;
    @ManagedProperty(value = "#{unidadMedidaBl}")
    private UnidadMedidaBl unidadMedidaBl;

    @ManagedProperty(value = "#{ordenCompraSeguimientoBl}")
    private OrdenCompraSeguimientoBl ordenCompraSeguimientoBl;
    @ManagedProperty(value = "#{ordenCompraSeguimiento}")
    private OrdenCompraSeguimiento ordenCompraSeguimiento;

    @ManagedProperty(value = "#{ordenCompraEstadosBl}")
    private OrdenCompraEstadosBl ordenCompraEstadosBl;

    private List<Equivalencia> listEquivalencia;
    private List<SelectItem> selectOneItemsEquivalencia;

    private NotaEntradaDetalle notaEntradaDetalleTemp;

    //private List<NotaEntrada> listNotaEntrada;
    private List<NotaEntradaDetalle> listNotaEntradaDetalle = new LinkedList<>();
    private List<NotaEntradaDetalle> listNotaEntradaDetalleTemp;
    ;
    private List<OrdenCompraDetalle> listOrdenCompraDetalle;
    private int totalProductos;

    private long res;

    public NotaIngresoBean() {
    }

    public void registrar() {
        //validar  que el los productos esten cargados en la tabla de  detalle
        int r = -1;
        int cont = 0;
        NotaEntrada temp = new NotaEntrada();
        if (!listNotaEntradaDetalle.isEmpty()) {
            res = registrarNotaEntrada();
            //Registrar Nota Entrada Detalle
            if (res == 0) {
                if (notaEntrada.getOrdenCompra() != null) {
                    temp = notaIngresoBl.buscarxIdCompra(notaEntrada.getOrdenCompra().getIdordencompra());
                    if (temp != null) {
                        HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
                        httpSession.setAttribute("notaEntrada", temp);
                    }
                }

                for (NotaEntradaDetalle obj : listNotaEntradaDetalle) {
                    cont++;
                    obj.setNotaEntrada(notaEntrada);
                    notaIngresoDetalleBl.registrar(obj);
                    //Registrar o actualizar almacen stock
                    long resp = registrarStockAlmacen(obj);
                    if (cont == listNotaEntradaDetalle.size()) {
                        if (resp != 0) {
                            MensajeView.registroCorrecto();
                        } else {
                            MensajeView.registroError();
                        }
                    }

                    //Registrar Kardex
                    long kx = registrarKardex(obj, notaEntrada);
                }
                //Actualizar el estado de  la orden de compra, solo si es que el ingreso proviene de una orden de compra
                if (notaEntrada.getOrdenCompra() != null && notaEntrada.getOrdenCompra().getIdordencompra() != 0) {
                    long res = -1;
                    res = registrarOrdenCompraSeguimiento();
                }
            }
            iniciar();
        } else {
            MensajeView.listVacia();
        }
    }

    @PostConstruct
    public void iniciar() {
        //remover:
        //FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("NotaIngresoBean");
        //FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("NotaIngresoBean", null);
        notaEntrada.setIdnotaentrada(0);
        notaEntrada.setOrdenCompra(new OrdenCompra());
        notaEntrada.setNumero(maxNumero() + 1);
        notaEntrada.setFechaReg(new Date());
        notaEntrada.setFechaDocref(null);
        notaEntrada.setNroDocref("");
        notaEntrada.setObservacion("");
        notaEntrada.setTipoIngreso("");
        notaEntrada.setProveedor(new Proveedor());
        notaEntrada.setAlmacenDestino(new Almacen());
        listNotaEntradaDetalle.clear();
        this.producto = null;
    }

    public void limpiarNew() {
        notaEntradaDetalle.setIdnotaentradadetalle(0);
        notaEntradaDetalle.setProducto(new Producto());
        notaEntradaDetalle.setUnidadMedida("");
        notaEntradaDetalle.setValorCompra(BigDecimal.ZERO);
        notaEntradaDetalle.setCantIngreso(BigDecimal.ZERO);
        setTotalProductos(0);
        notaEntradaDetalle.setFechaVencimiento(null);
        notaEntradaDetalle.setLote("");
        if (this.producto != null) {
            producto.getUnidadMedida().setDescripcion("");
            this.producto = null;
        }

    }

    private long maxNumero() {
        return getNotaIngresoBl().buscarUltimoNumero();
    }

    public void listarDetalleOrdenCompra() {
        listNotaEntradaDetalle.clear();
        //Obtener la lista de DetalleOrdenCompra
        long id = notaEntrada.getOrdenCompra().getIdordencompra();
        listOrdenCompraDetalle = ordenCompraDetalleBl.listarXIdOrdenCompra(id);
        //listNotaEnlistOrdenCompraDetalletradaDetalle = new ArrayList<>();
        for (OrdenCompraDetalle obj : listOrdenCompraDetalle) {
            //notaEntradaDetalle.setNotaEntrada(notaEntrada);
            NotaEntradaDetalle notaED = new NotaEntradaDetalle();
            notaED.setProducto(obj.getProducto());
            notaED.setLote(obj.getLote());
            notaED.setFechaVencimiento(obj.getFechaVencimiento());
            notaED.setValorCompra(obj.getValorCompra());
            notaED.setPrecioCompra(obj.getPrecioCompra());
            notaED.setDesc1(obj.getDesc1());
            notaED.setDesc2(obj.getDesc2());
            notaED.setUnidadMedida(obj.getUnidadMedida());
            notaED.setMontoDescitem(obj.getMontoDescitem());
            notaED.setIdEquivalencia(obj.getIdEquivalencia());

            notaED.setCantSolicitada(obj.getCantidad());
            //buscar la suma de la cantidad ingresada hasta ese momento.. buscar por orden de compra y producto
            notaED.setCantRecibida(notaIngresoDetalleBl.getCantIngresada(obj.getProducto().getIdproducto(), id));
            notaED.setCantPendiente(notaED.getCantSolicitada().subtract(notaED.getCantRecibida()));
            notaED.setCantIngreso(notaED.getCantPendiente());
            //validar que solo se agreguen los productos que faltan recepcionar
            //if (notaED.getCantRecibida() < notaED.getCantSolicitada()) {
            //
            //buscar la equivalencia  utiliza para la orden de compra 
            equivalencia = equivalenciaBl.buscaxId(obj.getIdEquivalencia());
            if (equivalencia != null) {
                notaED.setTotalProductos((notaED.getCantIngreso().multiply(new BigDecimal(equivalencia.getFactor()))));
            }

            //buscar stock disponible en el inventario
            AlmacenProducto temp = new AlmacenProducto();
            temp = almacenProductoBl.buscarProductoxAlmacenyLote(notaED.getLote(), notaED.getNotaEntrada().getOrdenCompra().getAlmacenSolicitante().getIdalmacen(), notaED.getProducto());
            if (temp == null) {
                notaED.setCantDisponible(BigDecimal.ZERO);
            } else {
                notaED.setCantDisponible(temp.getStockActual());
            }
            listNotaEntradaDetalle.add(notaED);
            //}
        }
    }

    public void onRowEdit(RowEditEvent event) {
        listNotaEntradaDetalleTemp = new LinkedList<>();
        notaEntradaDetalleTemp = new NotaEntradaDetalle();
        String msg = "";
        notaEntradaDetalleTemp.setProducto(((NotaEntradaDetalle) event.getObject()).getProducto());
        notaEntradaDetalleTemp.setCantIngreso(((NotaEntradaDetalle) event.getObject()).getCantIngreso());

        for (int i = 0; i < listNotaEntradaDetalle.size(); i++) {
            NotaEntradaDetalle ned = new NotaEntradaDetalle();
            ned = listNotaEntradaDetalle.get(i);
            if (listNotaEntradaDetalle.get(i).getProducto() == notaEntradaDetalleTemp.getProducto()) {
                if ((notaEntradaDetalleTemp.getCantIngreso().compareTo(ned.getCantPendiente()) == 1)) {
                    //si el primero es  mayor que el segundo
                    //la cantidad ingresa se debe mantener
                    ned.setCantIngreso(ned.getCantPendiente());
                    msg = "La cantidad ingresada supera a la cantidad pendiente";
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atención", msg);
                    FacesContext.getCurrentInstance().addMessage(null, message);
                } else {
                    ned.setCantIngreso(notaEntradaDetalleTemp.getCantIngreso());
                }
                equivalencia = equivalenciaBl.buscaxId(ned.getIdEquivalencia());
                ned.setTotalProductos((ned.getCantIngreso().multiply(new BigDecimal(equivalencia.getFactor()))));
            }
            listNotaEntradaDetalleTemp.add(ned);
        }
        setListNotaEntradaDetalle(listNotaEntradaDetalleTemp);
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edicion cancelada", null);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void agregarProducto() {
        NotaEntradaDetalle ned = new NotaEntradaDetalle();
        ned.setProducto(producto);
        ned.setLote(notaEntradaDetalle.getLote());
        ned.setFechaVencimiento(notaEntradaDetalle.getFechaVencimiento());
        ned.setValorCompra(notaEntradaDetalle.getValorCompra());
        ned.setPrecioCompra(BigDecimal.ZERO);
        ned.setDesc1(0);
        ned.setDesc2(0);
        ned.setMontoDescitem(BigDecimal.ZERO);
        ned.setCantIngreso(notaEntradaDetalle.getCantIngreso());
        ned.setIdEquivalencia(equivalencia.getIdequivalencia());
        //Buscar el factor de multiplicacion de acuerdo a   la unidad equivalente seleccionado
        equivalencia = equivalenciaBl.buscaxId(equivalencia.getIdequivalencia());
        //buscar la unidad de medida que corresponde a dicha equivalencia
        unidadMedida = unidadMedidaBl.buscar(equivalencia.getUnidadEquivalente().getIdunidadmedida());
        //
        ned.setUnidadMedida(unidadMedida.getDescripcion());
        ned.setTotalProductos((notaEntradaDetalle.getCantIngreso().multiply(new BigDecimal(equivalencia.getFactor()))));
        ned.setCantSolicitada(BigDecimal.ZERO);
        ned.setCantPendiente(BigDecimal.ZERO);
        ned.setCantRecibida(BigDecimal.ZERO);

        listNotaEntradaDetalle.add(ned);
    }

    public void buscarProducto() {
        producto = getProductoBl().buscarxID(notaEntradaDetalle.getProducto().getIdproducto());
        //buscar  equivalencias  de la unidad base del producto seleccionado
        //listarEquivalenciaxUnidadMedida(producto.getUnidadMedida().getIdunidadmedida());
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

    public NotaEntradaDetalle getNotaEntradaDetalle() {
        return notaEntradaDetalle;
    }

    public void setNotaEntradaDetalle(NotaEntradaDetalle notaEntradaDetalle) {
        this.notaEntradaDetalle = notaEntradaDetalle;
    }

    public NotaIngresoDetalleBl getNotaIngresoDetalleBl() {
        return notaIngresoDetalleBl;
    }

    public void setNotaIngresoDetalleBl(NotaIngresoDetalleBl notaIngresoDetalleBl) {
        this.notaIngresoDetalleBl = notaIngresoDetalleBl;
    }

    public List<OrdenCompraDetalle> getListOrdenCompraDetalle() {
        return listOrdenCompraDetalle;
    }

    public void setListOrdenCompraDetalle(List<OrdenCompraDetalle> listOrdenCompraDetalle) {
        this.listOrdenCompraDetalle = listOrdenCompraDetalle;
    }

    public OrdenCompraDetalleBl getOrdenCompraDetalleBl() {
        return ordenCompraDetalleBl;
    }

    public void setOrdenCompraDetalleBl(OrdenCompraDetalleBl ordenCompraDetalleBl) {
        this.ordenCompraDetalleBl = ordenCompraDetalleBl;
    }

    public List<NotaEntradaDetalle> getListNotaEntradaDetalle() {
        return listNotaEntradaDetalle;
    }

    public void setListNotaEntradaDetalle(List<NotaEntradaDetalle> listNotaEntradaDetalle) {
        this.listNotaEntradaDetalle = listNotaEntradaDetalle;
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

    public NotaEntradaDetalle getNotaEntradaDetalleTemp() {
        return notaEntradaDetalleTemp;
    }

    public void setNotaEntradaDetalleTemp(NotaEntradaDetalle notaEntradaDetalleTemp) {
        this.notaEntradaDetalleTemp = notaEntradaDetalleTemp;
    }

    private long registrarOrdenCompraSeguimiento() {
        OrdenCompra temp = new OrdenCompra();
        if (notaEntrada.getOrdenCompra() != null) {
            temp = ordenCompraBl.buscar(notaEntrada.getOrdenCompra().getIdordencompra());
            ordenCompraSeguimiento.setOrdenCompra(notaEntrada.getOrdenCompra());
            ordenCompraSeguimiento.setFecha(new Date());
            ordenCompraSeguimiento.setHora(Utilitarios.horaActual());
            ordenCompraSeguimiento.setNumero(ordenCompraSeguimientoBl.maxNumero(notaEntrada.getOrdenCompra().getIdordencompra()) + 1);
            HttpSession sesionUser = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            if (sesionUser.getAttribute("idUsuario") != null) {
                ordenCompraSeguimiento.setIdUser(Long.parseLong(sesionUser.getAttribute("idUsuario").toString()));
            } else {
                ordenCompraSeguimiento.setIdUser(0);
            }
            ordenCompraSeguimiento.setObservacion("");

            return ordenCompraSeguimientoBl.registrar(ordenCompraSeguimiento);
        } else {
            return -1;
        }

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

    public int getTotalProductos() {
        return totalProductos;
    }

    public void setTotalProductos(int totalProductos) {
        this.totalProductos = totalProductos;
    }

    private long registrarStockAlmacen(NotaEntradaDetalle obj) {
        almacenProducto.setProducto(obj.getProducto());
        almacenProducto.setStockActual(obj.getTotalProductos());
        almacenProducto.setProducto(obj.getProducto());
        almacenProducto.setAlmacen(obj.getNotaEntrada().getAlmacenDestino());
        almacenProducto.setLote(obj.getLote());
        almacenProducto.setFechaVencimiento(obj.getFechaVencimiento());
        almacenProducto.setValorCompraUnitario(obj.getValorCompra());
        almacenProducto.setIdEquivalencia(obj.getIdEquivalencia());
        almacenProducto.setFechaIngreso(new Date());
        //registrar el orden de ingreso para cumplir con FIFO
        int numOrden = almacenProductoBl.obtenerUltimoNumero(obj.getProducto().getIdproducto());
        almacenProducto.setOrdenIngreso(numOrden + 1);
        almacenProducto.setUnidad(obj.getUnidadMedida());

        almacenProductoBl.registrar(almacenProducto);
        return almacenProducto.getIdalmacenproducto();

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

    private long registrarNotaEntrada() {
        long r = -1;
        Usuario user = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        //BUSCAR ORDEN COMPRA PARA OBTENER SU PROVEEDOR  Y ALMACEN DESTINNO REGISTRADO
        //POR ALGUNA  RAZON NO PERSISTE ESOS OBJETOS -> VERIFICAR
        OrdenCompra temp = ordenCompraBl.buscarXId(notaEntrada.getOrdenCompra().getIdordencompra());
        if (temp != null) {
            if (notaEntrada.getOrdenCompra() != null && notaEntrada.getOrdenCompra().getIdordencompra() != 0) {
                notaEntrada.setOrdenCompra(notaEntrada.getOrdenCompra());
                notaEntrada.setProveedor(temp.getProveedor());
                notaEntrada.setAlmacenDestino(temp.getAlmacenSolicitante());
            } else {
                notaEntrada.setOrdenCompra(null);
                notaEntrada.setProveedor(null);
                notaEntrada.setAlmacenDestino(null);
            }
            notaEntrada.setIdUserReg(user != null ? user.getIdusuario() : 0);
            notaEntrada.setObservacion("");

            r = notaIngresoBl.registrar(notaEntrada);
        } else {
            notaEntrada.setOrdenCompra(null);
            notaEntrada.setIdUserReg(user != null ? user.getIdusuario() : 0);
            notaEntrada.setObservacion("");
            notaEntrada.setProveedor(notaEntrada.getProveedor().getIdproveedor() > 0 ? notaEntrada.getProveedor() : null);
            r = notaIngresoBl.registrar(notaEntrada);
        }

        return r;
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

    public List<Equivalencia> getListEquivalencia() {
        return listEquivalencia;
    }

    public void setListEquivalencia(List<Equivalencia> listEquivalencia) {
        this.listEquivalencia = listEquivalencia;
    }

    public List<SelectItem> getSelectOneItemsEquivalencia() {
        this.selectOneItemsEquivalencia = new LinkedList<SelectItem>();
        if (producto != null) {
            for (Equivalencia obj : listarEquivalenciaxUnidadMedida(producto.getUnidadMedida().getIdunidadmedida())) {
                this.setEquivalencia(obj);
                SelectItem selectItem = new SelectItem(equivalencia.getIdequivalencia(), getEquivalencia().getUnidadEquivalente().getDescripcion());
                this.selectOneItemsEquivalencia.add(selectItem);
            }
            return selectOneItemsEquivalencia;
        } else {
            return null;
        }

    }

    public void setSelectOneItemsEquivalencia(List<SelectItem> selectOneItemsEquivalencia) {
        this.selectOneItemsEquivalencia = selectOneItemsEquivalencia;
    }

    private List<Equivalencia> listarEquivalenciaxUnidadMedida(long idunidadmedida) {
        listEquivalencia = equivalenciaBl.listarEquivalenciaxUnidadMedida(idunidadmedida);
        return listEquivalencia;
    }

    public List<NotaEntradaDetalle> getListNotaEntradaDetalleTemp() {
        return listNotaEntradaDetalleTemp;
    }

    public void setListNotaEntradaDetalleTemp(List<NotaEntradaDetalle> listNotaEntradaDetalleTemp) {
        this.listNotaEntradaDetalleTemp = listNotaEntradaDetalleTemp;
    }

    public Kardex getKardex() {
        return kardex;
    }

    public void setKardex(Kardex kardex) {
        this.kardex = kardex;
    }

    public KardexBl getKardexBl() {
        return kardexBl;
    }

    public void setKardexBl(KardexBl kardexBl) {
        this.kardexBl = kardexBl;
    }

    private long registrarKardex(NotaEntradaDetalle obj, NotaEntrada notaEntrada) {
        kardex.setProducto(obj.getProducto());
        kardex.setAlmacen(notaEntrada.getAlmacenDestino());
        kardex.setFechaMov(new Date());
        kardex.setMovimiento("INGRESO");
        kardex.setDetalle(notaEntrada.getTipoIngreso());
        kardex.setCantidad(obj.getTotalProductos());
        kardex.setValorUnit(obj.getValorCompra());
        kardex.setHoraMov(Utilitarios.horaActual());
        kardex.setNumero(kardexBl.maxNumero() + 1);
        kardex.setNroOrden(kardexBl.maxNumeroxproducto(obj.getProducto().getIdproducto(), notaEntrada.getAlmacenDestino().getIdalmacen()) + 1);

        kardex.setComprobante("");
        kardex.setNroComprobante("");

        return kardexBl.registrar(kardex);
    }

    public void visualizarNotaEntrada() {
        NotaEntrada temp = new NotaEntrada();
        HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        temp = (NotaEntrada) httpSession.getAttribute("notaEntrada");
        try {
            if (temp != null) {

                Map<String, Object> parametro = new HashMap<>();
                //File file = new File("C:\\Reportes\\REP-0005-nota-pedido.jasper");
                File file = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/WEB-INF/classes/org/siga/reportes/REP-0006-nota-entrada.jasper"));
                DSConeccion ds = new DSConeccion("192.168.32.33", "5432", "sigadb_desa", "siga%admin", "siga%admin");
                parametro.put("ID_ENTRADA", temp.getIdnotaentrada());
                byte[] documento = JasperRunManager.runReportToPdf(file.getPath(), parametro, ds.getConeccion());

                String fileType = "inline";
                String reportSetting = fileType + "; filename=Nota_Entrada.pdf";

                HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
                response.setContentType("application/pdf");
                response.addHeader("Content-disposition", reportSetting);
                response.setHeader("Cache-Control", "private");
                response.setContentLength(documento.length);

                ServletOutputStream stream = response.getOutputStream();
                stream.write(documento, 0, documento.length);
                stream.flush();
                stream.close();

                ds.getConeccion().close();

                FacesContext.getCurrentInstance().responseComplete();

            }
        } catch (JRException ex) {
            Logger.getLogger(NotaIngresoBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(NotaIngresoBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(NotaIngresoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        //return "Reportes?faces-redirect=true";
    }

    public UnidadMedida getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(UnidadMedida unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public UnidadMedidaBl getUnidadMedidaBl() {
        return unidadMedidaBl;
    }

    public void setUnidadMedidaBl(UnidadMedidaBl unidadMedidaBl) {
        this.unidadMedidaBl = unidadMedidaBl;
    }

    public OrdenCompraSeguimientoBl getOrdenCompraSeguimientoBl() {
        return ordenCompraSeguimientoBl;
    }

    public void setOrdenCompraSeguimientoBl(OrdenCompraSeguimientoBl ordenCompraSeguimientoBl) {
        this.ordenCompraSeguimientoBl = ordenCompraSeguimientoBl;
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
