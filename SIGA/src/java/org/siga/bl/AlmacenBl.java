
package org.siga.bl;

import java.util.List;
import org.siga.be.Almacen;
import org.siga.dao.AlmacenDao;
import org.siga.util.AbstractBL;
import org.siga.util.AbstractDA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
@Service("almacenBl")
public class AlmacenBl extends AbstractBL<Almacen>{
    @Autowired
    @Qualifier("almacenDao")
    private AlmacenDao dao;
    @Override
    public AbstractDA getDAO() {
        return dao;
    }

    @Override
    public void setDA(AbstractDA dao) {
        this.dao = (AlmacenDao) dao;
    }

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
        return list();
    }

    @Override
    public List<Almacen> listar(String ref) {
        return list(ref);
    }

    @Override
    public List<Almacen> listar(long id) {
        return list(id);
    }

    @Override
    public Almacen buscar(long id) {
        return search(id);
    }

    @Override
    public Almacen buscar(String ref) {
        return search(ref);
    }

    @Override
    public long id() {
        return maxId();
    }

    public Almacen buscarXNombre(String ref) {
        return dao.buscarXNombre(ref);
    }
    
}
