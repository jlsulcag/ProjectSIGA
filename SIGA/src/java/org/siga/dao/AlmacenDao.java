
package org.siga.dao;

import java.util.List;
import org.siga.be.Almacen;
import org.siga.util.AbstractDA;
import org.springframework.stereotype.Repository;
@Repository("almacenDao")
public class AlmacenDao extends AbstractDA<Almacen>{

    @Override
    public long registrar(Almacen bean) {
        return save(bean);
    }

    @Override
    public long actualizar(Almacen bean) {
        return update(bean);
    }

    @Override
    public long eliminar(Almacen bean) {
        return delete(bean);
    }

    @Override
    public List<Almacen> listar() {
        return list(Almacen.class);
    }

    @Override
    public List<Almacen> listar(String ref) {
        return list("from Almacen a where a.nombre like '%"+ref+"%'");
    }

    @Override
    public List<Almacen> listar(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Almacen buscar(long id) {
        return search(Almacen.class, id);
    }

    @Override
    public Almacen buscar(String ref) {
        return search(ref);
    }

    @Override
    public long id() {
        return maxId(Almacen.class);
    }
    
}
