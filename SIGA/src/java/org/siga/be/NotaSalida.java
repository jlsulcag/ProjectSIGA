package org.siga.be;
// Generated 07/08/2017 05:08:45 PM by Hibernate Tools 4.3.1


import java.util.Date;
import org.springframework.stereotype.Component;

/**
 * NotaSalida generated by hbm2java
 */
@Component
public class NotaSalida  implements java.io.Serializable {
    
     private long idnotasalida;
     private int numero;
     private Date fechaReg;
     private Pedido pedido;
     private int idUserReg;
     private TipoMovimiento tipomovimiento;
     private Almacen almacenOrigen;
     private Almacen almacenDestino;
     private String observacion;
     private Dependencia dependencia;
     private String personaDestino;
     private String docRef;

    public NotaSalida() {
        this.idnotasalida = 0;
        this.pedido = new Pedido();
        this.tipomovimiento = new TipoMovimiento();
        this.almacenOrigen = new Almacen();
        this.almacenDestino = new Almacen();
        this.dependencia = new Dependencia();
    }

    public long getIdnotasalida() {
        return idnotasalida;
    }

    public void setIdnotasalida(long idnotasalida) {
        this.idnotasalida = idnotasalida;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public Date getFechaReg() {
        return fechaReg;
    }

    public void setFechaReg(Date fechaReg) {
        this.fechaReg = fechaReg;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public int getIdUserReg() {
        return idUserReg;
    }

    public void setIdUserReg(int idUserReg) {
        this.idUserReg = idUserReg;
    }

    public Almacen getAlmacenOrigen() {
        return almacenOrigen;
    }

    public void setAlmacenOrigen(Almacen almacenOrigen) {
        this.almacenOrigen = almacenOrigen;
    }

    public Almacen getAlmacenDestino() {
        return almacenDestino;
    }

    public void setAlmacenDestino(Almacen almacenDestino) {
        this.almacenDestino = almacenDestino;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public TipoMovimiento getTipomovimiento() {
        return tipomovimiento;
    }

    public void setTipomovimiento(TipoMovimiento tipomovimiento) {
        this.tipomovimiento = tipomovimiento;
    }

    public Dependencia getDependencia() {
        return dependencia;
    }

    public void setDependencia(Dependencia dependencia) {
        this.dependencia = dependencia;
    }

    public String getPersonaDestino() {
        return personaDestino;
    }

    public void setPersonaDestino(String personaDestino) {
        this.personaDestino = personaDestino;
    }

    public String getDocRef() {
        return docRef;
    }

    public void setDocRef(String docRef) {
        this.docRef = docRef;
    }

}


