package org.siga.be;
// Generated 07/08/2017 05:08:45 PM by Hibernate Tools 4.3.1

import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class Dependencia  implements java.io.Serializable {


     private long iddependencia;
     private Almacen almacen;//hace referencia a la agencia que pertenece dicha dependencia
     private String descripcion;
     private String estado;     

    public Dependencia() {
        this.iddependencia = 0;
        this.almacen = new Almacen();
    }

    public Dependencia(long iddependencia, String descripcion, String estado) {
       this.iddependencia = iddependencia;
       this.descripcion = descripcion;
       this.estado = estado;
    }
   
    public long getIddependencia() {
        return this.iddependencia;
    }
    
    public void setIddependencia(long iddependencia) {
        this.iddependencia = iddependencia;
    }
    public String getDescripcion() {
        return this.descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public String getEstado() {
        return this.estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Almacen getAlmacen() {
        return almacen;
    }

    public void setAlmacen(Almacen almacen) {
        this.almacen = almacen;
    }




}


