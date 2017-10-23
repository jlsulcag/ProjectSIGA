
package org.siga.bl;

import java.util.List;
import org.siga.be.Permiso;
import org.siga.dao.PermisoDao;
import org.siga.util.AbstractBL;
import org.siga.util.AbstractDA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("permisoBl")
public class PermisoBl extends AbstractBL<Permiso>{
    
    @Autowired
    @Qualifier("permisoDao")
    private PermisoDao dao;

    @Override
    public AbstractDA getDAO() {
        return dao;
    }

    @Override
    public void setDA(AbstractDA dao) {
        this.dao = (PermisoDao) dao;
    }

    @Override
    public long registrar(Permiso bean) {
        return save(bean);
    }

    @Override
    public long actualizar(Permiso bean) {
        return update(bean);
    }

    @Override
    public long eliminar(Permiso bean) {
        return delete(bean);
    }

    @Override
    public List<Permiso> listar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Permiso> listar(String ref) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Permiso> listar(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Permiso buscar(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Permiso buscar(String ref) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long id() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
