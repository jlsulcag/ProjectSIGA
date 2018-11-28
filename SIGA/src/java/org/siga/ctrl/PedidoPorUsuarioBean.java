package org.siga.ctrl;

import java.io.File;
import java.io.IOException;
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
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.siga.be.AlmacenProducto;
import org.siga.be.Equivalencia;
import org.siga.be.NotaEntrada;
import org.siga.be.NotaEntradaDetalle;
import org.siga.be.NotaSalida;
import org.siga.be.Pedido;
import org.siga.be.PedidoDetalle;
import org.siga.be.PedidoSeguimiento;
import org.siga.be.Usuario;
import org.siga.bl.AlmacenProductoBl;
import org.siga.bl.EquivalenciaBl;
import org.siga.bl.NotaIngresoBl;
import org.siga.bl.NotaIngresoDetalleBl;
import org.siga.bl.NotaSalidaBl;
import org.siga.bl.PedidoBl;
import org.siga.bl.PedidoDetalleBl;
import org.siga.bl.PedidoEstadoBl;
import org.siga.bl.PedidoSeguimientoBl;
import org.siga.ds.DSConeccion;
import org.siga.util.MensajeView;
import org.siga.util.Utilitarios;

@ManagedBean
@ViewScoped
public class PedidoPorUsuarioBean {

    @ManagedProperty(value = "#{pedidoBl}")
    private PedidoBl pedidoBl;
    @ManagedProperty(value = "#{pedido}")
    private Pedido pedido;

    @ManagedProperty(value = "#{pedidoDetalleBl}")
    private PedidoDetalleBl pedidoDetalleBl;
    @ManagedProperty(value = "#{pedidoDetalle}")
    private PedidoDetalle pedidoDetalle;

    @ManagedProperty(value = "#{pedidoSeguimiento}")
    private PedidoSeguimiento pedidoSeguimiento;
    @ManagedProperty(value = "#{pedidoSeguimientoBl}")
    private PedidoSeguimientoBl pedidoSeguimientoBl;

    @ManagedProperty(value = "#{pedidoEstadoBl}")
    private PedidoEstadoBl pedidoEstadoBl;

    @ManagedProperty(value = "#{notaIngresoBl}")
    private NotaIngresoBl notaIngresoBl;
    @ManagedProperty(value = "#{notaEntrada}")
    private NotaEntrada notaEntrada;

    @ManagedProperty(value = "#{notaSalida}")
    private NotaSalida notaSalida;
    @ManagedProperty(value = "#{notaSalidaBl}")
    private NotaSalidaBl notaSalidaBl;

    @ManagedProperty(value = "#{notaEntradaDetalle}")
    private NotaEntradaDetalle notaEntradaDetalle;
    @ManagedProperty(value = "#{notaIngresoDetalleBl}")
    private NotaIngresoDetalleBl notaIngresoDetalleBl;

    @ManagedProperty(value = "#{almacenProducto}")
    private AlmacenProducto almacenProducto;
    @ManagedProperty(value = "#{almacenProductoBl}")
    private AlmacenProductoBl almacenProductoBl;

    @ManagedProperty(value = "#{equivalencia}")
    private Equivalencia equivalencia;
    @ManagedProperty(value = "#{equivalenciaBl}")
    private EquivalenciaBl equivalenciaBl;

    private List<Pedido> listPedidos;
    private List<PedidoDetalle> listPedidoDetalles;
    private List<PedidoSeguimiento> listPedidoEstados;

    private Pedido selectedPedido;

    public PedidoPorUsuarioBean() {
    }

