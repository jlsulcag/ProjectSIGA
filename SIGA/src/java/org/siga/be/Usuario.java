package org.siga.be;
// Generated 07/08/2017 05:08:45 PM by Hibernate Tools 4.3.1


import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Component;

/**
 * Usuario generated by hbm2java
 */
@Component
public class Usuario  implements java.io.Serializable {


     private long idusuario;
     private String nombre;
     private String descripcion;
     private String contrasenia;
     private String estado;
     private Persona persona;
     private Date fechaReg;
     private Dependencia dependencia;
     private Set<Rol> roles = new HashSet<Rol>(0);
     private Set<UsuarioPermiso> usuarioPermisos = new HashSet<UsuarioPermiso>(0);

    public Usuario() {
        this.idusuario = 0;
        persona = new Persona();
        dependencia = new Dependencia();
    }

	
    public Usuario(long idusuario, String nombre, String descripcion, String contrasenia, String estado, int idPersona) {
        this.idusuario = idusuario;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.contrasenia = contrasenia;
        this.estado = estado;
    }
    public Usuario(long idusuario, String nombre, String descripcion, String contrasenia, String estado, int idPersona, Date fechaReg) {
       this.idusuario = idusuario;
       this.nombre = nombre;
       this.descripcion = descripcion;
       this.contrasenia = contrasenia;
       this.estado = estado;
       this.fechaReg = fechaReg;
    }
   
    public long getIdusuario() {
        return this.idusuario;
    }
    
    public void setIdusuario(long idusuario) {
        this.idusuario = idusuario;
    }
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getDescripcion() {
        return this.descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public String getContrasenia() {
        return this.contrasenia;
    }
    
    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }
    public String getEstado() {
        return this.estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public Date getFechaReg() {
        return this.fechaReg;
    }
    
    public void setFechaReg(Date fechaReg) {
        this.fechaReg = fechaReg;
    }

    public Dependencia getDependencia() {
        return dependencia;
    }

    public void setDependencia(Dependencia dependencia) {
        this.dependencia = dependencia;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public Set<Rol> getRoles() {
        return roles;
    }

    public void setRoles(Set<Rol> roles) {
        this.roles = roles;
    }

    public Set<UsuarioPermiso> getUsuarioPermisos() {
        return usuarioPermisos;
    }

    public void setUsuarioPermisos(Set<UsuarioPermiso> usuarioPermisos) {
        this.usuarioPermisos = usuarioPermisos;
    }




}


