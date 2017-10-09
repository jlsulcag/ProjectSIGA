package org.siga.be;
// Generated 09/10/2017 11:11:06 AM by Hibernate Tools 4.3.1


import java.math.BigDecimal;
import java.util.Date;
import org.springframework.stereotype.Component;

/**
 * AlmacenProducto generated by hbm2java
 */
@Component
public class AlmacenProducto  implements java.io.Serializable {


     private long idalmacenproducto;
     private Almacen almacen;
     private Producto producto;
     private int stockActual;
     private String lote;
     private Date fechaVencimiento;
     private BigDecimal valor;

    public AlmacenProducto() {
    }

	
    public AlmacenProducto(long idalmacenproducto, Almacen almacen, Producto producto, int stockActual, String lote) {
        this.idalmacenproducto = idalmacenproducto;
        this.almacen = almacen;
        this.producto = producto;
        this.stockActual = stockActual;
        this.lote = lote;
    }
    public AlmacenProducto(long idalmacenproducto, Almacen almacen, Producto producto, int stockActual, String lote, Date fechaVencimiento, BigDecimal valor) {
       this.idalmacenproducto = idalmacenproducto;
       this.almacen = almacen;
       this.producto = producto;
       this.stockActual = stockActual;
       this.lote = lote;
       this.fechaVencimiento = fechaVencimiento;
       this.valor = valor;
    }
   
    public long getIdalmacenproducto() {
        return this.idalmacenproducto;
    }
    
    public void setIdalmacenproducto(long idalmacenproducto) {
        this.idalmacenproducto = idalmacenproducto;
    }
    public Almacen getAlmacen() {
        return this.almacen;
    }
    
    public void setAlmacen(Almacen almacen) {
        this.almacen = almacen;
    }
    public Producto getProducto() {
        return this.producto;
    }
    
    public void setProducto(Producto producto) {
        this.producto = producto;
    }
    public int getStockActual() {
        return this.stockActual;
    }
    
    public void setStockActual(int stockActual) {
        this.stockActual = stockActual;
    }
    public String getLote() {
        return this.lote;
    }
    
    public void setLote(String lote) {
        this.lote = lote;
    }
    public Date getFechaVencimiento() {
        return this.fechaVencimiento;
    }
    
    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }
    public BigDecimal getValor() {
        return this.valor;
    }
    
    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }




}


