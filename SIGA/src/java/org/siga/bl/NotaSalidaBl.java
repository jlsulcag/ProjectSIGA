
package org.siga.bl;

import java.util.List;
import org.siga.be.NotaSalida;
import org.siga.dao.NotaSalidaDao;
import org.siga.util.AbstractBL;
import org.siga.util.AbstractDA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("notaSalidaBl")
public class NotaSalidaBl extends AbstractBL<NotaSalida>{

    @Autowired
    @Qualifier("notaSalidaDao")
    private NotaSalidaDao dao;
    
    @Override
    public AbstractDA getDAO() {
        return dao;
    }

    @Override
    public void setDA(AbstractDA dao) {
        this.dao = (NotaSalidaDao) dao;
    }

    @Override
    public long registrar(NotaSalida bean) {
        return save(bean);
    }

    @Override
    public long actualizar(NotaSalida bean) {
        return update(bean);
    }

    @Override
    public long eliminar(NotaSalida bean) {
        return delete(bean);
    }

    @Override
    public List<NotaSalida> listar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<NotaSalida> listar(String ref) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<NotaSalida> listar(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public NotaSalida buscar(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public NotaSalida buscar(String ref) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long id() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int maxNumero() {
        return dao.maxNumero();
    }
    
}
