
package org.siga.bl;

import java.util.List;
import org.siga.be.TipoMovimiento;
import org.siga.dao.TipoMovimientoDao;
import org.siga.util.AbstractBL;
import org.siga.util.AbstractDA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("tipoMovimientoBl")
public class TipoMovimientoBl extends AbstractBL<TipoMovimiento>{
    @Autowired
    @Qualifier("tipoMovimientoDao")
    private TipoMovimientoDao dao;
    
    @Override
    public AbstractDA getDAO() {
        return dao;
    }

    @Override
    public void setDA(AbstractDA dao) {
        this.dao = (TipoMovimientoDao) dao;
    }

    @Override
    public long registrar(TipoMovimiento bean) {
        return save(bean);
    }

    @Override
    public long actualizar(TipoMovimiento bean) {
        return update(bean);
    }

    @Override
    public long eliminar(TipoMovimiento bean) {
        return delete(bean);
    }

    @Override
    public List<TipoMovimiento> listar() {
        return list();
    }

    @Override
    public List<TipoMovimiento> listar(String ref) {
        return list(ref);
    }

    @Override
    public List<TipoMovimiento> listar(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public TipoMovimiento buscar(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public TipoMovimiento buscar(String ref) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long id() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
