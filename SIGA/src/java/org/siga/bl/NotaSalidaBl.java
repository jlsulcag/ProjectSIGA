
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
        return list();
    }

    @Override
    public List<NotaSalida> listar(String ref) {
        return list(ref);
    }

    @Override
    public List<NotaSalida> listar(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public NotaSalida buscar(long id) {
        return search(id);
    }

    @Override
    public NotaSalida buscar(String ref) {
        return search(ref);
    }

    @Override
    public long id() {
        return maxId();
    }

    public int maxNumero() {
        return dao.maxNumero();
    }

    public NotaSalida buscarxIdPedido(long idpedido) {
        return dao.buscarxIdPedido(idpedido);
    }
    
}
