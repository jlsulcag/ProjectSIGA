
package org.siga.dao;

import java.util.List;
import org.siga.be.AlmacenProducto;
import org.siga.util.AbstractDA;
import org.springframework.stereotype.Repository;
@Repository("almacenProductoDao")
public class AlmacenProductoDao extends AbstractDA<AlmacenProducto>{

    @Override
    public long registrar(AlmacenProducto bean) {
        return save(bean);
    }

    @Override
    public long actualizar(AlmacenProducto bean) {
        return update(bean);
    }

    @Override
    public long eliminar(AlmacenProducto bean) {
        return delete(bean);
    }

    @Override
    public List<AlmacenProducto> listar() {
        return list(AlmacenProducto.class);
    }

    @Override
    public List<AlmacenProducto> listar(String ref) {
        return list(ref);
    }

    @Override
    public List<AlmacenProducto> listar(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public AlmacenProducto buscar(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public AlmacenProducto buscar(String ref) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long id() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
