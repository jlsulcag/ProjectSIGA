package org.siga.be;
// Generated 07/08/2017 05:08:45 PM by Hibernate Tools 4.3.1

import org.springframework.stereotype.Component;




/**
 * EstadoCivil generated by hbm2java
 */
@Component
public class EstadoCivil  implements java.io.Serializable {


     private long idestadocivil;
     private String estadoCivil;
     private Boolean estado;

    public EstadoCivil() {
    }

	
    public EstadoCivil(long idestadocivil) {
        this.idestadocivil = idestadocivil;
    }
    public EstadoCivil(long idestadocivil, String estadoCivil, Boolean estado) {
       this.idestadocivil = idestadocivil;
       this.estadoCivil = estadoCivil;
       this.estado = estado;
    }
   
    public long getIdestadocivil() {
        return this.idestadocivil;
    }
    
    public void setIdestadocivil(long idestadocivil) {
        this.idestadocivil = idestadocivil;
    }
    public String getEstadoCivil() {
        return this.estadoCivil;
    }
    
    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }
    public Boolean getEstado() {
        return this.estado;
    }
    
    public void setEstado(Boolean estado) {
        this.estado = estado;
    }




}


