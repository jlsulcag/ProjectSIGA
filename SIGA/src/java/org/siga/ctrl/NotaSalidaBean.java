package org.siga.ctrl;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;
import org.siga.be.Almacen;
import org.siga.be.AlmacenProducto;
import org.siga.be.NotaSalida;
import org.siga.be.NotaSalidaDetalle;
import org.siga.be.Pedido;
import org.siga.be.PedidoDetalle;
import org.siga.be.Producto;
import org.siga.be.TipoMovimiento;
import org.siga.bl.AlmacenProductoBl;
import org.siga.bl.NotaSalidaBl;
import org.siga.bl.NotaSalidaDetalleBl;
import org.siga.bl.PedidoBl;
import org.siga.bl.PedidoDetalleBl;
import org.siga.bl.ProductoBl;
import org.siga.util.MensajeView;

@ManagedBean
@ViewScoped
public class NotaSalidaBean {

    @ManagedProperty(value = "#{notaSalida}")
    private NotaSalida notaSalida;
    @ManagedProperty(value = "#{notaSalidaBl}")
    private NotaSalidaBl notaSalidaBl;

    @ManagedProperty(value = "#{notaSalidaDetalle}")
    private NotaSalidaDetalle notaSalidaDetalle;
    @ManagedProperty(value = "#{notaSalidaDetalleBl}")
    private NotaSalidaDetalleBl notaSalidaDetalleBl;

    @ManagedProperty(value = "#{producto}")
    private Producto producto;
    @ManagedProperty(value = "#{productoBl}")
    private ProductoBl productoBl;

    @ManagedProperty(value = "#{almacenProducto}")
    private AlmacenProducto almacenProducto;
    @ManagedProperty(value = "#{almacenProductoBl}")
    private AlmacenProductoBl almacenProductoBl;

    @ManagedProperty(value = "#{pedido}")
    private Pedido pedido;
    @ManagedProperty(value = "#{pedidoBl}")
    private PedidoBl pedidoBl;

    @ManagedProperty(value = "#{pedidoDetalleBl}")
    private PedidoDetalleBl pedidoDetalleBl;

    private List<NotaSalidaDetalle> listNotaSalidas = new LinkedList<>();
    private List<PedidoDetalle> listPedidoDetalle = new LinkedList<>();
    private boolean pedidoxUnidad;
    private int totalProductos;
    private int stock;

    private NotaSalidaDetalle NotaSalidaDetalleTemp;

    public NotaSalidaBean() {
    }

    public void buscarAlmacenProducto() {
        almacenProducto = almacenProductoBl.buscarxId(almacenProducto.getIdalmacenproducto());
    }

    public void viewAlmacenProducto() {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("resizable", false);
        RequestContext.getCurrentInstance().openDialog("AlmacenProducto", options, null);
    }

    @PostConstruct
    public void inicio() {
        notaSalida.setNumero(maxNumero() + 1);
        notaSalida.setFechaReg(new Date());
        listNotaSalidas.clear();
        notaSalida.setObservacion("");
        notaSalida.setAlmacenOrigen(new Almacen());
        notaSalida.setAlmacenDestino(new Almacen());
        notaSalida.setTipomovimiento(new TipoMovimiento());
        notaSalida.setPedido(new Pedido());
    }

    public int maxNumero() {
        return notaSalidaBl.maxNumero();
    }

    public void buscarProducto() {
        producto = getProductoBl().buscarxID(notaSalidaDetalle.getProducto().getIdproducto());
    }

    public void setIsPedidoUnitario() {
        setPedidoxUnidad(pedidoxUnidad);
//        if (notaSalidaDetalle.getCantidad() > 0) {
//            calcularTotalProductos();
//        }
    }

    public void calcularTotalProductos() {
        if (pedidoxUnidad) {
            setTotalProductos(notaSalidaDetalle.getCantSolicitada());
        } else {
            setTotalProductos(notaSalidaDetalle.getCantSolicitada() * producto.getFraccion());
        }
    }

    public void agregar() {
        NotaSalidaDetalle temp = new NotaSalidaDetalle();
        temp.setProducto(almacenProducto.getProducto());
        temp.setCantSolicitada(notaSalidaDetalle.getCantSolicitada());
        temp.setStock(almacenProducto.getStockActual());
        temp.setIdAlmacenProducto(almacenProducto.getIdalmacenproducto());
        if (pedidoxUnidad) {
            temp.setUnidadmedida("UNIDAD");
        } else {
            temp.setUnidadmedida(almacenProducto.getProducto().getUnidadMedida().getDescripcion());
        }
        getListNotaSalidas().add(temp);

    }

