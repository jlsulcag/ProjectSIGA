
package org.siga.dao;

import java.util.List;
import org.siga.be.Dependencia;
import org.siga.util.AbstractDA;
import org.springframework.stereotype.Repository;

@Repository
public class DependenciaDao extends AbstractDA<Dependencia>{

    @Override
    public long registrar(Dependencia bean) {
        return save(bean);
    }

    @Override
    public long actualizar(Dependencia bean) {
        return update(bean);
    }

    @Override
    public long eliminar(Dependencia bean) {
        return delete(bean);
    }

    @Override
    public List<Dependencia> listar() {
        return list(Dependencia.class);
    }

    @Override
    public List<Dependencia> listar(String ref) {
        return list(ref);
    }

    @Override
    public List<Dependencia> listar(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Dependencia buscar(long id) {
        return search(Dependencia.class, id);
    }

    @Override
    public Dependencia buscar(String ref) {
        return search(ref);
    }

    @Override
    public long id() {
        return maxId(Dependencia.class);
    }

    public List<Dependencia> listarRef(String toUpperCase) {
        String hql = "From Dependencia d where d.descripcion like '%"+toUpperCase+"%'";
        return listar(hql);
        
    }
    
}
