
package org.siga.bl;

import java.util.List;
import org.siga.be.Rol;
import org.siga.dao.RolDao;
import org.siga.util.AbstractBL;
import org.siga.util.AbstractDA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("rolBl")
public class RolBl extends AbstractBL<Rol>{
    @Autowired
    @Qualifier("rolDao")
    private RolDao dao;
    
    @Override
    public AbstractDA getDAO() {
        return dao;
    }

    @Override
    public void setDA(AbstractDA dao) {
       this.dao = (RolDao)dao;
    }

    @Override
    public long registrar(Rol bean) {
        return save(bean);
    }

    @Override
    public long actualizar(Rol bean) {
        return update(bean);
    }

    @Override
    public long eliminar(Rol bean) {
        return delete(bean);
    }

    @Override
    public List<Rol> listar() {
        return list();
    }

    @Override
    public List<Rol> listar(String ref) {
        return list(ref);
    }

    @Override
    public List<Rol> listar(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Rol buscar(long id) {
        return search(id);
    }

    @Override
    public Rol buscar(String ref) {
        return search(ref);
    }

    @Override
    public long id() {
        return maxId();
    }
    
}
