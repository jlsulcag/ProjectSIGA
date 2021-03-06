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
     private Moneda moneda;
     private long numero;
     private Date fecha;
     private Date fechaEntrega;
     private String observacion;
     private String horaRegistro;
     private Proveedor proveedor;
     private Date fechaRecepcion;
     private Almacen almacenSolicitante;
     private String docReferencia;
     private BigDecimal valorBruto;
     private BigDecimal montoDesc;
     private BigDecimal valorNeto;
     private BigDecimal montoIgv;
     private BigDecimal montoTotal;
     private String formaPago;
     private BigDecimal montoSubTotal;
     private String estado;
     private String penalidadIncumplimiento;
     private String tipoOrden;
     private String reqParapago;
     
     private Set<OrdenCompraDetalle> ordenCompraDetalles = new HashSet<OrdenCompraDetalle>(0);
     private Set<NotaEntrada> notaEntradas = new HashSet<NotaEntrada>(0);
     private Set<OrdenCompraSeguimiento> ordenCompraSeguimientos = new HashSet<OrdenCompraSeguimiento>(0);

    public OrdenCompra() {
        this.idordencompra = 0;
        this.proveedor = new Proveedor();
        this.almacenSolicitante = new Almacen();
        this.moneda = new Moneda();
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
    
    public Date getFechaEntrega() {
        return this.fechaEntrega;
    }
    
    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }
    public String getObservacion() {
        return this.observacion;
    }
    
    public void setObservacion(String observacion) {
        this.observacion = observacion;
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

    public Almacen getAlmacenSolicitante() {
        return almacenSolicitante;
    }

    public void setAlmacenSolicitante(Almacen almacenSolicitante) {
        this.almacenSolicitante = almacenSolicitante;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public Set<OrdenCompraSeguimiento> getOrdenCompraSeguimientos() {
        return ordenCompraSeguimientos;
    }

    public void setOrdenCompraSeguimientos(Set<OrdenCompraSeguimiento> ordenCompraSeguimientos) {
        this.ordenCompraSeguimientos = ordenCompraSeguimientos;
    }

    public BigDecimal getMontoSubTotal() {
        return montoSubTotal;
    }

    public void setMontoSubTotal(BigDecimal montoSubTotal) {
        this.montoSubTotal = montoSubTotal;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPenalidadIncumplimiento() {
        return penalidadIncumplimiento;
    }

    public void setPenalidadIncumplimiento(String penalidadIncumplimiento) {
        this.penalidadIncumplimiento = penalidadIncumplimiento;
    }

    public String getTipoOrden() {
        return tipoOrden;
    }

    public void setTipoOrden(String tipoOrden) {
        this.tipoOrden = tipoOrden;
    }

    public Moneda getMoneda() {
        return moneda;
    }

    public void setMoneda(Moneda moneda) {
        this.moneda = moneda;
    }

    public String getReqParapago() {
        return reqParapago;
    }

    public void setReqParapago(String reqParapago) {
        this.reqParapago = reqParapago;
    }





}


