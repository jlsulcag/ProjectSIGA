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
    private long numero;
    private Date fechaPedido;
    private Integer idAlmacenSolicitante;
    private int idCentroGasto;
    private String observacion;
    private String justificacionUso;
    private int idAlmacenOrigen;
    private String estado;
    private int idUserReg;
    private int idUserAprueba;
    private Date fechaEntrega;
    private String lugarEntrega;
    private String horaPedido;
    private Set<PedidoDetalle> pedidoDetalles = new HashSet<PedidoDetalle>(0);

    public Pedido() {
    }

    public Pedido(long idpedido, int numero, Date fechaPedido, int idCentroGasto, String observacion, String justificacionUso, int idAlmacenOrigen, String estado, int idUserReg, int idUserAprueba, Date fechaEntrega, String lugarEntrega, String horaPedido) {
        this.idpedido = idpedido;
        this.numero = numero;
        this.fechaPedido = fechaPedido;
        this.idCentroGasto = idCentroGasto;
        this.observacion = observacion;
        this.justificacionUso = justificacionUso;
        this.idAlmacenOrigen = idAlmacenOrigen;
        this.estado = estado;
        this.idUserReg = idUserReg;
        this.idUserAprueba = idUserAprueba;
        this.horaPedido = horaPedido;
        this.fechaEntrega = fechaEntrega;
        this.lugarEntrega = lugarEntrega;
    }

    public Pedido(long idpedido, int numero, Date fechaPedido, Integer idAlmacenSolicitante, int idCentroGasto, String observacion, String justificacionUso, int idAlmacenOrigen, String estado, int idUserReg, int idUserAprueba, String horaPedido, Set<PedidoDetalle> pedidoDetalles, Date fechaEntrega, String lugarEntrega) {
        this.idpedido = idpedido;
        this.numero = numero;
        this.fechaPedido = fechaPedido;
        this.idAlmacenSolicitante = idAlmacenSolicitante;
        this.idCentroGasto = idCentroGasto;
        this.observacion = observacion;
        this.justificacionUso = justificacionUso;
        this.idAlmacenOrigen = idAlmacenOrigen;
        this.estado = estado;
        this.idUserReg = idUserReg;
        this.idUserAprueba = idUserAprueba;
        this.horaPedido = horaPedido;
        this.pedidoDetalles = pedidoDetalles;
        this.fechaEntrega = fechaEntrega;
        this.lugarEntrega = lugarEntrega;
    }

    public long getIdpedido() {
        return this.idpedido;
    }

    public void setIdpedido(long idpedido) {
        this.idpedido = idpedido;
    }

    public long getNumero() {
        return this.numero;
    }

    public void setNumero(long numero) {
        this.numero = numero;
    }

    public Date getFechaPedido() {
        return this.fechaPedido;
    }

    public void setFechaPedido(Date fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public Integer getIdAlmacenSolicitante() {
        return this.idAlmacenSolicitante;
    }

    public void setIdAlmacenSolicitante(Integer idAlmacenSolicitante) {
        this.idAlmacenSolicitante = idAlmacenSolicitante;
    }

    public int getIdCentroGasto() {
        return this.idCentroGasto;
    }

    public void setIdCentroGasto(int idCentroGasto) {
        this.idCentroGasto = idCentroGasto;
    }

    public String getObservacion() {
        return this.observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getJustificacionUso() {
        return this.justificacionUso;
    }

    public void setJustificacionUso(String justificacionUso) {
        this.justificacionUso = justificacionUso;
    }

    public int getIdAlmacenOrigen() {
        return this.idAlmacenOrigen;
    }

    public void setIdAlmacenOrigen(int idAlmacenOrigen) {
        this.idAlmacenOrigen = idAlmacenOrigen;
    }

    public String getEstado() {
        return this.estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getIdUserReg() {
        return this.idUserReg;
    }

    public void setIdUserReg(int idUserReg) {
        this.idUserReg = idUserReg;
    }

    public int getIdUserAprueba() {
        return this.idUserAprueba;
    }

    public void setIdUserAprueba(int idUserAprueba) {
        this.idUserAprueba = idUserAprueba;
    }

    public String getHoraPedido() {
        return this.horaPedido;
    }

    public void setHoraPedido(String horaPedido) {
        this.horaPedido = horaPedido;
    }

    public Set<PedidoDetalle> getPedidoDetalles() {
        return this.pedidoDetalles;
    }

    public void setPedidoDetalles(Set<PedidoDetalle> pedidoDetalles) {
        this.pedidoDetalles = pedidoDetalles;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getLugarEntrega() {
        return lugarEntrega;
    }

    public void setLugarEntrega(String lugarEntrega) {
        this.lugarEntrega = lugarEntrega;
    }

}
