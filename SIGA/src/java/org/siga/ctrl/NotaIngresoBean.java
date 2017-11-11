package org.siga.ctrl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.RowEditEvent;
import org.siga.be.Almacen;
import org.siga.be.AlmacenProducto;
import org.siga.be.NotaEntrada;
import org.siga.be.NotaEntradaDetalle;
import org.siga.be.OrdenCompra;
import org.siga.be.OrdenCompraDetalle;
import org.siga.be.Producto;
import org.siga.be.Proveedor;
import org.siga.bl.AlmacenProductoBl;
import org.siga.bl.NotaIngresoBl;
import org.siga.bl.NotaIngresoDetalleBl;
import org.siga.bl.OrdenCompraBl;
import org.siga.bl.OrdenCompraDetalleBl;
import org.siga.bl.ProductoBl;
import org.siga.util.MensajeView;

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

    private NotaEntradaDetalle notaEntradaDetalleTemp;

    //private List<NotaEntrada> listNotaEntrada;
    private List<NotaEntradaDetalle> listNotaEntradaDetalle = new LinkedList<>();
    private List<OrdenCompraDetalle> listOrdenCompraDetalle;
    private boolean compraxUnidad;
    private int totalProductos;

    private long res;

    public NotaIngresoBean() {
    }

    public void registrar() {
        //validar  que el stock de los productos existan en almacen
        int r = -1;
        if (!listNotaEntradaDetalle.isEmpty()) {
            res = registrarNotaEntrada();
            //Registrar Nota Entrada Detalle
            if (res == 0) {
                for (NotaEntradaDetalle obj : listNotaEntradaDetalle) {
                    obj.setNotaEntrada(notaEntrada);
                    notaIngresoDetalleBl.registrar(obj);
                }
                //Actualizar el estado de  la orden de compra, solo si es que el ingreso proviene de una orden de compra
                if (notaEntrada.getOrdenCompra() != null && notaEntrada.getOrdenCompra().getIdordencompra() != 0) {
                    long res = -1;
                    res = actualizarEstadoOrdenCompra();
                }
                //Actualizar el stock de almacen segun las cantidades ingresadas
                r = actualizarStockAlmacen();
                if (r == 1) {
                    MensajeView.registroCorrecto();
                } else {
                    MensajeView.registroError();
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
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("NotaIngresoBean", null);
        notaEntrada.setIdnotaentrada(0);
        notaEntrada.setOrdenCompra(new OrdenCompra());
        notaEntrada.setNumero(maxNumero() + 1);
        notaEntrada.setFechaReg(new Date());
        notaEntrada.setFechaDocref(null);
        notaEntrada.setNroDocref("");
        notaEntrada.setObservacion("");
        notaEntrada.setTipoIngreso("");
        ordenCompra.setEstado("");
        notaEntrada.setProveedor(new Proveedor());
        notaEntrada.setAlmacenDestino(new Almacen());
        listNotaEntradaDetalle.clear();
    }

    public void limpiarNew() {
        System.out.println("metodo limpia  neww  ");
        notaEntradaDetalle.setIdnotaentradadetalle(0);
        notaEntradaDetalle.setProducto(new Producto());
        notaEntradaDetalle.setUnidadMedida("");
        //notaEntradaDetalle.getProducto().setFraccion(0);
        producto.setFraccion(0);
        producto.getUnidadMedida().setDescripcion("");
        setCompraxUnidad(false);
        notaEntradaDetalle.setCantIngreso(0);
        setTotalProductos(0);
        notaEntradaDetalle.setFechaVencimiento(null);
        notaEntradaDetalle.setLote("");
//        listOrdenCompraDetalle = new LinkedList<>();
    }

    private long maxNumero() {
        return getNotaIngresoBl().buscarUltimoNumero();
    }

    public void listarDetalleOrdenCompra() {
        listNotaEntradaDetalle.clear();
        //Obtener la lista de DetalleOrdenCompra
        long id = notaEntrada.getOrdenCompra().getIdordencompra();
        listOrdenCompraDetalle = ordenCompraDetalleBl.listarXIdOrdenCompra(id);
        //listNotaEntradaDetalle = new ArrayList<>();
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

            notaED.setCantSolicitada(obj.getCantidad());
            //buscar la suma de la cantidad ingresada hasta ese momento.. buscar por orden de compra y producto
            notaED.setCantRecibida((int) notaIngresoDetalleBl.getCantIngresada(obj.getProducto().getIdproducto(), id));
            notaED.setCantPendiente(notaED.getCantSolicitada() - notaED.getCantRecibida());
            notaED.setCantIngreso(notaED.getCantPendiente());
            //validar que solo se agreguen los productos que faltan recepcionar
            //if (notaED.getCantRecibida() < notaED.getCantSolicitada()) {
            
            //buscar stock disponible en el inventario
            AlmacenProducto temp = new AlmacenProducto();
            temp = almacenProductoBl.buscarProductoxAlmacenyLote(notaED.getLote(), notaED.getNotaEntrada().getOrdenCompra().getAlmacenDestino().getIdalmacen(), notaED.getProducto());
            if(temp == null){
                notaED.setCantDisponible(0);
            }else{
                notaED.setCantDisponible(temp.getStockActual());
            }
            listNotaEntradaDetalle.add(notaED);
            //}
        }
    }

    public void onRowEdit(RowEditEvent event) {
        notaEntradaDetalleTemp = new NotaEntradaDetalle();
        String msg = "";
        notaEntradaDetalleTemp.setProducto(((NotaEntradaDetalle) event.getObject()).getProducto());
        notaEntradaDetalleTemp.setCantIngreso(((NotaEntradaDetalle) event.getObject()).getCantIngreso());
        for (NotaEntradaDetalle obj : listNotaEntradaDetalle) {
            //obj.setCantPendiente(notaEntradaDetalle.getCantPendiente());
            if (obj.getProducto() == notaEntradaDetalleTemp.getProducto()) {

                if (notaEntradaDetalleTemp.getCantIngreso() > obj.getCantPendiente()) {
                    //la cantidad ingresa se debe mantener
                    obj.setCantIngreso(obj.getCantPendiente());
                    msg = "La cantidad ingresada supera a la cantidad pendiente";
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atención", msg);
                    FacesContext.getCurrentInstance().addMessage(null, message);
                }
            }
        }

    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edicion cancelada", null);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void setIsCompraUnitaria() {
        setCompraxUnidad(compraxUnidad);
        if (notaEntradaDetalle.getCantIngreso() > 0) {
            calcularTotalProductos();
        }
    }
    public void calcularTotalProductos() {
        if (compraxUnidad) {
            setTotalProductos(notaEntradaDetalle.getCantIngreso());
        } else {
            setTotalProductos(notaEntradaDetalle.getCantIngreso() * producto.getFraccion());
        }
    }

    public void agregarProducto() {
        NotaEntradaDetalle ned = new NotaEntradaDetalle();
        ned.setProducto(producto);
        ned.setLote(notaEntradaDetalle.getLote());
        ned.setFechaVencimiento(notaEntradaDetalle.getFechaVencimiento());
        ned.setValorCompra(BigDecimal.ZERO);
        ned.setPrecioCompra(BigDecimal.ZERO);
        ned.setDesc1(0);
        ned.setDesc2(0);
        if (compraxUnidad) {
            ned.setUnidadMedida("UNIDAD");
        } else {
            ned.setUnidadMedida(producto.getUnidadMedida().getDescripcion());
        }
        ned.setMontoDescitem(BigDecimal.ZERO);
        ned.setCantIngreso(totalProductos);
        ned.setCantSolicitada(0);
        ned.setCantPendiente(0);
        ned.setCantRecibida(0);

        listNotaEntradaDetalle.add(ned);
    }

    public void buscarProducto() {
        producto = getProductoBl().buscarxID(notaEntradaDetalle.getProducto().getIdproducto());
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

    private long actualizarEstadoOrdenCompra() {
        OrdenCompra temp = new OrdenCompra();
        if (notaEntrada.getOrdenCompra() != null) {
            temp = ordenCompraBl.buscar(notaEntrada.getOrdenCompra().getIdordencompra());
            //temp = notaEntrada.getOrdenCompra();
//            temp.setIdordencompra(notaEntrada.getOrdenCompra().getIdordencompra());            
//            temp.setNumero(notaEntrada.getOrdenCompra().getNumero());
//            temp.setFecha(notaEntrada.getOrdenCompra().getFecha());
//            temp.setProveedor(notaEntrada.getOrdenCompra().getProveedor());
//            temp.setFechaEntrega(notaEntrada.getOrdenCompra().getFechaEntrega());
//            temp.setLugarEntrega(notaEntrada.getOrdenCompra().getLugarEntrega());
//            temp.setObservacion(notaEntrada.getOrdenCompra().getObservacion());            
            temp.setEstado(ordenCompra.getEstado());
//            temp.setHoraRegistro(notaEntrada.getOrdenCompra().getHoraRegistro());
//            temp.setFechaRecepcion(new Date());
//            temp.setIdAlmacenDestino(0);
//            temp.setIdMoneda(0);
//            temp.setDocReferencia("");
//            temp.setValorBruto(notaEntrada.getOrdenCompra().getValorBruto());
//            temp.setMontoDesc(notaEntrada.getOrdenCompra().getMontoDesc());
//            temp.setValorNeto(notaEntrada.getOrdenCompra().getValorNeto());
//            temp.setMontoIgv(notaEntrada.getOrdenCompra().getMontoIgv());
//            temp.setMontoTotal(notaEntrada.getOrdenCompra().getMontoTotal());

            return ordenCompraBl.actualizar(temp);
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

    private int actualizarStockAlmacen() {
        int res = -1;
        for (NotaEntradaDetalle obj : listNotaEntradaDetalle) {
            AlmacenProducto temp = new AlmacenProducto();
            almacenProducto.setProducto(obj.getProducto());
            almacenProducto.setAlmacen(obj.getNotaEntrada().getAlmacenDestino());
            almacenProducto.setLote(obj.getLote());
            almacenProducto.setFechaVencimiento(obj.getFechaVencimiento());
            almacenProducto.setValor(obj.getValorCompra());
            temp = almacenProductoBl.buscarProductoxAlmacenyLote(obj.getLote(), obj.getNotaEntrada().getAlmacenDestino().getIdalmacen(), obj.getProducto());
            if (temp != null && temp.getIdalmacenproducto() != 0) {//Actualizar registro existente
                almacenProducto.setIdalmacenproducto(temp.getIdalmacenproducto());
                almacenProducto.setStockActual(obj.getCantIngreso() + temp.getStockActual());
                almacenProductoBl.actualizar(almacenProducto);
                res = 1;
            } else {//Registrar nuevo
                almacenProducto.setProducto(obj.getProducto());
                almacenProducto.setStockActual(obj.getCantIngreso());
                almacenProductoBl.registrar(almacenProducto);
                res = 1;
            }
        }
        return res;
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
        //BUSCAR ORDEN COMPRA PARA OBTENER SU PROVEEDOR  Y ALMACEN DESTINNO REGISTRADO
        //POR ALGUNA  RAZON NO PERSISTE ESOS OBJETOS -> VERIFICAR
        OrdenCompra temp = ordenCompraBl.buscarXId(notaEntrada.getOrdenCompra().getIdordencompra());
        if (temp != null) {
            if (notaEntrada.getOrdenCompra() != null && notaEntrada.getOrdenCompra().getIdordencompra() != 0) {
                notaEntrada.setOrdenCompra(notaEntrada.getOrdenCompra());
                notaEntrada.setProveedor(temp.getProveedor());
                notaEntrada.setAlmacenDestino(temp.getAlmacenDestino());
            } else {
                notaEntrada.setOrdenCompra(null);
                notaEntrada.setProveedor(null);
                notaEntrada.setAlmacenDestino(null);
            }
            notaEntrada.setIdUserReg(0);
            notaEntrada.setObservacion("");

            r = notaIngresoBl.registrar(notaEntrada);
        } else {
            notaEntrada.setOrdenCompra(null);
            notaEntrada.setIdUserReg(0);
            notaEntrada.setObservacion("");

            r = notaIngresoBl.registrar(notaEntrada);
        }
        return r;
    }
}
