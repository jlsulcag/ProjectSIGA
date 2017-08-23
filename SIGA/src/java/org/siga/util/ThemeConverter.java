package org.siga.util;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import org.siga.be.Proveedor;
import org.siga.bl.ProveedorBl;
import org.siga.dao.ProveedorDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

@FacesConverter(value = "themeConverter")
public class ThemeConverter implements Converter {

//    @ManagedProperty(value = "#{proveedor}")
//    private Proveedor proveedor;
//    @ManagedProperty(value = "#{proveedorBl}")
//    private ProveedorBl proveedorBl;
    private ProveedorBl proveedorBl;
    private Proveedor proveedor;

    @Override
    public Proveedor getAsObject(FacesContext context, UIComponent component, String value) {
        System.out.println("value ... "+value);
        if(value != null && value.trim().length() > 0) {
            try {
                proveedorBl = new ProveedorBl();
                return proveedorBl.buscarxID(Long.parseLong(value));
            } catch(NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid theme."));
            }
        }
        else {
            return null;
        }
//        proveedorBl = new ProveedorBl();
//        for (Proveedor p : proveedorBl.listarxID(Long.valueOf(value))) {
//            if (p.getIdproveedor() == Long.valueOf(value)) {
//                return p;
//            }
//        proveedor = proveedorBl.buscar(Long.parseLong(value));
//        
//        }
//        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object object) {
        if (object instanceof Proveedor) {
            Proveedor pro = (Proveedor) object;
            return pro.getIdproveedor()+"";
        }
        return "";
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

}
