package org.siga.be;

import org.springframework.stereotype.Component;

@Component
public class Proveedor  implements java.io.Serializable {
     private long idproveedor;
     private String codigo;
     private String razonSocial;
     private String ruc;
     private String direccion;
     private String telefono;
     private String email;
     private String contacto;

    public Proveedor() {
    }

    public Proveedor(long idproveedor, String codigo, String razonSocial, String ruc, String direccion, String telefono, String email, String contacto) {
       this.idproveedor = idproveedor;
       this.codigo = codigo;
       this.razonSocial = razonSocial;
       this.ruc = ruc;
       this.direccion = direccion;
       this.telefono = telefono;
       this.email = email;
       this.contacto = contacto;
    }
   
    public long getIdproveedor() {
        return this.idproveedor;
    }
    
    public void setIdproveedor(long idproveedor) {
        this.idproveedor = idproveedor;
    }
    public String getCodigo() {
        return this.codigo;
    }
    
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    public String getRazonSocial() {
        return this.razonSocial;
    }
    
    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }
    public String getRuc() {
        return this.ruc;
    }
    
    public void setRuc(String ruc) {
        this.ruc = ruc;
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
    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    public String getContacto() {
        return this.contacto;
    }
    
    public void setContacto(String contacto) {
        this.contacto = contacto;
    }




}


