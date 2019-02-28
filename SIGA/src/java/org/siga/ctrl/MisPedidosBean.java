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
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.servlet.ServletContext;
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
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
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
import org.siga.be.Rol;
import org.siga.be.Usuario;
import org.siga.be.UsuarioRol;
import org.siga.bl.AlmacenProductoBl;
import org.siga.bl.EquivalenciaBl;
import org.siga.bl.NotaIngresoBl;
import org.siga.bl.NotaIngresoDetalleBl;
import org.siga.bl.NotaSalidaBl;
import org.siga.bl.PedidoBl;
import org.siga.bl.PedidoDetalleBl;
import org.siga.bl.PedidoEstadoBl;
import org.siga.bl.PedidoSeguimientoBl;
import org.siga.bl.RolBl;
import org.siga.bl.UsuarioRolBl;
import org.siga.ds.DSConeccion;
import org.siga.util.MensajeView;
import org.siga.util.Utilitarios;
import org.siga.util.Variables;

@ManagedBean
@ViewScoped
public class MisPedidosBean {

    @ManagedProperty(value = "#{pedidoBl}")
    private PedidoBl pedidoBl;
    @ManagedProperty(value = "#{pedido}")
    private Pedido pedido;

    private Pedido selectedPedido;
    private PedidoDetalle temp;
    private PedidoDetalle selectPedidoDetalle;

    private List<Pedido> listPedidos;
    private List<Pedido> listPedidosTemp;

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

    @ManagedProperty(value = "#{usuario}")
    private Usuario usuario;

    @ManagedProperty(value = "#{notaSalida}")
    private NotaSalida notaSalida;
    @ManagedProperty(value = "#{notaSalidaBl}")
    private NotaSalidaBl notaSalidaBl;

    @ManagedProperty(value = "#{notaIngresoBl}")
    private NotaIngresoBl notaIngresoBl;
    @ManagedProperty(value = "#{notaEntrada}")
    private NotaEntrada notaEntrada;

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

    private PedidoDetalle selectedPedidoDetalle;
    private List<PedidoDetalle> listPedidoDetalles;

    //@ManagedProperty(value = "#{rol}")
    //private Rol rol; 
    private String rol;

    private List<PedidoDetalle> listPedidoDetalle;
    private List<PedidoSeguimiento> listPedidoEstados;

    private long res;

    public MisPedidosBean() {
    }

