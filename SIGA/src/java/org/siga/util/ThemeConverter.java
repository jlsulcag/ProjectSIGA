package org.siga.util;

import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.siga.be.Proveedor;
import org.siga.bl.ProveedorBl;

@FacesConverter("themeConverter")
public class ThemeConverter implements Converter {

    @ManagedProperty(value = "#{proveedor}")
    private Proveedor proveedor;
    @ManagedProperty(value = "#{proveedorBl}")
    private ProveedorBl proveedorBl;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        for (Proveedor p : proveedorBl.listar()) {
            if (p.getIdproveedor() == Long.valueOf(value)) {
                return p;
            }
        }
        return null;
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
