package org.siga.be;
// Generated 07/08/2017 05:08:45 PM by Hibernate Tools 4.3.1

import org.springframework.stereotype.Component;




/**
 * Permiso generated by hbm2java
 */
@Component
public class Permiso  implements java.io.Serializable {


     private long idpermiso;
     private int codigo;
     private String permiso;
     private String descripcion;
     private int idUsuarioAct;
     private int idUsuario;

    public Permiso() {
    }

    public Permiso(long idpermiso, int codigo, String permiso, String descripcion, int idUsuarioAct, int idUsuario) {
       this.idpermiso = idpermiso;
       this.codigo = codigo;
       this.permiso = permiso;
       this.descripcion = descripcion;
       this.idUsuarioAct = idUsuarioAct;
       this.idUsuario = idUsuario;
    }
   
    public long getIdpermiso() {
        return this.idpermiso;
    }
    
    public void setIdpermiso(long idpermiso) {
        this.idpermiso = idpermiso;
    }
    public int getCodigo() {
        return this.codigo;
    }
    
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    public String getPermiso() {
        return this.permiso;
    }
    
    public void setPermiso(String permiso) {
        this.permiso = permiso;
    }
    public String getDescripcion() {
        return this.descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public int getIdUsuarioAct() {
        return this.idUsuarioAct;
    }
    
    public void setIdUsuarioAct(int idUsuarioAct) {
        this.idUsuarioAct = idUsuarioAct;
    }
    public int getIdUsuario() {
        return this.idUsuario;
    }
    
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }




}

