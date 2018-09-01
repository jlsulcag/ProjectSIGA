package org.siga.be;
// Generated 01/09/2018 12:46:20 PM by Hibernate Tools 4.3.1


import java.util.Date;

/**
 * OrdenCompraSeguimiento generated by hbm2java
 */
public class OrdenCompraSeguimiento  implements java.io.Serializable {


     private long idordencompraseguimiento;
     private OrdenCompra ordenCompra;
     private OrdenCompraEstados ordenCompraEstados;
     private Date fecha;
     private String hora;
     private int numero;
     private String estado;
     private long idUser;
     private String observacion;

    public OrdenCompraSeguimiento() {
        idordencompraseguimiento = 0;
        ordenCompra = new OrdenCompra();
        ordenCompraEstados = new OrdenCompraEstados();
    }

    public OrdenCompraSeguimiento(long idordencompraseguimiento, OrdenCompra ordenCompra, OrdenCompraEstados ordenCompraEstados, Date fecha, String hora, int numero, String estado, long idUser, String observacion) {
       this.idordencompraseguimiento = idordencompraseguimiento;
       this.ordenCompra = ordenCompra;
       this.ordenCompraEstados = ordenCompraEstados;
       this.fecha = fecha;
       this.hora = hora;
       this.numero = numero;
       this.estado = estado;
       this.idUser = idUser;
       this.observacion = observacion;
    }
   
    public long getIdordencompraseguimiento() {
        return this.idordencompraseguimiento;
    }
    
    public void setIdordencompraseguimiento(long idordencompraseguimiento) {
        this.idordencompraseguimiento = idordencompraseguimiento;
    }
    public OrdenCompra getOrdenCompra() {
        return this.ordenCompra;
    }
    
    public void setOrdenCompra(OrdenCompra ordenCompra) {
        this.ordenCompra = ordenCompra;
    }
    public OrdenCompraEstados getOrdenCompraEstados() {
        return this.ordenCompraEstados;
    }
    
    public void setOrdenCompraEstados(OrdenCompraEstados ordenCompraEstados) {
        this.ordenCompraEstados = ordenCompraEstados;
    }
    public Date getFecha() {
        return this.fecha;
    }
    
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    public String getHora() {
        return this.hora;
    }
    
    public void setHora(String hora) {
        this.hora = hora;
    }
    public int getNumero() {
        return this.numero;
    }
    
    public void setNumero(int numero) {
        this.numero = numero;
    }
    public String getEstado() {
        return this.estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public long getIdUser() {
        return this.idUser;
    }
    
    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }
    public String getObservacion() {
        return this.observacion;
    }
    
    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }




}


