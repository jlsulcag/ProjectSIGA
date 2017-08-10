
package org.siga.ctrl;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.siga.be.Almacen;
import org.siga.bl.AlmacenBl;
import org.siga.util.MensajeView;

@ManagedBean
@ViewScoped
public class AlmacenBean {
    
    @ManagedProperty(value = "#{almacenBl}")
    private AlmacenBl almacenBl;
    @ManagedProperty(value = "#{almacen}")
    private Almacen almacen;
    
    private List<Almacen> listAlmacenes;
    
    private String txtBusqueda;
    private long res;
    
    public AlmacenBean() {
    }
    
    public void registrar(){
        almacen.setNombre(almacen.getNombre().toUpperCase());
        almacen.setDireccion(almacen.getDireccion().toUpperCase());
        res = almacenBl.registrar(almacen);
        if (res == 0) {
            MensajeView.registroCorrecto();
        } else {
            MensajeView.registroError();
        }
        listar();
    }
    
    public void actualizar(){
        Almacen temp = new Almacen();
        temp = buscarId();
        temp.setNombre(almacen.getNombre().toUpperCase());
        temp.setDireccion(almacen.getDireccion().toUpperCase());
        temp.setEstado(almacen.getEstado().toUpperCase());
        res = almacenBl.actualizar(temp);
        if (res == 0) {
            MensajeView.actCorrecto();
        } else {
            MensajeView.actError();
        }
        listar();
        
    }
    
    public void eliminar(){
        Almacen temp = new Almacen();
        temp = buscarId();
        res = almacenBl.eliminar(temp);
        if(res == 0){
            MensajeView.eliminacionCorrecta();
        }else{
            MensajeView.eliminacionErronea();
        }
        listar();
    }
    
    @PostConstruct
    public void listar(){
        setListAlmacenes(almacenBl.listar());
    }
    
    public void limpiar() {
        almacen.setIdalmacen(0);
        almacen.setNombre("");
        almacen.setDireccion("");
    }
    
    public void listarRef(){
        setListAlmacenes(almacenBl.listar(txtBusqueda));
    }

    public AlmacenBl getAlmacenBl() {
        return almacenBl;
    }

    public void setAlmacenBl(AlmacenBl almacenBl) {
        this.almacenBl = almacenBl;
    }

    public Almacen getAlmacen() {
        return almacen;
    }

    public void setAlmacen(Almacen almacen) {
        this.almacen = almacen;
    }

    public String getTxtBusqueda() {
        return txtBusqueda;
    }

    public void setTxtBusqueda(String txtBusqueda) {
        this.txtBusqueda = txtBusqueda;
    }

    private Almacen buscarId() {
        return almacenBl.buscar(almacen.getIdalmacen());
    }

    public List<Almacen> getListAlmacenes() {
        return listAlmacenes;
    }

    public void setListAlmacenes(List<Almacen> listAlmacenes) {
        this.listAlmacenes = listAlmacenes;
    }
    
}
