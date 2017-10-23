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
import org.siga.bl.PersonaBl;

@ManagedBean
@ViewScoped
public class AdminUsuarioBean {
    @ManagedProperty(value = "#{persona}")
    private Persona persona;
    @ManagedProperty(value = "#{personaBl}")
    private PersonaBl personaBl;
    
    @ManagedProperty(value = "#{usuario}")
    private Usuario usuario;
    
    public AdminUsuarioBean() {
    }
    
}
