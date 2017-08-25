package org.siga.be;
// Generated 07/08/2017 05:08:45 PM by Hibernate Tools 4.3.1


import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Component;

/**
 * OrdenCompra generated by hbm2java
 */
@Component
public class OrdenCompra  implements java.io.Serializable {


     private long idordencompra;
     private long numero;
     private Date fecha;
     private int idProveedor;
     private Date fechaEntrega;
     private String lugarEntrega;
     private String observacion;
     private String estado;
     private String horaRegistro;
     private Proveedor proveedor;
     private Date fechaRecepcion;
     private int idAlmacenDestino;
     private int idMoneda;
     private String docReferencia;
     private BigDecimal valorBruto;
     private BigDecimal montoDesc;
     private BigDecimal valorNeto;
     private BigDecimal montoIgv;
     private BigDecimal montoTotal;
     
     private Set<OrdenCompraDetalle> ordenCompraDetalles = new HashSet<OrdenCompraDetalle>(0);
     private Set<NotaEntrada> notaEntradas = new HashSet<NotaEntrada>(0);

    public OrdenCompra() {
        this.idordencompra = 0;
        this.proveedor = new Proveedor();
    }

	
    public OrdenCompra(long idordencompra, long numero, Date fecha, int idProveedor, Date fechaEntrega, String lugarEntrega, String observacion, String estado) {
        this.idordencompra = idordencompra;
        this.numero = numero;
        this.fecha = fecha;
        this.idProveedor = idProveedor;
        this.fechaEntrega = fechaEntrega;
        this.lugarEntrega = lugarEntrega;
        this.observacion = observacion;
        this.estado = estado;
    }
    public OrdenCompra(long idordencompra, long numero, Date fecha, int idProveedor, Date fechaEntrega, String lugarEntrega, String observacion, String estado, Set<OrdenCompraDetalle> ordenCompraDetalles, Set<NotaEntrada> notaEntradas) {
       this.idordencompra = idordencompra;
       this.numero = numero;
       this.fecha = fecha;
       this.idProveedor = idProveedor;
       this.fechaEntrega = fechaEntrega;
       this.lugarEntrega = lugarEntrega;
       this.observacion = observacion;
       this.estado = estado;
       this.ordenCompraDetalles = ordenCompraDetalles;
       this.notaEntradas = notaEntradas;
    }
   
    public long getIdordencompra() {
        return this.idordencompra;
    }
    
    public void setIdordencompra(long idordencompra) {
        this.idordencompra = idordencompra;
    }
    public long getNumero() {
        return this.numero;
    }
    
    public void setNumero(long numero) {
        this.numero = numero;
    }
    public Date getFecha() {
        return this.fecha;
    }
    
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    public int getIdProveedor() {
        return this.idProveedor;
    }
    
    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }
    public Date getFechaEntrega() {
        return this.fechaEntrega;
    }
    
    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }
    public String getLugarEntrega() {
        return this.lugarEntrega;
    }
    
    public void setLugarEntrega(String lugarEntrega) {
        this.lugarEntrega = lugarEntrega;
    }
    public String getObservacion() {
        return this.observacion;
    }
    
    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
    public String getEstado() {
        return this.estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public Set<OrdenCompraDetalle> getOrdenCompraDetalles() {
        return this.ordenCompraDetalles;
    }
    
    public void setOrdenCompraDetalles(Set<OrdenCompraDetalle> ordenCompraDetalles) {
        this.ordenCompraDetalles = ordenCompraDetalles;
    }
    public Set<NotaEntrada> getNotaEntradas() {
        return this.notaEntradas;
    }
    
    public void setNotaEntradas(Set<NotaEntrada> notaEntradas) {
        this.notaEntradas = notaEntradas;
    }

    public String getHoraRegistro() {
        return horaRegistro;
    }

    public void setHoraRegistro(String horaRegistro) {
        this.horaRegistro = horaRegistro;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public Date getFechaRecepcion() {
        return fechaRecepcion;
    }

    public void setFechaRecepcion(Date fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
    }

    public int getIdAlmacenDestino() {
        return idAlmacenDestino;
    }

    public void setIdAlmacenDestino(int idAlmacenDestino) {
        this.idAlmacenDestino = idAlmacenDestino;
    }

    public int getIdMoneda() {
        return idMoneda;
    }

    public void setIdMoneda(int idMoneda) {
        this.idMoneda = idMoneda;
    }

    public String getDocReferencia() {
        return docReferencia;
    }

    public void setDocReferencia(String docReferencia) {
        this.docReferencia = docReferencia;
    }

    public BigDecimal getValorBruto() {
        return valorBruto;
    }

    public void setValorBruto(BigDecimal valorBruto) {
        this.valorBruto = valorBruto;
    }

    public BigDecimal getMontoDesc() {
        return montoDesc;
    }

    public void setMontoDesc(BigDecimal montoDesc) {
        this.montoDesc = montoDesc;
    }

    public BigDecimal getValorNeto() {
        return valorNeto;
    }

    public void setValorNeto(BigDecimal valorNeto) {
        this.valorNeto = valorNeto;
    }

    public BigDecimal getMontoIgv() {
        return montoIgv;
    }

    public void setMontoIgv(BigDecimal montoIgv) {
        this.montoIgv = montoIgv;
    }

    public BigDecimal getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(BigDecimal montoTotal) {
        this.montoTotal = montoTotal;
    }




}


