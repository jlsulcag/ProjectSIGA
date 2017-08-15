
package org.siga.ctrl;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.siga.be.OrdenCompra;
import org.siga.be.OrdenCompraDetalle;
import org.siga.bl.OrdenCompraBl;
import org.siga.bl.OrdenCompraDetalleBl;

@ManagedBean
@ViewScoped
public class OrdenCompraBean {
    @ManagedProperty(value = "#{ordenCompra]")
    private OrdenCompra ordencompra;    
    @ManagedProperty(value = "#{ordenCompraBl}")
    private OrdenCompraBl ordenCompraBl;    
    @ManagedProperty(value = "#{ordenCompraDetalle}")
    private OrdenCompraDetalle ordenCompraDetalle;    
    @ManagedProperty(value = "#{ordenCompraDetalleBl}")
    private OrdenCompraDetalleBl ordenCompraDetalleBl;    
    private List<OrdenCompraDetalle> listOrdenCompraDetalles;
    
    
    public OrdenCompraBean() {
    }
    
}
