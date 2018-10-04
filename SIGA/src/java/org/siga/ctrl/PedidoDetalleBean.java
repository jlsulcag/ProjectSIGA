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
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.util.JRLoader;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.siga.be.AlmacenProducto;
import org.siga.be.Equivalencia;
import org.siga.be.NotaEntrada;
import org.siga.be.NotaEntradaDetalle;
import org.siga.be.NotaSalida;
import org.siga.be.Pedido;
import org.siga.be.PedidoDetalle;
import org.siga.be.PedidoSeguimiento;
import org.siga.be.Permiso;
import org.siga.be.Producto;
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
import org.siga.bl.PermisoBl;
import org.siga.ds.DSConeccion;
import org.siga.util.MensajeView;
import org.siga.util.Utilitarios;

@ManagedBean
@ViewScoped
public class PedidoDetalleBean {

    @ManagedProperty(value = "#{pedidoBl}")
    private PedidoBl pedidoBl;
    @ManagedProperty(value = "#{pedido}")
    private Pedido pedido;
    @ManagedProperty(value = "#{pedidoDetalle}")
    private PedidoDetalle pedidoDetalle;
    @ManagedProperty(value = "#{pedidoDetalleBl}")
    private PedidoDetalleBl pedidoDetalleBl;
    @ManagedProperty(value = "#{producto}")
    private Producto producto;

    @ManagedProperty(value = "#{pedidoSeguimientoBl}")
    private PedidoSeguimientoBl pedidoSeguimientoBl;
    @ManagedProperty(value = "#{pedidoSeguimiento}")
    private PedidoSeguimiento pedidoSeguimiento;

    @ManagedProperty(value = "#{pedidoEstadoBl}")
    private PedidoEstadoBl pedidoEstadoBl;

    @ManagedProperty(value = "#{notaEntrada}")
    private NotaEntrada notaEntrada;
    @ManagedProperty(value = "#{notaIngresoBl}")
    private NotaIngresoBl notaIngresoBl;

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

    @ManagedProperty(value = "#{notaSalida}")
    private NotaSalida notaSalida;
    @ManagedProperty(value = "#{notaSalidaBl}")
    private NotaSalidaBl notaSalidaBl;

    private PedidoDetalle selectedPedidoDetalle;
    private int cantAutorizada;
    private List<PedidoDetalle> listPedidoDetalle = new LinkedList<>();
    private List<Permiso> listPermisos;

    public PedidoDetalleBean() {
    }

    public void agregar() {
        PedidoDetalle temp = new PedidoDetalle();

        HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        if (httpSession.getAttribute("idPedido") != null) {
            temp.setPedido(getPedidoBl().buscar(Long.parseLong(httpSession.getAttribute("idPedido").toString())));
        }
        temp.setProducto(producto);
        temp.setCantidadSolicitada(pedidoDetalle.getCantidadSolicitada());
        temp.setCantidadAutorizada(pedidoDetalle.getCantidadAutorizada());
        temp.setCantidadSurtida(pedidoDetalle.getCantidadSurtida());

        getListPedidoDetalle().add(temp);
    }

    @PostConstruct
    public void listar() {
        HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        if (httpSession.getAttribute("idPedido") != null) {
            setListPedidoDetalle(pedidoDetalleBl.listarxIdPedido(Long.parseLong(httpSession.getAttribute("idPedido").toString())));
        }
    }

    public void onRowEdit(RowEditEvent event) {
        selectedPedidoDetalle = new PedidoDetalle();
        selectedPedidoDetalle = pedidoDetalleBl.buscarxID(((PedidoDetalle) event.getObject()).getIdpedidodetalle());
        selectedPedidoDetalle.setCantidadAutorizada(pedidoDetalle.getCantidadAutorizada());
        selectedPedidoDetalle.setCantidadSurtida(pedidoDetalle.getCantidadAutorizada());
        pedidoDetalleBl.actualizar(selectedPedidoDetalle);
        listar();

    }

