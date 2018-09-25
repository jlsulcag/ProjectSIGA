
package org.siga.ctrl;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.view.ViewScoped;
import org.siga.bl.ProveedorFamiliaBl;

@ManagedBean
@ViewScoped
public class ProveedorFamiliaBean {
    @ManagedProperty(value = "#{proveedorFamiliaBl}")
    private ProveedorFamiliaBl proveedorFamiliaBl;

    public ProveedorFamiliaBean() {
    }
    
}
