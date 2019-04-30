package org.siga.ctrl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;
import org.hibernate.HibernateException;
import org.siga.be.Almacen;
import org.siga.be.Dependencia;
import org.siga.be.Equivalencia;
import org.siga.be.Pedido;
import org.siga.be.PedidoDetalle;
import org.siga.be.PedidoSeguimiento;
import org.siga.be.Producto;
import org.siga.be.UnidadMedida;
import org.siga.be.Usuario;
import org.siga.bl.EquivalenciaBl;
import org.siga.bl.PedidoBl;
import org.siga.bl.PedidoDetalleBl;
import org.siga.bl.PedidoEstadoBl;
import org.siga.bl.PedidoSeguimientoBl;
import org.siga.bl.ProductoBl;
import org.siga.bl.UnidadMedidaBl;
import org.siga.util.MensajeView;
import org.siga.util.Utilitarios;

@ManagedBean
@ViewScoped
public class PedidoBean {

    @ManagedProperty(value = "#{pedido}")
    private Pedido pedido;
    @ManagedProperty(value = "#{pedidoBl}")
    private PedidoBl pedidoBl;
    @ManagedProperty(value = "#{pedidoDetalle}")
    private PedidoDetalle pedidoDetalle;
    @ManagedProperty(value = "#{pedidoDetalleBl}")
    private PedidoDetalleBl pedidoDetalleBl;
    @ManagedProperty(value = "#{producto}")
    private Producto producto;
    @ManagedProperty(value = "#{productoBl}")
    private ProductoBl productoBl;

    @ManagedProperty(value = "#{pedidoSeguimientoBl}")
    private PedidoSeguimientoBl pedidoSeguimientoBl;
    @ManagedProperty(value = "#{pedidoSeguimiento}")
    private PedidoSeguimiento pedidoSeguimiento;

    @ManagedProperty(value = "#{pedidoEstadoBl}")
    private PedidoEstadoBl pedidoEstadoBl;

    @ManagedProperty(value = "#{equivalencia}")
    private Equivalencia equivalencia;
    @ManagedProperty(value = "#{equivalenciaBl}")
    private EquivalenciaBl equivalenciaBl;
    
    @ManagedProperty(value = "#{unidadMedida}")
    private UnidadMedida unidadMedida;
    @ManagedProperty(value = "#{unidadMedidaBl}")
    private UnidadMedidaBl unidadMedidaBl;

    private List<PedidoDetalle> listPedidoDetalle = new LinkedList<>();
    private List<Pedido> listPedido = new LinkedList<>();
    private boolean pedidoxUnidad;
    private int totalProductos;
    private List<Equivalencia> listEquivalencias;

    private List<SelectItem> selectOneItemsPedido;
    private List<SelectItem> selectOneItemsEquivalencia;
    private List<SelectItem> pedidosPorEstado;
    private List<PedidoSeguimiento> listPedidoSeguimiento;

    public PedidoBean() {
    }

    @PostConstruct
    public void inicio() {
        pedido.setNumero(maxNumero() + 1);
        pedido.setFechaPedido(new Date());
        listPedidoDetalle.clear();
        pedido.setObservacion("");
        pedido.setDependencia(new Dependencia());
        pedido.setAlmacenDestino(new Almacen());
        setProducto(null);
    }

    private long maxNumero() {
        return pedidoBl.buscarMaxNumero();
    }

    public void agregar() {
        PedidoDetalle temp = new PedidoDetalle();
        temp.setProducto(producto);
        temp.setIdEquivalencia(equivalencia.getIdequivalencia());
        temp.setCantidadSolicitada(pedidoDetalle.getCantidadSolicitada());
        //Buscar el factor de multiplicacion de acuerdo a   la unidad equivalente seleccionado
        equivalencia = equivalenciaBl.buscaxId(equivalencia.getIdequivalencia());
        //buscar la unidad de medida que corresponde a dicha equivalencia
        unidadMedida = unidadMedidaBl.buscar(equivalencia.getUnidadEquivalente().getIdunidadmedida());
        //
        temp.setUnidadMedida(unidadMedida.getDescripcion());
        listPedidoDetalle.add(temp);

    }

