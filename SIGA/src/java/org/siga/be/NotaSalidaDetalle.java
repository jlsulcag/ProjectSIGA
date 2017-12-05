package org.siga.be;
// Generated 07/08/2017 05:08:45 PM by Hibernate Tools 4.3.1

import org.springframework.stereotype.Component;




/**
 * NotaSalidaDetalle generated by hbm2java
 */
@Component
public class NotaSalidaDetalle  implements java.io.Serializable {


     private long idnotasalidadetalle;
     private Producto producto;
     private String unidadmedida;
     private NotaSalida notasalida;
     private int cantidad;
     private long idAlmacenProducto;
     private int stock;

    public NotaSalidaDetalle() {
        this.idnotasalidadetalle = 0;
        this.producto = new Producto();
        this.notasalida = new NotaSalida();
    }

    public NotaSalidaDetalle(long idnotasalidadetalle, Producto producto, String unidadmedida, NotaSalida notasalida, int cantidad) {
        this.idnotasalidadetalle = idnotasalidadetalle;
        this.producto = producto;
        this.unidadmedida = unidadmedida;
        this.notasalida = notasalida;
        this.cantidad = cantidad;
    }
    
    public long getIdnotasalidadetalle() {
        return this.idnotasalidadetalle;
    }
    
    public void setIdnotasalidadetalle(long idnotasalidadetalle) {
        this.idnotasalidadetalle = idnotasalidadetalle;
    }
    public int getCantidad() {
        return this.cantidad;
    }
    
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public String getUnidadmedida() {
        return unidadmedida;
    }

    public void setUnidadmedida(String unidadmedida) {
        this.unidadmedida = unidadmedida;
    }

    public NotaSalida getNotasalida() {
        return notasalida;
    }

    public void setNotasalida(NotaSalida notasalida) {
        this.notasalida = notasalida;
    }

    public long getIdAlmacenProducto() {
        return idAlmacenProducto;
    }

    public void setIdAlmacenProducto(long idAlmacenProducto) {
        this.idAlmacenProducto = idAlmacenProducto;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }




}


