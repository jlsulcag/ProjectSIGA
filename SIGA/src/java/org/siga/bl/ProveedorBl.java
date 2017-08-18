
package org.siga.bl;

import java.util.List;
import org.siga.be.Proveedor;
import org.siga.dao.ProveedorDao;
import org.siga.util.AbstractBL;
import org.siga.util.AbstractDA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("proveedorBl")
public class ProveedorBl extends AbstractBL<Proveedor>{

    @Autowired
    @Qualifier("proveedorDao")
    private ProveedorDao dao;
    
    @Override
    public AbstractDA getDAO() {
        return dao;
    }

    @Override
    public void setDA(AbstractDA dao) {
        this.dao = (ProveedorDao) dao;
    }

    @Override
    public long registrar(Proveedor bean) {
        return save(bean);
    }

    @Override
    public long actualizar(Proveedor bean) {
        return update(bean);
    }

    @Override
    public long eliminar(Proveedor bean) {
        return delete(bean);
    }

    @Override
    public List<Proveedor> listar() {
        return list();
    }

    @Override
    public List<Proveedor> listar(String ref) {
        return list(ref);
    }

    @Override
    public List<Proveedor> listar(long id) {
        return list(id);
    }

    @Override
    public Proveedor buscar(long id) {
        return search(id);
    }

    @Override
    public Proveedor buscar(String ref) {
        return search(ref);
    }

    @Override
    public long id() {
        return maxId();
    }

    public List<Proveedor> buscarRef(String txtBusqueda) {
        return dao.buscarRef(txtBusqueda);
    }
    
}
