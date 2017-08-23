
package org.siga.bl;

import java.util.List;
import org.siga.be.OrdenCompraDetalle;
import org.siga.dao.OrdenCompraDetalleDao;
import org.siga.util.AbstractBL;
import org.siga.util.AbstractDA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("ordenCompraDetalleBl")
public class OrdenCompraDetalleBl extends AbstractBL<OrdenCompraDetalle>{
    @Autowired
    @Qualifier("ordenCompraDetalleDao")
    private OrdenCompraDetalleDao dao;

    @Override
    public AbstractDA getDAO() {
        return dao;
    }

    @Override
    public void setDA(AbstractDA dao) {
        this.dao = (OrdenCompraDetalleDao) dao;
    }

    @Override
    public long registrar(OrdenCompraDetalle bean) {
        return save(bean);
    }

    @Override
    public long actualizar(OrdenCompraDetalle bean) {
        return update(bean);
    }

    @Override
    public long eliminar(OrdenCompraDetalle bean) {
        return delete(bean);
    }

    @Override
    public List<OrdenCompraDetalle> listar() {
        return list();
    }

    @Override
    public List<OrdenCompraDetalle> listar(String ref) {
        return list(ref);
    }

    @Override
    public List<OrdenCompraDetalle> listar(long id) {
        return list(id);
    }

    @Override
    public OrdenCompraDetalle buscar(long id) {
        return search(id);
    }

    @Override
    public OrdenCompraDetalle buscar(String ref) {
        return search(ref);
    }

    @Override
    public long id() {
        return maxId();
    }

    public List<OrdenCompraDetalle> listarFull() {
        return dao.listarFull();
    }

    public List<OrdenCompraDetalle> listarXIdOrdenCompra(long id) {
        return dao.listarXIdOrdenCompra(id);
    }
    
    
}
