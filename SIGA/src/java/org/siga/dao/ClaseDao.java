
package org.siga.dao;

import java.util.List;
import org.siga.be.Clase;
import org.siga.util.AbstractDA;
import org.springframework.stereotype.Repository;

@Repository("claseDao")
public class ClaseDao extends AbstractDA<Clase>{

    @Override
    public long registrar(Clase bean) {
        return save(bean);
    }

    @Override
    public long actualizar(Clase bean) {
        return update(bean);
    }

    @Override
    public long eliminar(Clase bean) {
        return delete(bean);
    }

    @Override
    public List<Clase> listar() {
        return list(Clase.class);
    }

    @Override
    public List<Clase> listar(String ref) {
        return list(ref);
    }

    @Override
    public List<Clase> listar(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Clase buscar(long id) {
        return search(Clase.class, id);
    }

    @Override
    public Clase buscar(String ref) {
        return search(ref);
    }

    @Override
    public long id() {
        return maxId(Clase.class);
    }

    public List<Clase> listarFull(String string) {
        string = "from Clase a left join fetch a.familia";
        return listar(string);
    }

    public List<Clase> listarClasePorFamilia(long idFam) {
        String hql = "select a from Clase a left join fetch a.familia b where b.idfamilia = "+idFam;
        return listar(hql);
    }
    
}