    public void registrar() {
        //REGISTRAR PEDIDO
        long id = -1;
        long id2 = -1;
        if (!listPedidoDetalle.isEmpty()) {
            id = registrarPedido();
            if (id == 0) {
                //registrar detalle movimiento                
                //registrarPedidoMovimiento(id);//agregar dicha funcionalidad  posteriormente, conocer cuando cambiara d estados el pedido
                id2 = registrarPedidoDetalle();
                if (id2 > 0) {
                    registrarPedidoSeguimiento(pedido);
                    MensajeView.registroCorrecto();
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

    public long registrarPedido() {
        pedido.setObservacion(pedido.getObservacion().toUpperCase().trim());
        pedido.setHoraPedido(Utilitarios.horaActual());
        HttpSession sesionUser = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        if (sesionUser.getAttribute("idUsuario") != null) {
            pedido.setIdUserreg(Long.parseLong(sesionUser.getAttribute("idUsuario").toString()));
        } else {
            pedido.setIdUserreg(0);
        }

        return pedidoBl.registrar(pedido);
    }

    private long registrarPedidoDetalle() {
        long id = -1;
        for (PedidoDetalle obj : listPedidoDetalle) {
            obj.setPedido(pedido);
            obj.setCantidadAutorizada(BigDecimal.ZERO);
            obj.setCantidadSurtida(BigDecimal.ZERO);
            pedidoDetalleBl.registrar(obj);
            id = obj.getIdpedidodetalle();
        }
        return id;
    }

    public void inicioNew() {
        pedidoDetalle.setProducto(new Producto());
        pedidoDetalle.setCantidadSolicitada(BigDecimal.ZERO);
        setTotalProductos(0);
        if(producto != null){
            producto = null;
        }
    }

    public void buscarProducto() {
        producto = getProductoBl().buscarxID(pedidoDetalle.getProducto().getIdproducto());
    }


    /*get and set*/
    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public PedidoDetalle getPedidoDetalle() {
        return pedidoDetalle;
    }

    public void setPedidoDetalle(PedidoDetalle pedidoDetalle) {
        this.pedidoDetalle = pedidoDetalle;
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

    public List<PedidoDetalle> getListPedidoDetalle() {
        return listPedidoDetalle;
    }

    public void setListPedidoDetalle(List<PedidoDetalle> listPedidoDetalle) {
        this.listPedidoDetalle = listPedidoDetalle;
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

    public PedidoBl getPedidoBl() {
        return pedidoBl;
    }

    public void setPedidoBl(PedidoBl pedidoBl) {
        this.pedidoBl = pedidoBl;
    }

    public PedidoDetalleBl getPedidoDetalleBl() {
        return pedidoDetalleBl;
    }

    public void setPedidoDetalleBl(PedidoDetalleBl pedidoDetalleBl) {
        this.pedidoDetalleBl = pedidoDetalleBl;
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

    public PedidoSeguimiento getPedidoSeguimiento() {
        return pedidoSeguimiento;
    }

    public void setPedidoSeguimiento(PedidoSeguimiento pedidoSeguimiento) {
        this.pedidoSeguimiento = pedidoSeguimiento;
    }

    public List<SelectItem> getSelectOneItemsPedido() {
//        this.selectOneItemsPedido = new LinkedList<SelectItem>();
//        for (Pedido obj : listOrdenPedidoXEstado("APROBADO")) {
//            this.setPedido(obj);
//            SelectItem selectItem = new SelectItem(pedido.getIdpedido(), pedido.getNumero() + "");
//            this.selectOneItemsPedido.add(selectItem);
//        }
//        return selectOneItemsPedido;

        //Listar los seguimientos de pedido que se encuentren  en estado APROBADO
        listPedidoSeguimiento = new LinkedList<>();
        listPedidoSeguimiento = pedidoSeguimientoBl.listarxEstado(2);
        this.selectOneItemsPedido = new LinkedList<>();
        for (PedidoSeguimiento pedidoSeg : listPedidoSeguimiento) {
            pedido = pedidoBl.buscarXid(pedidoSeg.getPedido().getIdpedido());
            SelectItem selectItem = new SelectItem(pedido.getIdpedido(), pedido.getNumero() + "");
            this.selectOneItemsPedido.add(selectItem);
        }
        return pedidosPorEstado;
    }

    public void setSelectOneItemsPedido(List<SelectItem> selectOneItemsPedido) {
        this.selectOneItemsPedido = selectOneItemsPedido;
    }

    private List<Pedido> listOrdenPedidoXEstado(String estado) {
        return pedidoBl.listOrdenPedidoXEstado(estado);
    }

    public List<SelectItem> getSelectOneItemsEquivalencia() {
        this.selectOneItemsEquivalencia = new LinkedList<SelectItem>();
        //System.out.println("producto ..... "+producto.getDescripcion());
        if (producto != null) {
            for (Equivalencia obj : listarEquivalenciaxUnidadMedida(producto.getUnidadMedida().getIdunidadmedida())) {
                this.setEquivalencia(obj);
                SelectItem selectItem = new SelectItem(getEquivalencia().getIdequivalencia(), getEquivalencia().getUnidadEquivalente().getDescripcion());
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
        listEquivalencias = getEquivalenciaBl().listarEquivalenciaxUnidadMedida(idunidadmedida);
        return getListEquivalencias();
    }

    public List<Equivalencia> getListEquivalencias() {
        return listEquivalencias;
    }

    public void setListEquivalencias(List<Equivalencia> listEquivalencias) {
        this.listEquivalencias = listEquivalencias;
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

    private void registrarPedidoSeguimiento(Pedido pedido) {
        pedidoSeguimiento.setPedido(pedido);
        pedidoSeguimiento.setEstado(pedidoEstadoBl.buscar(1));
        pedidoSeguimiento.setFecha(new Date());
        pedidoSeguimiento.setHora(Utilitarios.horaActual());
        pedidoSeguimiento.setObservacion(pedido.getObservacion());
        pedidoSeguimiento.setNumero(pedidoSeguimientoBl.maxNumero(pedido.getIdpedido()) + 1);
        HttpSession sesionUser = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        if (sesionUser.getAttribute("idUsuario") != null) {
            pedidoSeguimiento.setIdUser(Long.parseLong(sesionUser.getAttribute("idUsuario").toString()));
        } else {
            pedidoSeguimiento.setIdUser(0);
        }

        pedidoSeguimientoBl.registrar(pedidoSeguimiento);
    }

    public List<SelectItem> getPedidosPorEstado() {
        //Listar los seguimientos de pedido que se encuentren  en estado APROBADO
        listPedidoSeguimiento = new LinkedList<>();
        listPedidoSeguimiento = pedidoSeguimientoBl.listarxEstado(3);
        selectOneItemsPedido = new LinkedList<>();
        for (PedidoSeguimiento pedidoSeg : listPedidoSeguimiento) {
            pedido = pedidoBl.buscarXid(pedidoSeg.getPedido().getIdpedido());
            SelectItem selectItem = new SelectItem(pedido.getIdpedido(), pedido.getNumero() + "");
            this.selectOneItemsPedido.add(selectItem);
        }
        return selectOneItemsPedido;
//        
//        
//        this.pedidosPorEstado = new LinkedList<SelectItem>();
//        for (Pedido obj : listOrdenPedidoXEstado("APROBADO")) {
//            System.out.println("objeto ... "+obj.getNumero());
//            this.setPedido(obj);
//            SelectItem selectItem = new SelectItem(pedido.getIdpedido(), pedido.getNumero() + "");
//            this.selectOneItemsPedido.add(selectItem);
//        }        
//        return pedidosPorEstado;
    }

    public void setPedidosPorEstado(List<SelectItem> pedidosPorEstado) {
        this.pedidosPorEstado = pedidosPorEstado;
    }

    public List<PedidoSeguimiento> getListPedidoSeguimiento() {
        return listPedidoSeguimiento;
    }

    public void setListPedidoSeguimiento(List<PedidoSeguimiento> listPedidoSeguimiento) {
        this.listPedidoSeguimiento = listPedidoSeguimiento;
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

}
