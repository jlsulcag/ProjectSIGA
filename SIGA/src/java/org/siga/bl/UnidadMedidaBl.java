
package org.siga.bl;

import java.util.List;
import org.siga.be.UnidadMedida;
import org.siga.dao.UnidadMedidaDao;
import org.siga.util.AbstractBL;
import org.siga.util.AbstractDA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class UnidadMedidaBl extends AbstractBL<UnidadMedida>{
    @Autowired
    @Qualifier("unidadMedidaDao")
    private UnidadMedidaDao dao;

    @Override
    public AbstractDA getDAO() {
        return dao;
    }

    @Override
    public void setDA(AbstractDA dao) {
        this.dao = (UnidadMedidaDao) dao;
    }

    @Override
    public long registrar(UnidadMedida bean) {
        return save(bean);
    }

    @Override
    public long actualizar(UnidadMedida bean) {
        return update(bean);
    }

    @Override
    public long eliminar(UnidadMedida bean) {
        return delete(bean);
    }

    @Override
    public List<UnidadMedida> listar() {
        return list();
    }

    @Override
    public List<UnidadMedida> listar(String ref) {
        return list(ref);
    }

    @Override
    public List<UnidadMedida> listar(long id) {
        return list(id);
    }

    @Override
    public UnidadMedida buscar(long id) {
        return search(id);
    }

    @Override
    public UnidadMedida buscar(String ref) {
        return search(ref);
    }

    @Override
    public long id() {
        return maxId();
    }
    
}
