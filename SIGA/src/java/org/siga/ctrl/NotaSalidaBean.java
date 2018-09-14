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
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;
import org.siga.be.Almacen;
import org.siga.be.AlmacenProducto;
import org.siga.be.Dependencia;
import org.siga.be.Equivalencia;
import org.siga.be.NotaSalida;
import org.siga.be.NotaSalidaDetalle;
import org.siga.be.Pedido;
import org.siga.be.PedidoDetalle;
import org.siga.be.PedidoSeguimiento;
import org.siga.be.Persona;
import org.siga.be.Producto;
import org.siga.be.TipoMovimiento;
import org.siga.be.UnidadMedida;
import org.siga.be.Usuario;
import org.siga.bl.AlmacenBl;
import org.siga.bl.AlmacenProductoBl;
import org.siga.bl.DependenciaBl;
import org.siga.bl.EquivalenciaBl;
import org.siga.bl.NotaSalidaBl;
import org.siga.bl.NotaSalidaDetalleBl;
import org.siga.bl.PedidoBl;
import org.siga.bl.PedidoDetalleBl;
import org.siga.bl.PedidoEstadoBl;
import org.siga.bl.PedidoSeguimientoBl;
import org.siga.bl.PersonaBl;
import org.siga.bl.ProductoBl;
import org.siga.bl.UnidadMedidaBl;
import org.siga.bl.UsuarioBl;
import org.siga.util.MensajeView;
import org.siga.util.Utilitarios;

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

    @ManagedProperty(value = "#{equivalencia}")
    private Equivalencia equivalencia;
    @ManagedProperty(value = "#{equivalenciaBl}")
    private EquivalenciaBl equivalenciaBl;

    @ManagedProperty(value = "#{usuarioBl}")
    private UsuarioBl usuarioBl;
    @ManagedProperty(value = "#{usuario}")
    private Usuario usuario;

    @ManagedProperty(value = "#{personaBl}")
    private PersonaBl personaBl;
    @ManagedProperty(value = "#{persona}")
    private Persona persona;

    @ManagedProperty(value = "#{almacenBl}")
    private AlmacenBl almacenBl;
    @ManagedProperty(value = "#{almacen}")
    private Almacen almacen;

    @ManagedProperty(value = "#{dependenciaBl}")
    private DependenciaBl dependenciaBl;
    @ManagedProperty(value = "#{dependencia}")
    private Dependencia dependencia;

    @ManagedProperty(value = "#{unidadMedida}")
    private UnidadMedida unidadMedida;
    @ManagedProperty(value = "#{unidadMedidaBl}")
    private UnidadMedidaBl unidadMedidaBl;

    @ManagedProperty(value = "#{pedidoSeguimiento}")
    private PedidoSeguimiento pedidoSeguimiento;
    @ManagedProperty(value = "#{pedidoSeguimientoBl}")
    private PedidoSeguimientoBl pedidoSeguimientoBl;

    @ManagedProperty(value = "#{pedidoEstadoBl}")
    private PedidoEstadoBl pedidoEstadoBl;

    private List<NotaSalidaDetalle> listNotaSalidas = new LinkedList<>();
    private List<PedidoDetalle> listPedidoDetalle = new LinkedList<>();
    private List<Equivalencia> listEquivalencia;
    private List<SelectItem> selectOneItemsEquivalencia;
    private List<SelectItem> selectOneItemsDependencia;
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
        notaSalida.setPersonaDestino("");
        notaSalida.setDocRef("");
        if (pedido != null) {
            pedido = null;
        }
        //setear campos de origen nombre del usuario
        buscarPersonaOrigen();
    }

    public int maxNumero() {
        return notaSalidaBl.maxNumero();
    }

    public void buscarProducto() {
        producto = getProductoBl().buscarxID(notaSalidaDetalle.getProducto().getIdproducto());
    }
    /*
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
     */

    public void agregar() {
        NotaSalidaDetalle temp = new NotaSalidaDetalle();
        temp.setProducto(almacenProducto.getProducto());
        temp.setCantSalida(notaSalidaDetalle.getCantSalida());
        temp.setStock(almacenProducto.getStockActual());
        temp.setIdAlmacenProducto(almacenProducto.getIdalmacenproducto());
        //Buscar el factor de multiplicacion de acuerdo a   la unidad equivalente seleccionado
        equivalencia = equivalenciaBl.buscaxId(equivalencia.getIdequivalencia());
        //buscar la unidad de medida que corresponde a dicha equivalencia
        unidadMedida = unidadMedidaBl.buscar(equivalencia.getUnidadEquivalente().getIdunidadmedida());
        //
        temp.setUnidadmedida(getUnidadMedida().getDescripcion());
        temp.setIdEquivalencia(equivalencia.getIdequivalencia());
        if (notaSalidaDetalle.getCantSalida() <= almacenProducto.getStockActual()) {
            getListNotaSalidas().add(temp);
        } else {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Cantidad no disponible");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }

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
                            equivalencia = equivalenciaBl.buscaxId(nsd.getIdEquivalencia());
                            if (equivalencia != null) {
                                actualizarStock(MensajeView.SALIDA, nsd.getIdAlmacenProducto(), (int) (nsd.getCantSalida() * equivalencia.getFactor()));
                            }

                        } else {
                            cont++;
                        }
                    }
                    if (pedido != null && pedido.getIdpedido() > 0) {
                        if (cont == 0) {//registrar un nuevo item en pedido seguimiento del pedido para ya no mostrar en la lista
                            pedidoSeguimiento.setFecha(new Date());
                            pedidoSeguimiento.setHora(Utilitarios.horaActual());
                            pedidoSeguimiento.setPedido(pedido);
                            pedidoSeguimiento.setNumero(pedidoSeguimientoBl.maxNumero(pedido.getIdpedido()) + 1);
                            pedidoSeguimiento.setEstado(pedidoEstadoBl.buscar(4));
                            pedidoSeguimientoBl.registrar(pedidoSeguimiento);
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
        Usuario user = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        notaSalida.setFechaReg(new Date());
        notaSalida.setIdUserReg(user!=null?user.getIdusuario():0);
        //determinar almacen oprien y destino de  acuerdo a que tipo de salida es: DISTRIBUCION SIMPLE O  CON NOTA DE PEDIDO
        if (this.getNotaSalida().getPedido().getIdpedido() > 0) {
            //Obtener todos los datos del pedido para determinar  origen y destino 
            Pedido temp = new Pedido();
            pedido = pedidoBl.buscarXid(this.getNotaSalida().getPedido().getIdpedido());
            if (temp != null) {
                notaSalida.setPedido(pedido);
                notaSalida.setAlmacenOrigen(pedido.getDependencia().getAlmacen());
                notaSalida.setDependencia(pedido.getDependencia());
                notaSalida.setAlmacenDestino(pedido.getAlmacenDestino());
            }
        } else {
            notaSalida.setAlmacenOrigen(almacen != null ? almacen : null);
            notaSalida.setAlmacenDestino(notaSalida.getAlmacenDestino().getIdalmacen() > 0 ? notaSalida.getAlmacenDestino() : null);
            notaSalida.setObservacion(notaSalida.getObservacion().toUpperCase());
            notaSalida.setPersonaDestino(notaSalida.getPersonaDestino().toUpperCase());
            notaSalida.setDocRef(notaSalida.getDocRef().toUpperCase());
            notaSalida.setPedido(null);
        }
        //PARA  REGISTRAR POR DISTRIBUCION SIMPLE DESDE OTRA PAGINA

        return notaSalidaBl.registrar(notaSalida);

        //return notaSalida.getIdnotasalida();
    }

    /*metodo funcionando  para  nota salida distribucion
     public long registrarNotaSalida() {

     notaSalida.setFechaReg(new Date());
     notaSalida.setIdUserReg(0);
     //notaSalida.setAlmacenOrigen(notaSalida.getAlmacenOrigen().getIdalmacen() > 0 ? notaSalida.getAlmacenOrigen() : null);
     notaSalida.setAlmacenOrigen(almacen != null ? almacen : null);
     notaSalida.setAlmacenDestino(notaSalida.getAlmacenDestino().getIdalmacen() > 0 ? notaSalida.getAlmacenDestino() : null);
     notaSalida.setObservacion(notaSalida.getObservacion().toUpperCase());
     notaSalida.setPersonaDestino(notaSalida.getPersonaDestino().toUpperCase());
     notaSalida.setDocRef(notaSalida.getDocRef().toUpperCase());

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
     */
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
            nsd.setIdEquivalencia(obj.getIdEquivalencia());
            nsd.setUnidadmedida(obj.getUnidadMedida());
            nsd.setNotasalida(notaSalida);
            nsd.setCantSolicitada(obj.getCantidadSolicitada());
            nsd.setCantAtendida(obj.getCantidadSurtida());
            nsd.setCantPendiente(obj.getCantidadSolicitada() - obj.getCantidadAutorizada());
            nsd.setCantSalida(obj.getCantidadAutorizada());
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

                if (NotaSalidaDetalleTemp.getCantSalida() > obj.getCantSolicitada()) {
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
        System.out.println("stok que queda luego de atender el pedido .... " + temp.getStockActual());
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

    public List<SelectItem> getSelectOneItemsEquivalencia() {
        this.selectOneItemsEquivalencia = new LinkedList<SelectItem>();
        if (almacenProducto.getProducto() != null) {
            for (Equivalencia obj : listarEquivalenciaxUnidadMedida(almacenProducto.getProducto().getUnidadMedida().getIdunidadmedida())) {
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

    private void buscarPersonaOrigen() {
        HttpSession sesionUser = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        if (sesionUser.getAttribute("idUsuario") != null) {
            usuario = usuarioBl.buscarxIdUsuario(Long.parseLong(sesionUser.getAttribute("idUsuario").toString()));
            persona = personaBl.buscar(usuario.getPersona().getIdpersona());
            almacen = almacenBl.buscar(usuario.getDependencia().getAlmacen().getIdalmacen());
        }
    }

    public UsuarioBl getUsuarioBl() {
        return usuarioBl;
    }

    public void setUsuarioBl(UsuarioBl usuarioBl) {
        this.usuarioBl = usuarioBl;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public PersonaBl getPersonaBl() {
        return personaBl;
    }

    public void setPersonaBl(PersonaBl personaBl) {
        this.personaBl = personaBl;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public AlmacenBl getAlmacenBl() {
        return almacenBl;
    }

    public void setAlmacenBl(AlmacenBl almacenBl) {
        this.almacenBl = almacenBl;
    }

    public Almacen getAlmacen() {
        return almacen;
    }

    public void setAlmacen(Almacen almacen) {
        this.almacen = almacen;
    }

    public List<Dependencia> listarDependenciaxAlmacen() {
        return dependenciaBl.listarDependenciaxAlmacen(notaSalida.getAlmacenDestino().getIdalmacen());
    }

    public DependenciaBl getDependenciaBl() {
        return dependenciaBl;
    }

    public void setDependenciaBl(DependenciaBl dependenciaBl) {
        this.dependenciaBl = dependenciaBl;
    }

    public Dependencia getDependencia() {
        return dependencia;
    }

    public void setDependencia(Dependencia dependencia) {
        this.dependencia = dependencia;
    }

    public List<SelectItem> getSelectOneItemsDependencia() {
        this.selectOneItemsDependencia = new LinkedList<SelectItem>();
        for (Dependencia obj : listarDependenciaxAlmacen()) {
            this.setDependencia(obj);
            SelectItem selectItem = new SelectItem(dependencia.getIddependencia(), dependencia.getDescripcion());
            this.selectOneItemsDependencia.add(selectItem);
        }
        return selectOneItemsDependencia;
    }

    public void setSelectOneItemsDependencia(List<SelectItem> selectOneItemsDependencia) {
        this.selectOneItemsDependencia = selectOneItemsDependencia;
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

    public PedidoSeguimiento getPedidoSeguimiento() {
        return pedidoSeguimiento;
    }

    public void setPedidoSeguimiento(PedidoSeguimiento pedidoSeguimiento) {
        this.pedidoSeguimiento = pedidoSeguimiento;
    }

    public PedidoSeguimientoBl getPedidoSeguimientoBl() {
        return pedidoSeguimientoBl;
    }

    public void setPedidoSeguimientoBl(PedidoSeguimientoBl pedidoSeguimientoBl) {
        this.pedidoSeguimientoBl = pedidoSeguimientoBl;
    }

    public PedidoEstadoBl getPedidoEstadoBl() {
        return pedidoEstadoBl;
    }

    public void setPedidoEstadoBl(PedidoEstadoBl pedidoEstadoBl) {
        this.pedidoEstadoBl = pedidoEstadoBl;
    }

}
