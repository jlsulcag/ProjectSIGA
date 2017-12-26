
package org.siga.dao;

import java.util.List;
import org.siga.be.Equivalencia;
import org.siga.util.AbstractDA;
import org.springframework.stereotype.Repository;
@Repository("equivalenciaDao")
public class EquivalenciaDao extends AbstractDA<Equivalencia>{

    @Override
    public long registrar(Equivalencia bean) {
        return save(bean);
    }

    @Override
    public long actualizar(Equivalencia bean) {
        return update(bean);
    }

    @Override
    public long eliminar(Equivalencia bean) {
        return delete(bean);
    }

    @Override
    public List<Equivalencia> listar() {
        return list(Equivalencia.class);
    }

    @Override
    public List<Equivalencia> listar(String ref) {
        return list(ref);
    }

    @Override
    public List<Equivalencia> listar(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Equivalencia buscar(long id) {
        return search(Equivalencia.class, id);
    }

    @Override
    public Equivalencia buscar(String ref) {
        return search(ref);
    }

    @Override
    public long id() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<Equivalencia> listarXidUnidadMedida(long idunidadmedida) {
        String hql = "from Equivalencia a left join fetch a.unidadMedida b left join fetch a.unidadEquivalente c where b.idunidadmedida = "+idunidadmedida;
        return listar(hql);
    }

    public List<Equivalencia> listarFull() {
        String hql = "from Equivalencia a left join fetch a.unidadMedida b left join fetch a.unidadEquivalente c order by b.descripcion asc";
        return listar(hql);
    }

    public Equivalencia buscaxId(long idEquivalencia) {
        String hql = "from Equivalencia a left join fetch a.unidadMedida b left join fetch a.unidadEquivalente c where a.idequivalencia = "+idEquivalencia;
        return buscar(hql);
    }
    
}
