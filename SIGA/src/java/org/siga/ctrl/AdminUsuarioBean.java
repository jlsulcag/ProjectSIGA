/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.siga.ctrl;

import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.siga.be.Persona;
import org.siga.be.Usuario;
import org.siga.be.UsuarioRol;
import org.siga.bl.PersonaBl;
import org.siga.bl.UsuarioBl;
import org.siga.bl.UsuarioRolBl;
import org.siga.util.MensajeView;

@ManagedBean
@ViewScoped
public class AdminUsuarioBean {
    @ManagedProperty(value = "#{persona}")
    private Persona persona;
    @ManagedProperty(value = "#{personaBl}")
    private PersonaBl personaBl;
    
    @ManagedProperty(value = "#{usuario}")
    private Usuario usuario;
    @ManagedProperty(value = "#{usuarioBl}")
    private UsuarioBl usuarioBl;
    
    @ManagedProperty(value = "#{usuarioRol}")
    private UsuarioRol usuarioRol;
    @ManagedProperty(value = "#{usuarioRolBl}")
    private UsuarioRolBl usuarioRolBl;
    
    private String txtBusqueda;
    private List<Usuario> listUsuarios;
    
    public AdminUsuarioBean() {
    }
    
    public void registrar(){
        long res = -1;
        persona.setNombre(persona.getNombre().toUpperCase());
        persona.setApellidoPaterno(persona.getApellidoPaterno().toUpperCase());
        persona.setApellidoMaterno(persona.getApellidoMaterno().toUpperCase());
        persona.setSexo(persona.getSexo().toUpperCase());
        persona.setCorreoElectronico(persona.getCorreoElectronico().toUpperCase());
        res = personaBl.registrar(persona);
        if(res == 0){
            long res2 = -1;
            usuario.setNombre(usuario.getNombre().trim().toUpperCase());
            usuario.setContrasenia(usuario.getContrasenia().toUpperCase());
            usuario.setDescripcion("");
            usuario.setEstado("ACT");
            usuario.setPersona(persona);
            res2 = usuarioBl.registrar(usuario);
            if(res2 == 0){
                long res3 = -1;
                //REGISTRAR USUARIO ROL
                usuarioRol.setUsuario(usuario);
                usuarioRol.setRol(usuarioRol.getRol());
                usuarioRol.setFechaReg(new Date());
                usuarioRol.setDescripcion("");
                usuarioRol.setEstado(Boolean.TRUE);
                
                res3 = usuarioRolBl.registrar(usuarioRol);
                if(res3 == 0){
                    MensajeView.registroCorrecto();
                }else{
                    MensajeView.registroError();
                }
            }
        }
        listarFull();
        
        
    }
    
    public void limpiarNew(){
        usuario.setIdusuario(0);
        //usuario.setDependencia(null);        
        persona.setNombre("");
        persona.setApellidoPaterno("");
        persona.setApellidoMaterno("");
        persona.setNumeroDocumento("");
        persona.setSexo("");
        persona.setTelefono("");
        persona.setCorreoElectronico("");
        usuario.setNombre("");
        usuario.setContrasenia("");
        //usuarioRol.setRol(null);
    }
    
    @PostConstruct
    public void listarFull(){
        setListUsuarios(usuarioBl.listarFull());
    }
    
    public void listarRef(){
        setListUsuarios(usuarioBl.listarRef(txtBusqueda.toUpperCase()));
    }
    
    public void listarxNombres(){
        setListUsuarios(usuarioBl.listarxNombres(txtBusqueda.toUpperCase()));
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public PersonaBl getPersonaBl() {
        return personaBl;
    }

    public void setPersonaBl(PersonaBl personaBl) {
        this.personaBl = personaBl;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public UsuarioBl getUsuarioBl() {
        return usuarioBl;
    }

    public void setUsuarioBl(UsuarioBl usuarioBl) {
        this.usuarioBl = usuarioBl;
    }

    public UsuarioRol getUsuarioRol() {
        return usuarioRol;
    }

    public void setUsuarioRol(UsuarioRol usuarioRol) {
        this.usuarioRol = usuarioRol;
    }

    public UsuarioRolBl getUsuarioRolBl() {
        return usuarioRolBl;
    }

    public void setUsuarioRolBl(UsuarioRolBl usuarioRolBl) {
        this.usuarioRolBl = usuarioRolBl;
    }

    public String getTxtBusqueda() {
        return txtBusqueda;
    }

    public void setTxtBusqueda(String txtBusqueda) {
        this.txtBusqueda = txtBusqueda;
    }

    public List<Usuario> getListUsuarios() {
        return listUsuarios;
    }

    public void setListUsuarios(List<Usuario> listUsuarios) {
        this.listUsuarios = listUsuarios;
    }
    
}
