package org.siga.be;
// Generated 05/10/2018 10:07:52 AM by Hibernate Tools 4.3.1


import java.util.Date;
import org.springframework.stereotype.Component;

/**
 * UsuarioPermiso generated by hbm2java
 */
@Component
public class UsuarioPermiso  implements java.io.Serializable {


     private long idusuariopermiso;
     private Permiso permiso;
     private Usuario usuario;
     private long idUseract;
     private Date fecha;

    public UsuarioPermiso() {
    }

    public UsuarioPermiso(long idusuariopermiso, Permiso permiso, Usuario usuario, long idUseract, Date fecha) {
       this.idusuariopermiso = idusuariopermiso;
       this.permiso = permiso;
       this.usuario = usuario;
       this.idUseract = idUseract;
       this.fecha = fecha;
    }
   
    public long getIdusuariopermiso() {
        return this.idusuariopermiso;
    }
    
    public void setIdusuariopermiso(long idusuariopermiso) {
        this.idusuariopermiso = idusuariopermiso;
    }
    public Permiso getPermiso() {
        return this.permiso;
    }
    
    public void setPermiso(Permiso permiso) {
        this.permiso = permiso;
    }
    public Usuario getUsuario() {
        return this.usuario;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    public long getIdUseract() {
        return this.idUseract;
    }
    
    public void setIdUseract(long idUseract) {
        this.idUseract = idUseract;
    }
    public Date getFecha() {
        return this.fecha;
    }
    
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }




}

