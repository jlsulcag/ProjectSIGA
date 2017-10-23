/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.siga.ctrl;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.siga.be.Persona;
import org.siga.be.Usuario;
import org.siga.be.UsuarioRol;
import org.siga.bl.PersonaBl;
import org.siga.bl.UsuarioBl;
import org.siga.bl.UsuarioRolBl;

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
    
    public AdminUsuarioBean() {
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
    
}