    public void registrar() {
        long res = -1;
        long res2 = -1;
        int cont = 0;//Contador de items no atendidos
        if (!listNotaSalidas.isEmpty()) {
            res = registrarNotaSalida();
            if (res == 0) {
                res2 = registrarNotaSalidaDetalle();
                if (res2 == 0) {
                    for (NotaSalidaDetalle nsd : listNotaSalidas) {
                        if (nsd.getStock() >= 0 && nsd.getStock() >= nsd.getCantSolicitada()) {
                            actualizarStock(MensajeView.SALIDA, nsd.getIdAlmacenProducto(), nsd.getCantSolicitada());
                        } else {
                            cont++;
                        }
                    }
                    if (pedido != null) {
                        if (cont == 0) {//Actualizar estado del pedido para ya no mostrar en la lista
                            pedido.setEstado("CERRADO");
                            pedidoBl.actualizar(pedido);
                        }
                    }

                    MensajeView.registroCorrecto();
                    //actualizar el estado del pedido si fuese el caso
                    inicio();
                } else {
                    MensajeView.registroError();
                }
            } else {
                MensajeView.registroError();
            }
        } else {
            MensajeView.listVacia();
        }

    }

    public long registrarNotaSalida() {

        notaSalida.setFechaReg(new Date());
        notaSalida.setIdUserReg(0);
        notaSalida.setAlmacenOrigen(notaSalida.getAlmacenOrigen().getIdalmacen() > 0 ? notaSalida.getAlmacenOrigen() : null);
        notaSalida.setAlmacenDestino(notaSalida.getAlmacenDestino().getIdalmacen() > 0 ? notaSalida.getAlmacenDestino() : null);
        notaSalida.setObservacion(notaSalida.getObservacion().toUpperCase());

        //Obtener todos los datos del pedido para determinar  origen y destino 
        pedido = pedidoBl.buscarXid(notaSalida.getPedido().getIdpedido());

        if (pedido != null && pedido.getIdpedido() > 0) {
            notaSalida.setPedido(notaSalida.getPedido());
            notaSalida.setAlmacenOrigen(pedido.getDependencia().getAlmacen());
            notaSalida.setAlmacenDestino(pedido.getAlmacenDestino());
        } else {
            notaSalida.setPedido(null);
        }

        return notaSalidaBl.registrar(notaSalida);

        //return notaSalida.getIdnotasalida();
    }

    private long registrarNotaSalidaDetalle() {
        long id = -1;
        for (NotaSalidaDetalle obj : listNotaSalidas) {
            if (obj.getStock() >= obj.getCantSolicitada()) {
                obj.setNotasalida(notaSalida);
                id = notaSalidaDetalleBl.registrar(obj);
            }

            //Actualizar stock almacen
        }
        return id;
    }

    public void inicioNew() {
        notaSalidaDetalle.setProducto(new Producto());
        producto.setFraccion(0);
        producto.setUnidadMedida(null);
        setPedidoxUnidad(false);
        notaSalidaDetalle.setCantSolicitada(0);
        notaSalidaDetalle.setCantAtendida(0);
        notaSalidaDetalle.setCantPendiente(0);
        notaSalidaDetalle.setCantSalida(0);
        setTotalProductos(0);
    }

    public void listarDetallePedido() {
        listNotaSalidas.clear();
        //obtener la lista con los detalles del pedido
        long id = notaSalida.getPedido().getIdpedido();
        listPedidoDetalle = pedidoDetalleBl.listarxIdPedido(id);
        for (PedidoDetalle obj : listPedidoDetalle) {
            NotaSalidaDetalle nsd = new NotaSalidaDetalle();
            nsd.setProducto(obj.getProducto());
            nsd.setUnidadmedida(obj.getUnidadMedida());
            nsd.setNotasalida(notaSalida);
            nsd.setCantSolicitada(obj.getCantidadSolicitada());
            //buscar el producto en el almacen  para realizar  la actualizacion de stock de acuerdo al orden de ingreso  y stock disponible
            nsd.setIdAlmacenProducto(almacenProductoBl.buscarMinNumeroOrdenxProducto(nsd.getProducto().getIdproducto()));
            nsd.setStock(almacenProductoBl.buscarStockxProducto(nsd.getProducto().getIdproducto()));

            listNotaSalidas.add(nsd);
        }
    }

