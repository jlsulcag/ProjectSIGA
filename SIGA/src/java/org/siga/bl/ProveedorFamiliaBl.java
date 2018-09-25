
package org.siga.bl;

import java.util.List;
import org.siga.be.ProveedorFamilia;
import org.siga.dao.ProveedorFamiliaDao;
import org.siga.util.AbstractBL;
import org.siga.util.AbstractDA;
import org.springframework.stereotype.Service;
@Service("proveedorFamiliaBl")
public class ProveedorFamiliaBl extends AbstractBL<ProveedorFamilia>{
    private ProveedorFamiliaDao dao;
    
    @Override
    public AbstractDA getDAO() {
        return dao;
    }

    @Override
    public void setDA(AbstractDA dao) {
        this.dao = (ProveedorFamiliaDao) dao;
    }

    @Override
    public long registrar(ProveedorFamilia bean) {
        return save(bean);
    }

    @Override
    public long actualizar(ProveedorFamilia bean) {
        return update(bean);
    }

    @Override
    public long eliminar(ProveedorFamilia bean) {
        return delete(bean);
    }

    @Override
    public List<ProveedorFamilia> listar() {
        return list();
    }

    @Override
    public List<ProveedorFamilia> listar(String ref) {
        return list(ref);
    }

    @Override
    public List<ProveedorFamilia> listar(long id) {
        return list(id);
    }

    @Override
    public ProveedorFamilia buscar(long id) {
        return search(id);
    }

    @Override
    public ProveedorFamilia buscar(String ref) {
        return search(ref);
    }

    @Override
    public long id() {
        return maxId();
    }
    
}
