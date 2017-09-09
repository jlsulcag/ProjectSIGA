
package org.siga.ctrl;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import org.siga.be.OrdenCompra;
import org.siga.bl.OrdenCompraBl;
import org.siga.util.MensajeView;
import org.siga.util.Utilitarios;

@ManagedBean
@ViewScoped
public class GenerarOrdenCompraBean {
    @ManagedProperty(value = "#{ordenCompra}")
    private OrdenCompra ordenCompra;    
    @ManagedProperty(value = "#{ordenCompraBl}")
    private OrdenCompraBl ordenCompraBl;
    
    private long res;
    
    public GenerarOrdenCompraBean() {
    }
    @PostConstruct
    public void limpiarOrdenCompra(){
        getOrdenCompra().setIdordencompra(0);
        getOrdenCompra().setNumero(maxNumero()+1);
        getOrdenCompra().setFecha(null);
        getOrdenCompra().setIdProveedor(0);
        getOrdenCompra().setFechaEntrega(null);
        getOrdenCompra().setLugarEntrega("");
        getOrdenCompra().setObservacion("");
    }
    
    public long maxNumero(){
        return getOrdenCompraBl().buscarUltimoNumero();
    }
    
    public long registrarOrdenCompra(){
        //buscar  proveedor por razon social
        System.out.println("proveedor "+ordenCompra.getProveedor().getRazonSocial());
        //ordenCompra.setProveedor(proveedorBl.buscarXNombre(proveedor.getRazonSocial()));
        //ordenCompra.setIdProveedor((int) proveedor.getIdproveedor());
        ordenCompra.setObservacion(getOrdenCompra().getObservacion().toUpperCase());
        ordenCompra.setEstado("REG");
        ordenCompra.setHoraRegistro(Utilitarios.horaActual());
        res = ordenCompraBl.registrar(getOrdenCompra());
        if (res == 0) {
            MensajeView.registroCorrecto();
        } else {
            MensajeView.registroError();
        }
        return res;
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
    
}
