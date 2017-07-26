
package org.siga.ctrl;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.siga.be.Proveedor;
import org.siga.bl.ProveedorBl;
import org.siga.util.MensajeView;

@ManagedBean
@ViewScoped
public class ProveedorBean {

    @ManagedProperty(value = "#{proveedor}")
    private Proveedor proveedor;
    @ManagedProperty(value = "#{proveedorBl}")
    private ProveedorBl proveedorBl;
    
    private List<Proveedor> listProveedores;
    private String txtBusqueda;
    private long res;
            
    public ProveedorBean() {
    }
    
    public void registrar(){
        proveedor.setCodigo(proveedor.getCodigo().toUpperCase());
        proveedor.setRazonSocial(proveedor.getRazonSocial().toUpperCase());
        proveedor.setRuc(proveedor.getRuc().toUpperCase());
        proveedor.setDireccion(proveedor.getDireccion().toUpperCase());
        proveedor.setTelefono(proveedor.getTelefono().toUpperCase());
        proveedor.setEmail(proveedor.getEmail().toUpperCase());
        proveedor.setContacto(proveedor.getContacto().toUpperCase());
        
        res = proveedorBl.registrar(proveedor);
        if(res == 0){
            MensajeView.registroCorrecto();
        }else{
            MensajeView.registroError();
        }
        listar();
        
    }
    
    public void limpiar(){
        proveedor.setIdproveedor(0);
        proveedor.setCodigo("");
        proveedor.setRazonSocial("");
        proveedor.setRuc("");
        proveedor.setDireccion("");
        proveedor.setTelefono("");
        proveedor.setEmail("");
        proveedor.setContacto("");
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public ProveedorBl getProveedorBl() {
        return proveedorBl;
    }

    public void setProveedorBl(ProveedorBl proveedorBl) {
        this.proveedorBl = proveedorBl;
    }

    public List<Proveedor> getListProveedores() {
        return listProveedores;
    }

    public void setListProveedores(List<Proveedor> listProveedores) {
        this.listProveedores = listProveedores;
    }

    public String getTxtBusqueda() {
        return txtBusqueda;
    }

    public void setTxtBusqueda(String txtBusqueda) {
        this.txtBusqueda = txtBusqueda;
    }
    
    @PostConstruct
    private void listar() {
        setListProveedores(proveedorBl.listar());
    }
    
}