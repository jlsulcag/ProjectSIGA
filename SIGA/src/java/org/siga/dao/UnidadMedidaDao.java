
package org.siga.dao;

import java.util.List;
import org.siga.be.UnidadMedida;
import org.siga.util.AbstractDA;
import org.springframework.stereotype.Repository;

@Repository
public class UnidadMedidaDao extends AbstractDA<UnidadMedida>{

    @Override
    public long registrar(UnidadMedida bean) {
        return save(bean);
    }

    @Override
    public long actualizar(UnidadMedida bean) {
        return update(bean);
    }

    @Override
    public long eliminar(UnidadMedida bean) {
        return delete(bean);
    }

    @Override
    public List<UnidadMedida> listar() {
        return list(UnidadMedida.class);
    }

    @Override
    public List<UnidadMedida> listar(String ref) {
        return list(ref);
    }

    @Override
    public List<UnidadMedida> listar(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public UnidadMedida buscar(long id) {
        return search(UnidadMedida.class, id);
    }

    @Override
    public UnidadMedida buscar(String ref) {
        return search(ref);
    }

    @Override
    public long id() {
        return maxId(UnidadMedida.class);
    }
    
}