    public void onRowEdit(RowEditEvent event) {
        NotaSalidaDetalleTemp = new NotaSalidaDetalle();
        String msg = "";
        NotaSalidaDetalleTemp.setProducto(((NotaSalidaDetalle) event.getObject()).getProducto());
        NotaSalidaDetalleTemp.setCantSalida(((NotaSalidaDetalle) event.getObject()).getCantSalida());
        for (NotaSalidaDetalle obj : listNotaSalidas) {
            //obj.setCantPendiente(notaEntradaDetalle.getCantPendiente());
            if (obj.getProducto() == NotaSalidaDetalleTemp.getProducto()) {

                if (NotaSalidaDetalleTemp.getCantSalida() > obj.getCantPendiente()) {
                    //la cantidad ingresa se debe mantener
                    obj.setCantSalida(obj.getCantPendiente());
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
    /*
     public int actualizarStock(){
     System.out.println("pedido    "+notaSalida);
     System.out.println("Detalle    "+listNotaSalidas);
     int res = -1;
     for (NotaSalidaDetalle obj : listNotaSalidas) {
     AlmacenProducto temp = new AlmacenProducto();
     almacenProducto.setProducto(obj.getProducto());
     almacenProducto.setAlmacen(obj.getNotasalida().getAlmacenOrigen());
     almacenProducto.setLote(obj.getProducto().get);
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
     */

    public NotaSalida getNotaSalida() {
        return notaSalida;
    }

    public void setNotaSalida(NotaSalida notaSalida) {
        this.notaSalida = notaSalida;
    }

    public NotaSalidaBl getNotaSalidaBl() {
        return notaSalidaBl;
    }

    public void setNotaSalidaBl(NotaSalidaBl notaSalidaBl) {
        this.notaSalidaBl = notaSalidaBl;
    }

    public NotaSalidaDetalle getNotaSalidaDetalle() {
        return notaSalidaDetalle;
    }

    public void setNotaSalidaDetalle(NotaSalidaDetalle notaSalidaDetalle) {
        this.notaSalidaDetalle = notaSalidaDetalle;
    }

    public NotaSalidaDetalleBl getNotaSalidaDetalleBl() {
        return notaSalidaDetalleBl;
    }

    public void setNotaSalidaDetalleBl(NotaSalidaDetalleBl notaSalidaDetalleBl) {
        this.notaSalidaDetalleBl = notaSalidaDetalleBl;
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

    public boolean isPedidoxUnidad() {
        return pedidoxUnidad;
    }

    public void setPedidoxUnidad(boolean pedidoxUnidad) {
        this.pedidoxUnidad = pedidoxUnidad;
    }

    public int getTotalProductos() {
        return totalProductos;
    }

    public void setTotalProductos(int totalProductos) {
        this.totalProductos = totalProductos;
    }

    public List<NotaSalidaDetalle> getListNotaSalidas() {
        return listNotaSalidas;
    }

    public void setListNotaSalidas(List<NotaSalidaDetalle> listNotaSalidas) {
        this.listNotaSalidas = listNotaSalidas;
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

    public PedidoDetalleBl getPedidoDetalleBl() {
        return pedidoDetalleBl;
    }

    public void setPedidoDetalleBl(PedidoDetalleBl pedidoDetalleBl) {
        this.pedidoDetalleBl = pedidoDetalleBl;
    }

    public List<PedidoDetalle> getListPedidoDetalle() {
        return listPedidoDetalle;
    }

    public void setListPedidoDetalle(List<PedidoDetalle> listPedidoDetalle) {
        this.listPedidoDetalle = listPedidoDetalle;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public PedidoBl getPedidoBl() {
        return pedidoBl;
    }

    public void setPedidoBl(PedidoBl pedidoBl) {
        this.pedidoBl = pedidoBl;
    }

    private void actualizarStock(int op, long idAlmacenProducto, int cantidad) {
        AlmacenProducto temp = new AlmacenProducto();
        temp = almacenProductoBl.buscar(idAlmacenProducto);
        if (op == MensajeView.SALIDA) {
            temp.setStockActual(temp.getStockActual() - cantidad);
        } else if (op == MensajeView.ENTRADA) {
            temp.setStockActual(temp.getStockActual() + cantidad);
        }
        almacenProducto.setIdalmacenproducto(temp.getIdalmacenproducto());
        almacenProductoBl.actualizar(temp);
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public NotaSalidaDetalle getNotaSalidaDetalleTemp() {
        return NotaSalidaDetalleTemp;
    }

    public void setNotaSalidaDetalleTemp(NotaSalidaDetalle NotaSalidaDetalleTemp) {
        this.NotaSalidaDetalleTemp = NotaSalidaDetalleTemp;
    }

}
