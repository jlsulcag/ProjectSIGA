
package org.siga.dao;

import java.util.List;
import org.siga.be.ProveedorFamilia;
import org.siga.util.AbstractDA;
import org.springframework.stereotype.Repository;
@Repository("proveedorFamiliaDao")
public class ProveedorFamiliaDao extends AbstractDA<ProveedorFamilia>{

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
        return list(ProveedorFamilia.class);
    }

    @Override
    public List<ProveedorFamilia> listar(String ref) {
        return list(ref);
    }

    @Override
    public List<ProveedorFamilia> listar(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ProveedorFamilia buscar(long id) {
        return search(ProveedorFamilia.class, id);
    }

    @Override
    public ProveedorFamilia buscar(String ref) {
        return search(ref);
    }

    @Override
    public long id() {
        return maxId(ProveedorFamilia.class);
    }
    
}
