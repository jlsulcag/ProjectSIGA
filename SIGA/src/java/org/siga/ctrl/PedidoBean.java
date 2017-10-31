package org.siga.ctrl;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.hibernate.HibernateException;
import org.siga.be.Almacen;
import org.siga.be.Dependencia;
import org.siga.be.Pedido;
import org.siga.be.PedidoDetalle;
import org.siga.be.Producto;
import org.siga.be.Usuario;
import org.siga.bl.PedidoBl;
import org.siga.bl.PedidoDetalleBl;
import org.siga.bl.ProductoBl;
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

    private List<PedidoDetalle> listPedidoDetalle = new LinkedList<>();
    private boolean pedidoxUnidad;
    private int totalProductos;

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
    }

    private long maxNumero() {
        return pedidoBl.buscarMaxNumero();
    }

    public void agregar() {
        PedidoDetalle temp = new PedidoDetalle();
        temp.setProducto(producto);
        temp.setCantidadSolicitada(pedidoDetalle.getCantidadSolicitada());
        System.out.println("cantidad " + temp.getCantidadSolicitada());
//        temp.setCantidadSurtida(pedidoDetalle.getCantidadSurtida());
//        temp.setCantidadAutorizada(pedidoDetalle.getCantidadAutorizada());
        if (pedidoxUnidad) {
            temp.setUnidadMedida("UNIDAD");
        } else {
            temp.setUnidadMedida(producto.getUnidadMedida().getDescripcion());
        }
        listPedidoDetalle.add(temp);

    }

    public void registrar() {
        //REGISTRAR PEDIDO
        long id = -1;
        long id2 = -1;
        if (!listPedidoDetalle.isEmpty()) {
            id = registrarPedido();
            if (id > 0) {
                id2 = registrarPedidoDetalle();
                if (id2 > 0) {
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
        pedido.setEstado("NO ATENDIDO");
        pedido.setHoraPedido(Utilitarios.horaActual());
        pedidoBl.registrar(pedido);
        return pedido.getIdpedido();
    }

    private long registrarPedidoDetalle() {
        long id = -1;
        for (PedidoDetalle obj : listPedidoDetalle) {
            obj.setPedido(pedido);
            pedidoDetalleBl.registrar(obj);
            id = obj.getIdpedidodetalle();
        }
        return id;
    }

    public void inicioNew() {
        pedidoDetalle.setProducto(new Producto());
        producto.setFraccion(0);
        producto.setUnidadMedida(null);
        setPedidoxUnidad(false);
        pedidoDetalle.setCantidadSolicitada(0);
        setTotalProductos(0);
    }

    public void buscarProducto() {
        producto = getProductoBl().buscarxID(pedidoDetalle.getProducto().getIdproducto());
    }

    public void setIsPedidoUnitario() {
        setPedidoxUnidad(pedidoxUnidad);
        if (pedidoDetalle.getCantidadSolicitada() > 0) {
            calcularTotalProductos();
        }
    }

    public void calcularTotalProductos() {
        if (pedidoxUnidad) {

            setTotalProductos(pedidoDetalle.getCantidadSolicitada());
        } else {
            setTotalProductos(pedidoDetalle.getCantidadSolicitada() * producto.getFraccion());
        }
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

}
