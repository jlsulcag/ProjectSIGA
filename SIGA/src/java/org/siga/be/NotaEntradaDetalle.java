package org.siga.be;
// Generated 07/08/2017 05:08:45 PM by Hibernate Tools 4.3.1


import java.math.BigDecimal;
import org.springframework.stereotype.Component;

/**
 * NotaEntradaDetalle generated by hbm2java
 */
@Component
public class NotaEntradaDetalle  implements java.io.Serializable {


     private int idnotaentradadetalle;
     private NotaEntrada notaEntrada;
     private Producto producto;
     private Integer idUnidad;
     private int cantidad;
     private BigDecimal costoUnitario;
     private BigDecimal importe;

    public NotaEntradaDetalle() {
    }

	
    public NotaEntradaDetalle(int idnotaentradadetalle, NotaEntrada notaEntrada, Producto producto, int cantidad, BigDecimal costoUnitario, BigDecimal importe) {
        this.idnotaentradadetalle = idnotaentradadetalle;
        this.notaEntrada = notaEntrada;
        this.producto = producto;
        this.cantidad = cantidad;
        this.costoUnitario = costoUnitario;
        this.importe = importe;
    }
    public NotaEntradaDetalle(int idnotaentradadetalle, NotaEntrada notaEntrada, Producto producto, Integer idUnidad, int cantidad, BigDecimal costoUnitario, BigDecimal importe) {
       this.idnotaentradadetalle = idnotaentradadetalle;
       this.notaEntrada = notaEntrada;
       this.producto = producto;
       this.idUnidad = idUnidad;
       this.cantidad = cantidad;
       this.costoUnitario = costoUnitario;
       this.importe = importe;
    }
   
    public int getIdnotaentradadetalle() {
        return this.idnotaentradadetalle;
    }
    
    public void setIdnotaentradadetalle(int idnotaentradadetalle) {
        this.idnotaentradadetalle = idnotaentradadetalle;
    }
    public NotaEntrada getNotaEntrada() {
        return this.notaEntrada;
    }
    
    public void setNotaEntrada(NotaEntrada notaEntrada) {
        this.notaEntrada = notaEntrada;
    }
    public Producto getProducto() {
        return this.producto;
    }
    
    public void setProducto(Producto producto) {
        this.producto = producto;
    }
    public Integer getIdUnidad() {
        return this.idUnidad;
    }
    
    public void setIdUnidad(Integer idUnidad) {
        this.idUnidad = idUnidad;
    }
    public int getCantidad() {
        return this.cantidad;
    }
    
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    public BigDecimal getCostoUnitario() {
        return this.costoUnitario;
    }
    
    public void setCostoUnitario(BigDecimal costoUnitario) {
        this.costoUnitario = costoUnitario;
    }
    public BigDecimal getImporte() {
        return this.importe;
    }
    
    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }




}