    @PostConstruct
    public void listarPedidos() {
        //obtener valore sde usuario y rol para determinar que fragmento de pagina  se abrirá
        rol = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("rol");
        listPedidos = new LinkedList<>();
        if (rol.equals("USUARIO")) {
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
        } else {
            for (Pedido obj : pedidoBl.listarFull("")) {
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
        //FacesMessage msg = new FacesMessage("Car Selected", ((Pedido)event.getObject()).getObservacion());
        //FacesContext.getCurrentInstance().addMessage(null, msg);
        pedido = (Pedido) event.getObject();
        setListPedidoDetalle(pedidoDetalleBl.listarxIdPedido(pedido.getIdpedido()));
        setListPedidoEstados(pedidoSeguimientoBl.listarxidPedido(pedido.getIdpedido()));
    }

    public void onRowUnselect(UnselectEvent event) {
        FacesMessage msg = new FacesMessage("Car Unselected", ((Pedido) event.getObject()).getObservacion());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowEdit(RowEditEvent event) {
        System.out.println("ingreso al metodo ..........");
        selectedPedido = new Pedido();
        selectedPedido.setIdpedido(((Pedido) event.getObject()).getIdpedido());
        System.out.println("id pedido .. " + selectedPedido.getIdpedido());
//        temp = new PedidoDetalle();
//        temp = (PedidoDetalle) event.getObject();
//        System.out.println("pedido detalle ... "+temp.getCantidadSolicitada());
//        String msg = "";
//        temp.setIdpedidodetalle(((PedidoDetalle) event.getObject()).getIdpedidodetalle());
//        System.out.println("ID enviado "+temp.getIdpedidodetalle());
//        PedidoDetalle temp2 = new PedidoDetalle();
//        temp2 = pedidoDetalleBl.buscar(temp.getIdpedidodetalle());
//        System.out.println("Estado modificado..." + getPedidoDetalle().getIdpedidodetalle());
//        temp2.setCantidadAutorizada(getPedidoDetalle().getCantidadAutorizada());
    }

    public void onRowEditDetalle(RowEditEvent event) {
        selectedPedidoDetalle = new PedidoDetalle();
        selectedPedidoDetalle = pedidoDetalleBl.buscarxID(((PedidoDetalle) event.getObject()).getIdpedidodetalle());
        selectedPedidoDetalle.setCantidadAutorizada(pedidoDetalle.getCantidadAutorizada());
        selectedPedidoDetalle.setCantidadSurtida(pedidoDetalle.getCantidadAutorizada());
        pedidoDetalleBl.actualizar(selectedPedidoDetalle);
        listarPedidoDetalle();
    }

    public void onRowCancelDetalle(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edicion cancelada", null);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void listarPedidoDetalle() {
        if (pedido != null) {
            setListPedidoDetalle(pedidoDetalleBl.listarxIdPedido(pedido.getIdpedido()));
        }
    }

    public String redirigir() {
        String url = "";
        HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        httpSession.setAttribute("idPedido", getPedido().getIdpedido());
        switch (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("rol").toString()) {
            case "ALMACENERO":
                url = "PedidoDetalle";
                break;
            case "JEFE LOGISTICA":
                url = "PedidoDetalleAdmin";
                break;
        }
        return url + "?faces-redirect=true";

    }

    public void actualizarPedidoDetalle() {
        PedidoDetalle temp = new PedidoDetalle();
        temp = buscarId();
        temp.setCantidadAutorizada(pedidoDetalle.getCantidadAutorizada());

        res = pedidoDetalleBl.actualizar(temp);
        if (res == 0) {
            MensajeView.actCorrecto();
        } else {
            MensajeView.actError();
        }
        //listar();
    }

    private PedidoDetalle buscarId() {
        return pedidoDetalleBl.buscarxID(pedidoDetalle.getIdpedidodetalle());
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edicion cancelada", null);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
        PedidoDetalle old = (PedidoDetalle) event.getOldValue();
        System.out.println("valor antiguo........... " + oldValue);
        System.out.println("obj antiguo........... " + old.getCantidadAutorizada());

        System.out.println("valor nuevo........... " + newValue);

        //pedidoDetalle = (PedidoDetalle) ((DataTable)event.getComponent()).getRowData();
        System.out.println("valor nuevo........... " + pedidoDetalle.getCantidadAutorizada());

        //System.out.println("valor nuevo........... "+selectPedidoDetalle.getCantidadAutorizada());
        if (newValue != null && !newValue.equals(oldValue)) {
            System.out.println("dentro del if .................");
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void recepcionarPedido() {
        int resp = -1;
        System.out.println("pedido id --..... "+pedido.getIdpedido());
        if (pedido != null) {
            
            pedidoSeguimiento = pedidoSeguimientoBl.buscarxidPedido(pedido.getIdpedido());
            //Validar el estado del pedido para evitar  multiples ingresos para un mismo pedido
            if (pedidoSeguimiento.getEstado().getDescripcion().trim().equals("ATENDIDO")) {
                /*Registrar la nota  de entrada  solo cuando se trate de distribucion
                 Para lo cual se debe buscar  el tipo de movimiento (CONSUMO O DISTRIBUCION), generada en la nota de salida  
                 */
                setNotaSalida(getNotaSalidaBl().buscarxIdPedido(pedido.getIdpedido()));
                if (getNotaSalida() != null) {
                    if (getNotaSalida().getTipomovimiento().getDescripcion().trim().equals("DISTRIBUCION")) {
                        //Registrar Nota ingreso a almacen                    
                        resp = registrarIngresoAlmacenDestino(pedido, getListPedidoDetalles());
                    }

                }
                registrarMovimiento(pedido);
            } else {
                MensajeView.pedidoEstado("ATENDIDO");
            }

        } else {
            MensajeView.seleccioneTabla();
        }
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
                sesion.setAttribute("nealmacen", getNotaEntrada());
                for (PedidoDetalle obj : listPedidoDetalle) {
                    getNotaEntradaDetalle().setNotaEntrada(getNotaEntrada());
                    getNotaEntradaDetalle().setProducto(obj.getProducto());
                    getNotaEntradaDetalle().setLote("");
                    getNotaEntradaDetalle().setFechaVencimiento(null);
                    getNotaEntradaDetalle().setUnidadMedida(obj.getUnidadMedida());
                    getNotaEntradaDetalle().setCantSolicitada(obj.getCantidadSolicitada());
                    getNotaEntradaDetalle().setCantRecibida(obj.getCantidadSurtida());
                    getNotaEntradaDetalle().setCantPendiente(BigDecimal.ZERO);
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

    private long registrarNotaEntrada(Pedido pedido) {
        Usuario user = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        getNotaEntrada().setNumero(getNotaIngresoBl().buscarUltimoNumero() + 1);
        getNotaEntrada().setFechaReg(new Date());
        getNotaEntrada().setOrdenCompra(null);
        getNotaEntrada().setAlmacenDestino(pedido.getDependencia().getAlmacen());
        getNotaEntrada().setIdUserReg(user != null ? user.getIdusuario() : 0);
        getNotaEntrada().setObservacion("");
        getNotaEntrada().setTipoIngreso("POR PEDIDO");
        getNotaEntrada().setNroDocref(pedido.getNumero() + "");
        getNotaEntrada().setFechaDocref(pedido.getFechaPedido());
        getNotaEntrada().setProveedor(null);
        getNotaEntrada().setPedido(pedido != null ? pedido : null);
        return getNotaIngresoBl().registrar(getNotaEntrada());

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
        if (res == 0) {
            MensajeView.recepcionarPedido();
        }
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
        getAlmacenProducto().setStockActual((obj.getCantIngreso().multiply(new BigDecimal(getEquivalencia().getFactor()))));

        getAlmacenProductoBl().registrar(getAlmacenProducto());
        return getAlmacenProducto().getIdalmacenproducto();

    }

    public void verificarPedido() {
        if (pedido != null) {
            pedidoSeguimiento = pedidoSeguimientoBl.buscarxidPedido(pedido.getIdpedido());
            if (pedidoSeguimiento.getEstado().getDescripcion().trim().equals("REGISTRADO")) {
                pedidoSeguimiento.setPedido(pedido);
                pedidoSeguimiento.setEstado(pedidoEstadoBl.buscar(2));
                pedidoSeguimiento.setObservacion("");
                pedidoSeguimiento.setFecha(new Date());
                pedidoSeguimiento.setHora(Utilitarios.horaActual());
                pedidoSeguimiento.setNumero(pedidoSeguimientoBl.maxNumero(pedido.getIdpedido()) + 1);
                HttpSession sesionUser = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
                if (sesionUser.getAttribute("idUsuario") != null) {
                    pedidoSeguimiento.setIdUser(Long.parseLong(sesionUser.getAttribute("idUsuario").toString()));
                } else {
                    pedidoSeguimiento.setIdUser(0);
                }
                res = pedidoSeguimientoBl.registrar(pedidoSeguimiento);
                if (res == 0) {
                    MensajeView.verificarPedido();
                    listarPedidos();
                }
            } else {
                MensajeView.pedidoEstado("REGISTRADO");
            }
        }
    }

    public void aprobarPedido() {
        if (pedido != null) {
            pedidoSeguimiento = pedidoSeguimientoBl.buscarxidPedido(pedido.getIdpedido());
            if (pedidoSeguimiento.getEstado().getDescripcion().trim().equals("VERIFICADO")) {
                pedidoSeguimiento.setPedido(pedido);
                pedidoSeguimiento.setEstado(pedidoEstadoBl.buscar(3));
                pedidoSeguimiento.setObservacion("");
                pedidoSeguimiento.setFecha(new Date());
                pedidoSeguimiento.setHora(Utilitarios.horaActual());
                pedidoSeguimiento.setNumero(pedidoSeguimientoBl.maxNumero(pedido.getIdpedido()) + 1);
                HttpSession sesionUser = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
                if (sesionUser.getAttribute("idUsuario") != null) {
                    pedidoSeguimiento.setIdUser(Long.parseLong(sesionUser.getAttribute("idUsuario").toString()));
                } else {
                    pedidoSeguimiento.setIdUser(0);
                }
                res = pedidoSeguimientoBl.registrar(pedidoSeguimiento);
                if (res == 0) {
                    MensajeView.pedidoAprobado();
                    listarPedidos();
                }
            } else {
                MensajeView.pedidoEstado("VERIFICADO");
            }
        }
    }

     //metodo  que ubica la raiz de la  app
    public static String getPath() {
        try {
            ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            return ctx.getRealPath("/");
        } catch (Exception e) {
            MensajeView.errorFatal(e);
            return null;
        }

    }

    public void descargarPedido() {
        try {
            if (pedido != null) {
                pedidoSeguimiento = pedidoSeguimientoBl.buscarxidPedido(pedido.getIdpedido());
                if (pedidoSeguimiento.getEstado().getDescripcion().trim().equals("APROBADO")) {
                    //Mapa de parametros
                    Map<String, Object> parametro = new HashMap<>();
                    //
                    File file = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/WEB-INF/classes/org/siga/reportes/REP-0002-nota-pedido.jasper"));
                    //DSConeccion ds = new DSConeccion("192.168.32.33", "5432", "sigadb_desa", "siga%admin", "siga%admin");
                    DSConeccion ds = new DSConeccion(Variables.HOST, Variables.PORT, Variables.DB, Variables.USER, Variables.PASS);
                    //DsConexion ds = new DsConexion();
                    //ruta de logo
                    String path = getPath()+ "/resources/img/logo.png";
                    parametro.put("ID_PEDIDO", pedido.getIdpedido());
                    parametro.put("P_RUTAIMAGEN", path);
                    JasperReport reporte = (JasperReport) JRLoader.loadObjectFromFile(file.getPath());
                    JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parametro, ds.getConeccion());

                    HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
                    response.addHeader("Content-disposition", "attachment; filename=Pedido_" + Utilitarios.numberFormat(pedido.getNumero(), "########") + ".pdf");
                    ServletOutputStream stream = response.getOutputStream();

                    JasperExportManager.exportReportToPdfStream(jasperPrint, stream);

                    stream.flush();
                    stream.close();
                    FacesContext.getCurrentInstance().responseComplete();
                } else {
                    MensajeView.pedidoEstado("APROBADO");
                }

            } else {

            }

        } catch (JRException ex) {
            Logger.getLogger(OrdenCompraBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(OrdenCompraBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void descargarNotaEntradaSucursal() {
        notaEntrada = notaIngresoBl.buscarxIdPedido(pedido.getIdpedido());
        if (notaEntrada != null) {
            try {
                Map<String, Object> parametro = new HashMap<>();
                File file = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/WEB-INF/classes/org/siga/reportes/REP-0006-nota-entrada.jasper"));
                DSConeccion ds = new DSConeccion("192.168.32.33", "5432", "sigadb_desa", "siga%admin", "siga%admin");
                parametro.put("ID_ENTRADA", notaEntrada.getIdnotaentrada());
                
                JasperReport reporte = (JasperReport) JRLoader.loadObjectFromFile(file.getPath());
                JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parametro, ds.getConeccion());
                
                HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
                response.addHeader("Content-disposition", "attachment; filename=Nota_Entrada_" + Utilitarios.numberFormat(pedido.getNumero(), "########") + ".pdf");
                ServletOutputStream stream = response.getOutputStream();
                
                JasperExportManager.exportReportToPdfStream(jasperPrint, stream);
                
                stream.flush();
                stream.close();
                FacesContext.getCurrentInstance().responseComplete();
            } catch (JRException | IOException ex) {
                Logger.getLogger(MisPedidosBean.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            MensajeView.noExisteNotaEntrada(pedido.getNumero());
        }

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

    public List<Pedido> getListPedidos() {
        return listPedidos;
    }

    public void setListPedidos(List<Pedido> listPedidos) {
        this.listPedidos = listPedidos;
    }

    public List<Pedido> getListPedidosTemp() {
        return listPedidosTemp;
    }

    public void setListPedidosTemp(List<Pedido> listPedidosTemp) {
        this.listPedidosTemp = listPedidosTemp;
    }

    public Pedido getSelectedPedido() {
        return selectedPedido;
    }

    public void setSelectedPedido(Pedido selectedPedido) {
        this.selectedPedido = selectedPedido;
    }

    public List<PedidoDetalle> getListPedidoDetalle() {
        return listPedidoDetalle;
    }

    public void setListPedidoDetalle(List<PedidoDetalle> listPedidoDetalle) {
        this.listPedidoDetalle = listPedidoDetalle;
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

    public PedidoDetalle getSelectPedidoDetalle() {
        return selectPedidoDetalle;
    }

    public void setSelectPedidoDetalle(PedidoDetalle selectPedidoDetalle) {
        this.selectPedidoDetalle = selectPedidoDetalle;
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

    public List<PedidoSeguimiento> getListPedidoEstados() {
        return listPedidoEstados;
    }

    public void setListPedidoEstados(List<PedidoSeguimiento> listPedidoEstados) {
        this.listPedidoEstados = listPedidoEstados;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
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

    public List<PedidoDetalle> getListPedidoDetalles() {
        return listPedidoDetalles;
    }

    public void setListPedidoDetalles(List<PedidoDetalle> listPedidoDetalles) {
        this.listPedidoDetalles = listPedidoDetalles;
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

}