    @PostConstruct
    public void listarPedidos() {
        listPedidos = new LinkedList<>();
        //HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        Usuario user = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        if (user != null) {
            for (Pedido obj : pedidoBl.listarPedidoxUser(user.getIdusuario())) {
                pedidoSeguimiento = pedidoSeguimientoBl.buscarxidPedido(obj.getIdpedido());
                if (pedidoSeguimiento != null) {
                    obj.setEstado(pedidoSeguimiento.getEstado().getDescripcion());
                }
                listPedidos.add(obj);
            }
            setListPedidos(listPedidos);
        }

    }

    public void onRowSelect(SelectEvent event) {
        /*
        FacesMessage msg = new FacesMessage("Numero Pedido", ((Pedido) event.getObject()).getNumero() + "");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        */
        pedido = (Pedido) event.getObject();
        setListPedidoDetalles(pedidoDetalleBl.listarxIdPedido(((Pedido) event.getObject()).getIdpedido()));
        setListPedidoEstados(pedidoSeguimientoBl.listarxidPedido(((Pedido) event.getObject()).getIdpedido()));
    }

    public void onRowUnselect(UnselectEvent event) {
        FacesMessage msg = new FacesMessage("Car Unselected", ((Pedido) event.getObject()).getObservacion());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public String redirigir() {
        HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        httpSession.setAttribute("idPedido", getPedido().getIdpedido());
        return "PedidosporUsuarioDetalle?faces-redirect=true";

    }

    public void recepcionarPedido() {
        int resp = -1;
        if (pedido != null) {
            //Validar el estado del pedido para evitar  multiples ingresos para un mismo pedido
            if (!(pedidoSeguimientoBl.buscarxidPedido(pedido.getIdpedido())).getEstado().getDescripcion().trim().equals("CERRADO")) {
                /*Registrar la nota  de entrada  solo cuando se trate de distribucion
                 Para lo cual se debe buscar  el tipo de movimiento (CONSUMO O DISTRIBUCION), generada en la nota de salida  
                 */
                notaSalida = notaSalidaBl.buscarxIdPedido(pedido.getIdpedido());
                if (notaSalida != null) {
                    if (notaSalida.getTipomovimiento().getDescripcion().trim().equals("DISTRIBUCION")) {
                        //Registrar Nota ingreso a almacen                    
                        resp = registrarIngresoAlmacenDestino(pedido, listPedidoDetalles);
                    }

                }
                registrarMovimiento(pedido);
            }else{
                MensajeView.docEstaCerrado();
            }

        } else {
            MensajeView.seleccioneTabla();
        }
    }

    private void registrarMovimiento(Pedido pedido) {
        long res = -1;
        pedidoSeguimiento.setPedido(pedido);
        pedidoSeguimiento.setFecha(new Date());
        pedidoSeguimiento.setHora(Utilitarios.horaActual());
        pedidoSeguimiento.setNumero(pedidoSeguimientoBl.maxNumero(pedido.getIdpedido()) + 1);
        pedidoSeguimiento.setObservacion("");
        HttpSession sesionUser = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        if (sesionUser.getAttribute("idUsuario") != null) {
            pedidoSeguimiento.setIdUser(Long.parseLong(sesionUser.getAttribute("idUsuario").toString()));
        } else {
            pedidoSeguimiento.setIdUser(0);
        }
        pedidoSeguimiento.setEstado(getPedidoEstadoBl().buscarRef("CERRADO"));
        res = pedidoSeguimientoBl.registrar(pedidoSeguimiento);
        limpiar();
        if (res == 0) {
            MensajeView.recepcionarPedido();
        }
    }

    public void limpiar() {
        pedidoSeguimiento.setIdpedidoseguimiento(0);
        pedidoSeguimiento.setObservacion("");
    }

    private int registrarIngresoAlmacenDestino(Pedido pedido, List<PedidoDetalle> listPedidoDetalle) {
        int SUCESS = 1;
        int ERROR = 2;
        int retur = 0;
        long res = -1;
        int cont = 0;
        if (!listPedidoDetalle.isEmpty()) {
            res = registrarNotaEntrada(pedido);
            if (res == 0) {
                HttpSession sesion = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
                sesion.setAttribute("nealmacen", notaEntrada);
                for (PedidoDetalle obj : listPedidoDetalle) {
                    getNotaEntradaDetalle().setNotaEntrada(notaEntrada);
                    getNotaEntradaDetalle().setProducto(obj.getProducto());
                    getNotaEntradaDetalle().setLote("");
                    getNotaEntradaDetalle().setFechaVencimiento(null);
                    getNotaEntradaDetalle().setUnidadMedida(obj.getUnidadMedida());
                    getNotaEntradaDetalle().setCantSolicitada(obj.getCantidadSolicitada());
                    getNotaEntradaDetalle().setCantRecibida(obj.getCantidadSurtida());
                    getNotaEntradaDetalle().setCantPendiente(0);
                    getNotaEntradaDetalle().setCantIngreso(obj.getCantidadSurtida());
                    getNotaEntradaDetalle().setIdEquivalencia(obj.getIdEquivalencia());

                    getNotaIngresoDetalleBl().registrar(getNotaEntradaDetalle());
                    cont++;
                    long resp = registrarStockAlmacen(getNotaEntradaDetalle());
                    if (cont == listPedidoDetalle.size()) {
                        if (resp != 0) {
                            retur = SUCESS;
                        } else {
                            retur = ERROR;
                        }
                    }
                }

            }
        }
        return retur;
    }

    private long registrarStockAlmacen(NotaEntradaDetalle obj) {
        getAlmacenProducto().setProducto(obj.getProducto());
        getAlmacenProducto().setProducto(obj.getProducto());
        getAlmacenProducto().setAlmacen(obj.getNotaEntrada().getAlmacenDestino());
        getAlmacenProducto().setLote(obj.getLote());
        getAlmacenProducto().setFechaVencimiento(obj.getFechaVencimiento());
        getAlmacenProducto().setValorCompraUnitario(obj.getValorCompra());
        getAlmacenProducto().setIdEquivalencia(obj.getIdEquivalencia());
        getAlmacenProducto().setFechaIngreso(new Date());
        //registrar el orden de ingreso para cumplir con FIFO
        int numOrden = getAlmacenProductoBl().obtenerUltimoNumero(obj.getProducto().getIdproducto());
        getAlmacenProducto().setOrdenIngreso(numOrden + 1);
        getAlmacenProducto().setUnidad(obj.getUnidadMedida());
        //Obtener factor de equivalencia
        setEquivalencia(getEquivalenciaBl().buscaxId(obj.getIdEquivalencia()));
        getAlmacenProducto().setStockActual((int) (obj.getCantIngreso() * getEquivalencia().getFactor()));

        getAlmacenProductoBl().registrar(getAlmacenProducto());
        return getAlmacenProducto().getIdalmacenproducto();

    }

    private long registrarNotaEntrada(Pedido pedido) {
        Usuario user = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        notaEntrada.setNumero(notaIngresoBl.buscarUltimoNumero() + 1);
        notaEntrada.setFechaReg(new Date());
        notaEntrada.setOrdenCompra(null);
        notaEntrada.setAlmacenDestino(pedido.getDependencia().getAlmacen());
        notaEntrada.setIdUserReg(user != null ? user.getIdusuario() : 0);
        notaEntrada.setObservacion("");
        notaEntrada.setTipoIngreso("POR PEDIDO");
        notaEntrada.setNroDocref(pedido.getNumero() + "");
        notaEntrada.setFechaDocref(pedido.getFechaPedido());
        notaEntrada.setProveedor(null);
        notaEntrada.setPedido(pedido != null ? pedido : null);
        return notaIngresoBl.registrar(notaEntrada);

    }

    public void visualizarNotaEntrada() {
        notaEntrada = notaIngresoBl.buscarxIdPedido(pedido.getIdpedido());
        if (notaEntrada != null) {
            try {
                Map<String, Object> parametro = new HashMap<>();
                File file = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/WEB-INF/classes/org/siga/reportes/REP-0006-nota-entrada.jasper"));
                DSConeccion ds = new DSConeccion("192.168.32.33", "5432", "sigadb_desa", "siga%admin", "siga%admin");
                parametro.put("ID_ENTRADA", notaEntrada.getIdnotaentrada());
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
            } catch (JRException ex) {
                Logger.getLogger(PedidoPorUsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(PedidoPorUsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(PedidoPorUsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            MensajeView.noExisteNotaEntrada(pedido.getNumero());
        }
    }

    /*Metodos de acceso y seteo*/
    public PedidoBl getPedidoBl() {
        return pedidoBl;
    }

    public void setPedidoBl(PedidoBl pedidoBl) {
        this.pedidoBl = pedidoBl;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public PedidoDetalleBl getPedidoDetalleBl() {
        return pedidoDetalleBl;
    }

    public void setPedidoDetalleBl(PedidoDetalleBl pedidoDetalleBl) {
        this.pedidoDetalleBl = pedidoDetalleBl;
    }

    public PedidoDetalle getPedidoDetalle() {
        return pedidoDetalle;
    }

    public void setPedidoDetalle(PedidoDetalle pedidoDetalle) {
        this.pedidoDetalle = pedidoDetalle;
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

    public List<Pedido> getListPedidos() {
        return listPedidos;
    }

    public void setListPedidos(List<Pedido> listPedidos) {
        this.listPedidos = listPedidos;
    }

    public Pedido getSelectedPedido() {
        return selectedPedido;
    }

    public void setSelectedPedido(Pedido selectedPedido) {
        this.selectedPedido = selectedPedido;
    }

    public List<PedidoDetalle> getListPedidoDetalles() {
        return listPedidoDetalles;
    }

    public void setListPedidoDetalles(List<PedidoDetalle> listPedidoDetalles) {
        this.listPedidoDetalles = listPedidoDetalles;
    }

    public List<PedidoSeguimiento> getListPedidoEstados() {
        return listPedidoEstados;
    }

    public void setListPedidoEstados(List<PedidoSeguimiento> listPedidoEstados) {
        this.listPedidoEstados = listPedidoEstados;
    }

    public NotaIngresoBl getNotaIngresoBl() {
        return notaIngresoBl;
    }

    public void setNotaIngresoBl(NotaIngresoBl notaIngresoBl) {
        this.notaIngresoBl = notaIngresoBl;
    }

    public NotaEntrada getNotaEntrada() {
        return notaEntrada;
    }

    public void setNotaEntrada(NotaEntrada notaEntrada) {
        this.notaEntrada = notaEntrada;
    }

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

    public PedidoEstadoBl getPedidoEstadoBl() {
        return pedidoEstadoBl;
    }

    public void setPedidoEstadoBl(PedidoEstadoBl pedidoEstadoBl) {
        this.pedidoEstadoBl = pedidoEstadoBl;
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
