package org.siga.be;
// Generated 07/08/2017 05:08:45 PM by Hibernate Tools 4.3.1


import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Component;

/**
 * NotaEntrada generated by hbm2java
 */
@Component
public class NotaEntrada  implements java.io.Serializable {


     private long idnotaentrada;
     private OrdenCompra ordenCompra;
     private Proveedor proveedor;
     private Almacen almacenDestino;
     private long numero;
     private Date fechaReg;
     private Date fechaDocref;
     private String nroDocref;
     private long idUserReg;
     private String observacion;
     private String tipoIngreso;
     private Pedido pedido;
     private Set<NotaEntradaDetalle> notaEntradaDetalles = new HashSet<NotaEntradaDetalle>(0);

    public NotaEntrada() {
        this.idnotaentrada = 0;
        this.ordenCompra = new OrdenCompra();
        this.proveedor = new Proveedor();
        this.almacenDestino = new Almacen();
    }
    
   
    public long getIdnotaentrada() {
        return this.idnotaentrada;
    }
    
    public void setIdnotaentrada(long idnotaentrada) {
        this.idnotaentrada = idnotaentrada;
    }
    public OrdenCompra getOrdenCompra() {
        return this.ordenCompra;
    }
    
    public void setOrdenCompra(OrdenCompra ordenCompra) {
        this.ordenCompra = ordenCompra;
    }
    
    public long getNumero() {
        return this.numero;
    }
    
    public void setNumero(long numero) {
        this.numero = numero;
    }
    public Date getFechaReg() {
        return this.fechaReg;
    }
    
    public void setFechaReg(Date fechaReg) {
        this.fechaReg = fechaReg;
    }
    public Date getFechaDocref() {
        return this.fechaDocref;
    }
    
    public void setFechaDocref(Date fechaDocref) {
        this.fechaDocref = fechaDocref;
    }
    public String getNroDocref() {
        return this.nroDocref;
    }
    
    public void setNroDocref(String nroDocref) {
        this.nroDocref = nroDocref;
    }
    public long getIdUserReg() {
        return this.idUserReg;
    }
    
    public void setIdUserReg(long idUserReg) {
        this.idUserReg = idUserReg;
    }
    public String getObservacion() {
        return this.observacion;
    }
    
    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
    
    public Set<NotaEntradaDetalle> getNotaEntradaDetalles() {
        return this.notaEntradaDetalles;
    }
    
    public void setNotaEntradaDetalles(Set<NotaEntradaDetalle> notaEntradaDetalles) {
        this.notaEntradaDetalles = notaEntradaDetalles;
    }

    public String getTipoIngreso() {
        return tipoIngreso;
    }

    public void setTipoIngreso(String tipoIngreso) {
        this.tipoIngreso = tipoIngreso;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public Almacen getAlmacenDestino() {
        return almacenDestino;
    }

    public void setAlmacenDestino(Almacen almacenDestino) {
        this.almacenDestino = almacenDestino;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }


}


