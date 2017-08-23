
package org.siga.bl;

import java.util.List;
import org.siga.be.OrdenCompra;
import org.siga.dao.OrdenCompraDao;
import org.siga.util.AbstractBL;
import org.siga.util.AbstractDA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
@Service("ordenCompraBl")
public class OrdenCompraBl extends AbstractBL<OrdenCompra>{
    @Autowired
    @Qualifier("ordenCompraDao")
    private OrdenCompraDao dao;
    @Override
    public AbstractDA getDAO() {
        return dao;
    }

    @Override
    public void setDA(AbstractDA dao) {
        this.dao  = (OrdenCompraDao) dao;
    }

    @Override
    public long registrar(OrdenCompra bean) {
        return save(bean);
    }

    @Override
    public long actualizar(OrdenCompra bean) {
        return update(bean);
    }

    @Override
    public long eliminar(OrdenCompra bean) {
        return delete(bean);
    }

    @Override
    public List<OrdenCompra> listar() {
        return list();
    }

    @Override
    public List<OrdenCompra> listar(String ref) {
        return list(ref);
    }

    @Override
    public List<OrdenCompra> listar(long id) {
        return list(id);
    }

    @Override
    public OrdenCompra buscar(long id) {
        return search(id);
    }

    @Override
    public OrdenCompra buscar(String ref) {
        return search(ref);
    }

    @Override
    public long id() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public OrdenCompraDao getDao() {
        return dao;
    }

    public void setDao(OrdenCompraDao dao) {
        this.dao = dao;
    }

    public List<OrdenCompra> listarFull(String string) {
        return dao.listarFull(string);
    }

    public long buscarUltimoNumero() {
        return dao.buscarUltimoNumero();
    }
    
}
