package org.siga.be;
// Generated 07/08/2017 05:08:45 PM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Component;

/**
 * Familia generated by hbm2java
 */
@Component
public class Familia  implements java.io.Serializable {


     private long idfamilia;
     private String descripcion;
     private String estado;
     private Set<Clase> clases = new HashSet<Clase>(0);

    public Familia() {
    }

	
    public Familia(long idfamilia, String descripcion, String estado) {
        this.idfamilia = idfamilia;
        this.descripcion = descripcion;
        this.estado = estado;
    }
    public Familia(long idfamilia, String descripcion, String estado, Set<Clase> clases) {
       this.idfamilia = idfamilia;
       this.descripcion = descripcion;
       this.estado = estado;
       this.clases = clases;
    }
   
    public long getIdfamilia() {
        return this.idfamilia;
    }
    
    public void setIdfamilia(long idfamilia) {
        this.idfamilia = idfamilia;
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
    public Set<Clase> getClases() {
        return this.clases;
    }
    
    public void setClases(Set<Clase> clases) {
        this.clases = clases;
    }




}


