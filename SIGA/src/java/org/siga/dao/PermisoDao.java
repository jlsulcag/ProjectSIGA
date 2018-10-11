
package org.siga.dao;

import java.util.List;
import org.siga.be.Permiso;
import org.siga.util.AbstractDA;
import org.springframework.stereotype.Repository;

@Repository("permisoDao")
public class PermisoDao extends AbstractDA<Permiso>{

    @Override
    public long registrar(Permiso bean) {
        return save(bean);
    }

    @Override
    public long actualizar(Permiso bean) {
        return update(bean);
    }

    @Override
    public long eliminar(Permiso bean) {
        return delete(bean);
    }

    @Override
    public List<Permiso> listar() {
        return list(Permiso.class);
    }

    @Override
    public List<Permiso> listar(String ref) {
        return list(ref);
    }

    @Override
    public List<Permiso> listar(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Permiso buscar(long id) {
        return search(Permiso.class, id);
    }

    @Override
    public Permiso buscar(String ref) {
        return search(ref);
    }

    @Override
    public long id() {
        return maxId(Permiso.class);
    }

    public Permiso buscarxCodigo(int cod) {
        String hql = "from Permiso a where a.codigo = "+cod;
        return buscar(hql);
    }
    
}
