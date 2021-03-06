package org.siga.be;
// Generated 07/08/2017 05:08:45 PM by Hibernate Tools 4.3.1


import java.util.Date;
import org.springframework.stereotype.Component;

/**
 * Persona generated by hbm2java
 */
@Component
public class Persona  implements java.io.Serializable {


     private long idpersona;
     private String nombre;
     private String apellidoPaterno;
     private String apellidoMaterno;
     private Date fechaNacimiento;
     private String numeroDocumento;
     private Date fechaReg;
     private String direccion;
     private String telefono;
     private String estado;
     private String sexo;
     private Integer idTipoDoc;
     private Integer idUbigeo;
     private String correoElectronico;
     private Integer idEstadoCivil;
     private String autorizaUsoDatos;

    public Persona() {
    }

	
    public Persona(long idpersona) {
        this.idpersona = idpersona;
    }
    public Persona(long idpersona, String nombre, String apellidoPaterno, String apellidoMaterno, Date fechaNacimiento, String numeroDocumento, Date fechaReg, String direccion, String telefono, String estado, String sexo, Integer idTipoDoc, Integer idUbigeo, String correoElectronico, Integer idEstadoCivil, String autorizaUsoDatos) {
       this.idpersona = idpersona;
       this.nombre = nombre;
       this.apellidoPaterno = apellidoPaterno;
       this.apellidoMaterno = apellidoMaterno;
       this.fechaNacimiento = fechaNacimiento;
       this.numeroDocumento = numeroDocumento;
       this.fechaReg = fechaReg;
       this.direccion = direccion;
       this.telefono = telefono;
       this.estado = estado;
       this.sexo = sexo;
       this.idTipoDoc = idTipoDoc;
       this.idUbigeo = idUbigeo;
       this.correoElectronico = correoElectronico;
       this.idEstadoCivil = idEstadoCivil;
       this.autorizaUsoDatos = autorizaUsoDatos;
    }
   
    public long getIdpersona() {
        return this.idpersona;
    }
    
    public void setIdpersona(long idpersona) {
        this.idpersona = idpersona;
    }
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getApellidoPaterno() {
        return this.apellidoPaterno;
    }
    
    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }
    public String getApellidoMaterno() {
        return this.apellidoMaterno;
    }
    
    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }
    public Date getFechaNacimiento() {
        return this.fechaNacimiento;
    }
    
    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
    public String getNumeroDocumento() {
        return this.numeroDocumento;
    }
    
    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }
    public Date getFechaReg() {
        return this.fechaReg;
    }
    
    public void setFechaReg(Date fechaReg) {
        this.fechaReg = fechaReg;
    }
    public String getDireccion() {
        return this.direccion;
    }
    
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public String getTelefono() {
        return this.telefono;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public String getEstado() {
        return this.estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public String getSexo() {
        return this.sexo;
    }
    
    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
    public Integer getIdTipoDoc() {
        return this.idTipoDoc;
    }
    
    public void setIdTipoDoc(Integer idTipoDoc) {
        this.idTipoDoc = idTipoDoc;
    }
    public Integer getIdUbigeo() {
        return this.idUbigeo;
    }
    
    public void setIdUbigeo(Integer idUbigeo) {
        this.idUbigeo = idUbigeo;
    }
    public String getCorreoElectronico() {
        return this.correoElectronico;
    }
    
    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }
    public Integer getIdEstadoCivil() {
        return this.idEstadoCivil;
    }
    
    public void setIdEstadoCivil(Integer idEstadoCivil) {
        this.idEstadoCivil = idEstadoCivil;
    }
    public String getAutorizaUsoDatos() {
        return this.autorizaUsoDatos;
    }
    
    public void setAutorizaUsoDatos(String autorizaUsoDatos) {
        this.autorizaUsoDatos = autorizaUsoDatos;
    }




}


