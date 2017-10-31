package org.siga.be;
// Generated 07/08/2017 05:08:45 PM by Hibernate Tools 4.3.1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Component;

/**
 * Pedido generated by hbm2java
 */
@Component
public class Pedido implements java.io.Serializable {

    private long idpedido;
    private Dependencia dependencia;
    private Almacen almacenDestino;
    private long numero;
    private Date fechaPedido;
    private String observacion;    
    private String estado;
    private String horaPedido;
    private int idUserreg;
    private Set<PedidoDetalle> pedidoDetalles = new HashSet<PedidoDetalle>(0);
    
    public Pedido() {
        this.idpedido = 0;
        this.dependencia = new Dependencia();
        this.almacenDestino = new Almacen();
    }

    public long getIdpedido() {
        return idpedido;
    }

    public void setIdpedido(long idpedido) {
        this.idpedido = idpedido;
    }

    public long getNumero() {
        return numero;
    }

    public void setNumero(long numero) {
        this.numero = numero;
    }

    public Date getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(Date fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

//    public Dependencia getDependencia() {
//        return dependencia;
//    }
//
//    public void setDependencia(Dependencia dependencia) {
//        this.dependencia = dependencia;
//    }

//    public Almacen getAlmacenOrigen() {
//        return almacenOrigen;
//    }
//
//    public void setAlmacenOrigen(Almacen almacenOrigen) {
//        this.almacenOrigen = almacenOrigen;
//    }
//
//    public Usuario getUserreg() {
//        return userreg;
//    }
//
//    public void setUserreg(Usuario userreg) {
//        this.userreg = userreg;
//    }
//
//    public TipoMovimiento getTipoMovimiento() {
//        return tipoMovimiento;
//    }
//
//    public void setTipoMovimiento(TipoMovimiento tipoMovimiento) {
//        this.tipoMovimiento = tipoMovimiento;
//    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    
    public String getHoraPedido() {
        return horaPedido;
    }

    public void setHoraPedido(String horaPedido) {
        this.horaPedido = horaPedido;
    }

    public Set<PedidoDetalle> getPedidoDetalles() {
        return pedidoDetalles;
    }

    public void setPedidoDetalles(Set<PedidoDetalle> pedidoDetalles) {
        this.pedidoDetalles = pedidoDetalles;
    }

    public Almacen getAlmacenDestino() {
        return almacenDestino;
    }

    public void setAlmacenDestino(Almacen almacenDestino) {
        this.almacenDestino = almacenDestino;
    }

    public int getIdUserreg() {
        return idUserreg;
    }

    public void setIdUserreg(int idUserreg) {
        this.idUserreg = idUserreg;
    }

    public Dependencia getDependencia() {
        return dependencia;
    }

    public void setDependencia(Dependencia dependencia) {
        this.dependencia = dependencia;
    }

   

}
