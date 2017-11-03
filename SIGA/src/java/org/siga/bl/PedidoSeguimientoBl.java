
package org.siga.bl;

import java.util.List;
import org.siga.be.PedidoSeguimiento;
import org.siga.dao.PedidoSeguimientoDao;
import org.siga.util.AbstractBL;
import org.siga.util.AbstractDA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("pedidoSeguimientoBl")
public class PedidoSeguimientoBl extends AbstractBL<PedidoSeguimiento>{
    @Autowired
    @Qualifier("pedidoSeguimientoDao")
    private PedidoSeguimientoDao dao;

    @Override
    public AbstractDA getDAO() {
        return dao;
    }

    @Override
    public void setDA(AbstractDA dao) {
        this.dao = (PedidoSeguimientoDao) dao;
    }

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
        return list();
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
