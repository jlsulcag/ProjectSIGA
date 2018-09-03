
package org.siga.bl;

import java.util.List;
import org.siga.be.OrdenCompraEstados;
import org.siga.dao.OrdenCompraEstadosDao;
import org.siga.util.AbstractBL;
import org.siga.util.AbstractDA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("ordenCompraEstadosBl")
public class OrdenCompraEstadosBl extends AbstractBL<OrdenCompraEstados>{

    @Autowired
    @Qualifier("ordenCompraEstadosDao")
    private OrdenCompraEstadosDao dao;
    
    @Override
    public AbstractDA getDAO() {
        return dao;
    }

    @Override
    public void setDA(AbstractDA dao) {
        this.dao = (OrdenCompraEstadosDao) dao;
    }

    @Override
    public long registrar(OrdenCompraEstados bean) {
        return save(bean);
    }

    @Override
    public long actualizar(OrdenCompraEstados bean) {
        return update(bean);
    }

    @Override
    public long eliminar(OrdenCompraEstados bean) {
        return delete(bean);
    }

    @Override
    public List<OrdenCompraEstados> listar() {
        return list();
    }

    @Override
    public List<OrdenCompraEstados> listar(String ref) {
        return list(ref);
    }

    @Override
    public List<OrdenCompraEstados> listar(long id) {
        return list(id);
    }

    @Override
    public OrdenCompraEstados buscar(long id) {
        return search(id);
    }

    @Override
    public OrdenCompraEstados buscar(String ref) {
        return search(ref);
    }

    @Override
    public long id() {
        return maxId();
    }
    
}
