package org.siga.be;
// Generated 07/08/2017 05:08:45 PM by Hibernate Tools 4.3.1

import java.math.BigDecimal;
import java.util.Date;
import org.springframework.stereotype.Component;

/**
 * NotaEntradaDetalle generated by hbm2java
 */
@Component
public class NotaEntradaDetalle implements java.io.Serializable {

    private long idnotaentradadetalle;
    private NotaEntrada notaEntrada;
    private Producto producto;
    private String lote;
    private Date fechaVencimiento;
    private BigDecimal valorCompra;
    private BigDecimal precioCompra;
    private double desc1;
    private double desc2;
    private String unidadMedida;
    private BigDecimal montoDescitem;
    private BigDecimal subTotal;
    private int cantSolicitada;
    private int cantRecibida;
    private int cantPendiente;
    private int cantIngreso;

    public NotaEntradaDetalle() {
        this.idnotaentradadetalle = 0;
        this.notaEntrada = new NotaEntrada();
        this.producto = new Producto();
    } 

    public long getIdnotaentradadetalle() {
        return this.idnotaentradadetalle;
    }

    public void setIdnotaentradadetalle(long idnotaentradadetalle) {
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

    public int getCantSolicitada() {
        return cantSolicitada;
    }

    public void setCantSolicitada(int cantSolicitada) {
        this.cantSolicitada = cantSolicitada;
    }

    public int getCantRecibida() {
        return cantRecibida;
    }

    public void setCantRecibida(int cantRecibida) {
        this.cantRecibida = cantRecibida;
    }

    public int getCantPendiente() {
        return cantPendiente;
    }

    public void setCantPendiente(int cantPendiente) {
        this.cantPendiente = cantPendiente;
    }

    public int getCantIngreso() {
        return cantIngreso;
    }

    public void setCantIngreso(int cantIngreso) {
        this.cantIngreso = cantIngreso;
    }

}