    public void actualizarPedido() {
        long res = -1;
        HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        if (httpSession.getAttribute("idPedido") != null) {
            pedido = pedidoBl.buscarXid(Long.parseLong(httpSession.getAttribute("idPedido").toString()));
            pedidoSeguimiento.setPedido(pedido);
            pedidoSeguimiento.setFecha(new Date());
            pedidoSeguimiento.setHora(Utilitarios.horaActual());
            pedidoSeguimiento.setNumero(pedidoSeguimientoBl.maxNumero(pedido.getIdpedido()) + 1);
            pedidoSeguimiento.setObservacion(pedidoSeguimiento.getObservacion().toUpperCase().trim());
            HttpSession sesionUser = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            if (sesionUser.getAttribute("idUsuario") != null) {
                pedidoSeguimiento.setIdUser(Long.parseLong(sesionUser.getAttribute("idUsuario").toString()));
            } else {
                pedidoSeguimiento.setIdUser(0);
            }
            res = pedidoSeguimientoBl.registrar(pedidoSeguimiento);
            limpiar();
            if (res == 0) {
                MensajeView.autorizarPedido();
            }
        }
    }

    public void recepcionarPedido() {
        int resp = -1;
        HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        if (httpSession.getAttribute("idPedido") != null) {
            pedido = pedidoBl.buscarXid(Long.parseLong(httpSession.getAttribute("idPedido").toString()));
            /*Registrar la nota  de entrada  solo cuando se trate de distribucion
             Para lo cual se debe buscar  el tipo de movimiento (CONSUMO O DISTRIBUCION), generada en la nota de salida  
             */
            notaSalida = notaSalidaBl.buscarxIdPedido(pedido.getIdpedido());
            if (notaSalida != null) {
                if (notaSalida.getTipomovimiento().getDescripcion().trim().equals("DISTRIBUCION")) {
                    //Registrar Nota ingreso a almacen                    
                    resp = registrarIngresoAlmacenDestino(pedido, listPedidoDetalle);
                }

            }
            //  
            registrarMovimiento(pedido);

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
        pedidoSeguimiento.setEstado(pedidoEstadoBl.buscarRef("CERRADO"));
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

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edicion cancelada", null);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
        System.out.println("valor antiguo........... " + oldValue);
        System.out.println("valor nuevo........... " + newValue);
        if (newValue != null && !newValue.equals(oldValue)) {

        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void onRowSelect(SelectEvent event) {
        System.out.println("pedido detalle seleccionado " + ((PedidoDetalle) event.getObject()).getIdpedidodetalle());
        //FacesMessage msg = new FacesMessage("Car Selected", ((Car) event.getObject()).getId());
        //FacesContext.getCurrentInstance().addMessage(null, msg);
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
                    notaEntradaDetalle.setNotaEntrada(notaEntrada);
                    notaEntradaDetalle.setProducto(obj.getProducto());
                    notaEntradaDetalle.setLote("");
                    notaEntradaDetalle.setFechaVencimiento(null);
                    notaEntradaDetalle.setUnidadMedida(obj.getUnidadMedida());
                    notaEntradaDetalle.setCantSolicitada(obj.getCantidadSolicitada());
                    notaEntradaDetalle.setCantRecibida(obj.getCantidadSurtida());
                    notaEntradaDetalle.setCantPendiente(0);
                    notaEntradaDetalle.setCantIngreso(obj.getCantidadSurtida());
                    notaEntradaDetalle.setIdEquivalencia(obj.getIdEquivalencia());

                    notaIngresoDetalleBl.registrar(notaEntradaDetalle);
                    cont++;
                    long resp = registrarStockAlmacen(notaEntradaDetalle);
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
        return notaIngresoBl.registrar(notaEntrada);

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
        equivalencia = equivalenciaBl.buscaxId(obj.getIdEquivalencia());
        getAlmacenProducto().setStockActual((int) (obj.getCantIngreso() * equivalencia.getFactor()));

        getAlmacenProductoBl().registrar(getAlmacenProducto());
        return getAlmacenProducto().getIdalmacenproducto();

    }

    public void visualizarNotaEntrada() {
        NotaEntrada temp = new NotaEntrada();
        HttpSession sesion = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        temp = (NotaEntrada) sesion.getAttribute("nealmacen");
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

    public void descargarNotaEntrada() {
        try {
            //Mapa de parametros
            System.out.println("entra al  reporte ................");
            Map<String, Object> parametro = new HashMap<>();
            //
            File file = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/WEB-INF/classes/org/siga/reportes/REP-0006-nota-entrada.jasper"));
            DSConeccion ds = new DSConeccion("192.168.32.33", "5432", "sigadb_desa", "siga%admin", "siga%admin");
            //parametro.put("ID_ENTRADA", temp.getIdnotaentrada());
            //DsConexion ds = new DsConexion();
            //System.out.println("orden de compra ID = ........ " + ordenCompra.getIdordencompra());
            //parametro.put("ID_ORDEN_COMPRA", ordenCompra.getIdordencompra());
            //File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("REP-0003-orden_compra.jasper"));
            System.out.println("path......... " + file.getPath());
            JasperReport reporte = (JasperReport) JRLoader.loadObjectFromFile(file.getPath());
            JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parametro, ds.getConeccion());

            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.addHeader("Content-disposition", "attachment; filename=OrdenCompra.pdf");
            ServletOutputStream stream = response.getOutputStream();

            JasperExportManager.exportReportToPdfStream(jasperPrint, stream);

            stream.flush();
            stream.close();
            FacesContext.getCurrentInstance().responseComplete();

        } catch (JRException ex) {
            Logger.getLogger(OrdenCompraBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(OrdenCompraBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // getter and setter
    public PedidoBl getPedidoBl() {
        return pedidoBl;
    }

    public void setPedidoBl(PedidoBl pedidoBl) {
        this.pedidoBl = pedidoBl;
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

    public List<PedidoDetalle> getListPedidoDetalle() {
        return listPedidoDetalle;
    }

    public void setListPedidoDetalle(List<PedidoDetalle> listPedidoDetalle) {
        this.listPedidoDetalle = listPedidoDetalle;
    }

    //-----
    public PedidoDetalleBl getPedidoDetalleBl() {
        return pedidoDetalleBl;
    }

    public void setPedidoDetalleBl(PedidoDetalleBl pedidoDetalleBl) {
        this.pedidoDetalleBl = pedidoDetalleBl;
    }

    public PedidoDetalle getSelectedPedidoDetalle() {
        return selectedPedidoDetalle;
    }

    public void setSelectedPedidoDetalle(PedidoDetalle selectedPedidoDetalle) {
        this.selectedPedidoDetalle = selectedPedidoDetalle;
    }

    public int getCantAutorizada() {
        return cantAutorizada;
    }

    public void setCantAutorizada(int cantAutorizada) {
        this.cantAutorizada = cantAutorizada;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public PedidoSeguimientoBl getPedidoSeguimientoBl() {
        return pedidoSeguimientoBl;
    }

    public void setPedidoSeguimientoBl(PedidoSeguimientoBl pedidoSeguimientoBl) {
        this.pedidoSeguimientoBl = pedidoSeguimientoBl;
    }

    public PedidoSeguimiento getPedidoSeguimiento() {
        return pedidoSeguimiento;
    }

    public void setPedidoSeguimiento(PedidoSeguimiento pedidoSeguimiento) {
        this.pedidoSeguimiento = pedidoSeguimiento;
    }

    public PedidoEstadoBl getPedidoEstadoBl() {
        return pedidoEstadoBl;
    }

    public void setPedidoEstadoBl(PedidoEstadoBl pedidoEstadoBl) {
        this.pedidoEstadoBl = pedidoEstadoBl;
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

}
