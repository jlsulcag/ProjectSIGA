
package org.siga.dao;

import java.util.List;
import org.siga.be.PedidoSeguimiento;
import org.siga.util.AbstractDA;
import org.springframework.stereotype.Repository;

@Repository("pedidoSeguimientoDao")
public class PedidoSeguimientoDao extends AbstractDA<PedidoSeguimiento>{

    @Override
    public long registrar(PedidoSeguimiento bean) {
        return save(bean);
    }

    @Override
    public long actualizar(PedidoSeguimiento bean) {
        return update(bean);
    }

    @Override
    public long eliminar(PedidoSeguimiento bean) {
        return delete(bean);
    }

    @Override
    public List<PedidoSeguimiento> listar() {
        return list(PedidoSeguimiento.class);
    }

    @Override
    public List<PedidoSeguimiento> listar(String ref) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<PedidoSeguimiento> listar(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PedidoSeguimiento buscar(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PedidoSeguimiento buscar(String ref) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long id() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
