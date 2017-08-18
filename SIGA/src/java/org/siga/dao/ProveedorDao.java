
package org.siga.dao;

import java.util.List;
import org.siga.be.Proveedor;
import org.siga.util.AbstractDA;
import org.springframework.stereotype.Repository;

@Repository("proveedorDao")
public class ProveedorDao extends AbstractDA<Proveedor>{

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
        return list(Proveedor.class);
    }

    @Override
    public List<Proveedor> listar(String ref) {
        return list(ref);
    }

    @Override
    public List<Proveedor> listar(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Proveedor buscar(long id) {
        return search(Proveedor.class, id);
    }

    @Override
    public Proveedor buscar(String ref) {
        return search(ref);
    }

    @Override
    public long id() {
        return maxId(Proveedor.class);
    }

    public List<Proveedor> buscarRef(String txtBusqueda) {
        String hql = "From Proveedor a where a.razonSocial like '%"+txtBusqueda+"%'";
        return listar(hql);
    }
    
}
