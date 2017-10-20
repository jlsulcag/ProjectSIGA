
package org.siga.dao;

import java.util.List;
import org.siga.be.PedidoDetalle;
import org.siga.util.AbstractBL;
import org.siga.util.AbstractDA;
import org.springframework.stereotype.Repository;

@Repository("pedidoDetalleDao")
public class PedidoDetalleDao extends AbstractDA<PedidoDetalle>{

    @Override
    public long registrar(PedidoDetalle bean) {
        return save(bean);
    }

    @Override
    public long actualizar(PedidoDetalle bean) {
        return update(bean);
    }

    @Override
    public long eliminar(PedidoDetalle bean) {
        return delete(bean);
    }

    @Override
    public List<PedidoDetalle> listar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<PedidoDetalle> listar(String ref) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<PedidoDetalle> listar(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PedidoDetalle buscar(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PedidoDetalle buscar(String ref) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long id() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
   
    
}
