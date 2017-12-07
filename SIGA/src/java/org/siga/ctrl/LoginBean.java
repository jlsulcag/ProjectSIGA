/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.siga.ctrl;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.siga.be.Rol;
import org.siga.be.Usuario;
import org.siga.be.UsuarioRol;
import org.siga.bl.RolBl;
import org.siga.bl.UsuarioBl;
import org.siga.bl.UsuarioRolBl;
import org.siga.util.Encrypt;

@ManagedBean
@SessionScoped
public class LoginBean implements Serializable{

    @ManagedProperty(value = "#{usuarioBl}")
    private UsuarioBl usuarioBl;
    @ManagedProperty(value = "#{usuario}")
    private Usuario usuario;

    @ManagedProperty(value = "#{usuarioRolBl}")
    private UsuarioRolBl usuarioRolBl;
    @ManagedProperty(value = "#{usuarioRol}")
    private UsuarioRol usuarioRol;

    @ManagedProperty(value = "#{rolBl}")
    private RolBl rolBl;
    @ManagedProperty(value = "#{rol}")
    private Rol rol;

    private String nombreUsuario;
    private String contrasenia;

    public LoginBean() {
        HttpSession sesion = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        sesion.setMaxInactiveInterval(5000);
    }

    public String login() {
        String url = "";
        //Obtenemos los datos del usuario
        Usuario temp = getUsuarioBl().buscarxUsuario(this.getNombreUsuario().trim().toUpperCase());

        if (temp != null && temp.getIdusuario() > 0) {
            //guardamos en sesion usuario
            HttpSession sesionUser = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            sesionUser.setAttribute("idUsuario", temp.getIdusuario());
            sesionUser.setAttribute("usuario", temp);
            //Obtenemos los datos del usuario vinculado al rol
            usuarioRol = usuarioRolBl.buscarxIdUsuario(temp.getIdusuario());
            //Obtenemos el rol del usuario que ingresa, para establecer las paginas de acceso
            //rol = rolBl.buscar("" + getUsuarioRol().getRol().getIdrol());
            if (usuarioRol.getIdusuariorol() > 0) {
                if (temp.getEstado().equals("ACT")) {
                    if (temp.getContrasenia().equals(Encrypt.sha512(this.getContrasenia().toUpperCase()))) {
                        HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
                        httpSession.setAttribute("nombreUsuario", this.nombreUsuario);
                    //FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Atención", "Bienvenido " + temp.getNombreUsuario());
                        //FacesContext.getCurrentInstance().addMessage(null, message);
                        //rol = rolBl.buscar(usuarioRol.getRol().getIdrol());
                        if(usuarioRol.getRol().getIdrol()>0){
                            switch (usuarioRol.getRol().getRol().trim()) {
                            case "ALMACEN":
                                url = "/page/InicioAlmacen";
                                break;
                            case "SISTEMAS":
                                url = "/page/InicioAlmacen";
                                break;
                            case "ADMINISTRADOR":
                                url = "/page/InicioAlmacen";
                                break;
                        }
                        }else{
                            System.out.println("sin rolesss");
                        }
                        
                    } else {
                        System.out.println("Contraseña incorrecta ..........");
                        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error de acceso", "Usuario y/o contraseña incorrectos");
                        FacesContext.getCurrentInstance().addMessage(null, message);
                        url = "/page/login_1";
                    }
                } else {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error de acceso", "Su cuenta aún no esta activada, pongase en contacto con el administrador");
                    FacesContext.getCurrentInstance().addMessage(null, message);
                    url = "/page/login_1";
                }
            }else{
                System.out.println(".........");
            }

        } else {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error de acceso", "Usuario y/o contraseña incorrectos");
            FacesContext.getCurrentInstance().addMessage(null, message);
            url = "/page/login_1";
        }
        return url + "?faces-redirect=true";
    }

    public String cerrarSesion() {
        this.nombreUsuario = null;
        this.contrasenia = null;
        HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        httpSession.invalidate();
        return "/page/Login?faces-redirect=true";
    }

    public UsuarioBl getUsuarioBl() {
        return usuarioBl;
    }

    public void setUsuarioBl(UsuarioBl usuarioBl) {
        this.usuarioBl = usuarioBl;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public UsuarioRolBl getUsuarioRolBl() {
        return usuarioRolBl;
    }

    public void setUsuarioRolBl(UsuarioRolBl usuarioRolBl) {
        this.usuarioRolBl = usuarioRolBl;
    }

    public UsuarioRol getUsuarioRol() {
        return usuarioRol;
    }

    public void setUsuarioRol(UsuarioRol usuarioRol) {
        this.usuarioRol = usuarioRol;
    }

    public RolBl getRolBl() {
        return rolBl;
    }

    public void setRolBl(RolBl rolBl) {
        this.rolBl = rolBl;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

}
