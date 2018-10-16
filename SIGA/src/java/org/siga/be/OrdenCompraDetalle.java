package org.siga.be;
// Generated 07/08/2017 05:08:45 PM by Hibernate Tools 4.3.1

import java.math.BigDecimal;
import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public class OrdenCompraDetalle  implements java.io.Serializable {
     private long idordencompradetalle;
     private OrdenCompra ordenCompra;
     private Producto producto;
     private int cantidad;
     private String observacion;
     private String lote;
     private Date fechaVencimiento;
     private BigDecimal valorCompra;
     private BigDecimal precioCompra;
     private double desc1;
     private double desc2;
     private String unidadMedida;
     private BigDecimal montoDescitem;
     private BigDecimal subTotal;
     private long idEquivalencia;
     private String descripcion;

    public OrdenCompraDetalle() {
        this.idordencompradetalle = 0;
        this.producto = new Producto();
        this.ordenCompra = new OrdenCompra();
    }

	
    public OrdenCompraDetalle(long idordencompradetalle, OrdenCompra ordenCompra, Producto producto, int cantidad) {
        this.idordencompradetalle = idordencompradetalle;
        this.ordenCompra = ordenCompra;
        this.producto = producto;
        this.cantidad = cantidad;
    }
    public OrdenCompraDetalle(long idordencompradetalle, OrdenCompra ordenCompra, Producto producto, int cantidad, String observacion) {
       this.idordencompradetalle = idordencompradetalle;
       this.ordenCompra = ordenCompra;
       this.producto = producto;
       this.cantidad = cantidad;
       this.observacion = observacion;
    }
   
    public long getIdordencompradetalle() {
        return this.idordencompradetalle;
    }
    
    public void setIdordencompradetalle(long idordencompradetalle) {
        this.idordencompradetalle = idordencompradetalle;
    }
    public OrdenCompra getOrdenCompra() {
        return this.ordenCompra;
    }
    
    public void setOrdenCompra(OrdenCompra ordenCompra) {
        this.ordenCompra = ordenCompra;
    }
    public Producto getProducto() {
        return this.producto;
    }
    
    public void setProducto(Producto producto) {
        this.producto = producto;
    }
    public int getCantidad() {
        return this.cantidad;
    }
    
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    public String getObservacion() {
        return this.observacion;
    }
    
    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public BigDecimal getValorCompra() {
        return valorCompra;
    }

    public void setValorCompra(BigDecimal valorCompra) {
        this.valorCompra = valorCompra;
    }

    public BigDecimal getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(BigDecimal precioCompra) {
        this.precioCompra = precioCompra;
    }

    public double getDesc1() {
        return desc1;
    }

    public void setDesc1(double desc1) {
        this.desc1 = desc1;
    }

    public double getDesc2() {
        return desc2;
    }

    public void setDesc2(double desc2) {
        this.desc2 = desc2;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public BigDecimal getMontoDescitem() {
        return montoDescitem;
    }

    public void setMontoDescitem(BigDecimal montoDescitem) {
        this.montoDescitem = montoDescitem;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

    public long getIdEquivalencia() {
        return idEquivalencia;
    }

    public void setIdEquivalencia(long idEquivalencia) {
        this.idEquivalencia = idEquivalencia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}


