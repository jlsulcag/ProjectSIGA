
package org.siga.bl;

import java.util.List;
import org.siga.be.Menu;
import org.siga.dao.MenuDao;
import org.siga.util.AbstractBL;
import org.siga.util.AbstractDA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("menuBl")
public class MenuBl extends AbstractBL<Menu>{

    @Autowired
    @Qualifier("menuDao")
    private MenuDao dao;
    
    @Override
    public AbstractDA getDAO() {
        return dao;
    }

    @Override
    public void setDA(AbstractDA dao) {
        this.dao = (MenuDao) dao;
    }

    @Override
    public long registrar(Menu bean) {
        return save(bean);
    }

    @Override
    public long actualizar(Menu bean) {
        return update(bean);
    }

    @Override
    public long eliminar(Menu bean) {
        return delete(bean);
    }

    @Override
    public List<Menu> listar() {
        return list();
    }

    @Override
    public List<Menu> listar(String ref) {
        return list(ref);
    }

    @Override
    public List<Menu> listar(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Menu buscar(long id) {
        return search(id);
    }

    @Override
    public Menu buscar(String ref) {
        return search(ref);
    }

    @Override
    public long id() {
        return maxId();
    }
    
}
