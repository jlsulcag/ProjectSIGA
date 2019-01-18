package org.siga.be;
// Generated 17/01/2019 08:51:05 AM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Component;

/**
 * Moneda generated by hbm2java
 */
@Component
public class Moneda  implements java.io.Serializable {


     private long idmoneda;
     private String descripcion;
     private boolean estado;
     private String siglaSunat;
     private String simbolo;
     private Set ordenCompras = new HashSet(0);

    public Moneda() {
    }

	
    public Moneda(long idmoneda, String descripcion, boolean estado, String siglaSunat, String simbolo) {
        this.idmoneda = idmoneda;
        this.descripcion = descripcion;
        this.estado = estado;
        this.siglaSunat = siglaSunat;
        this.simbolo = simbolo;
    }
    public Moneda(long idmoneda, String descripcion, boolean estado, String siglaSunat, String simbolo, Set ordenCompras) {
       this.idmoneda = idmoneda;
       this.descripcion = descripcion;
       this.estado = estado;
       this.siglaSunat = siglaSunat;
       this.simbolo = simbolo;
       this.ordenCompras = ordenCompras;
    }
   
    public long getIdmoneda() {
        return this.idmoneda;
    }
    
    public void setIdmoneda(long idmoneda) {
        this.idmoneda = idmoneda;
    }
    public String getDescripcion() {
        return this.descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public boolean isEstado() {
        return this.estado;
    }
    
    public void setEstado(boolean estado) {
        this.estado = estado;
    }
    public String getSiglaSunat() {
        return this.siglaSunat;
    }
    
    public void setSiglaSunat(String siglaSunat) {
        this.siglaSunat = siglaSunat;
    }
    public String getSimbolo() {
        return this.simbolo;
    }
    
    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }
    public Set getOrdenCompras() {
        return this.ordenCompras;
    }
    
    public void setOrdenCompras(Set ordenCompras) {
        this.ordenCompras = ordenCompras;
    }




}


