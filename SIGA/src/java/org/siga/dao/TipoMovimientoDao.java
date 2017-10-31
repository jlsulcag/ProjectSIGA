
package org.siga.dao;

import java.util.List;
import org.siga.be.TipoMovimiento;
import org.siga.util.AbstractDA;
import org.springframework.stereotype.Repository;

@Repository("tipoMovimientoDao")
public class TipoMovimientoDao extends AbstractDA<TipoMovimiento>{

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
        return list(TipoMovimiento.class);
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
