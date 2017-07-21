
package org.siga.dao;

import java.util.List;
import org.siga.be.Categoria;
import org.siga.util.AbstractDA;
import org.springframework.stereotype.Repository;

@Repository("categoriaDao")
public class CategoriaDao extends AbstractDA<Categoria>{

    @Override
    public long registrar(Categoria bean) {
        return save(bean);
    }

    @Override
    public long actualizar(Categoria bean) {
        return update(bean);
    }

    @Override
    public long eliminar(Categoria bean) {
        return delete(bean);
    }

    @Override
    public List<Categoria> listar() {
        return list(Categoria.class);
    }

    @Override
    public List<Categoria> listar(String ref) {
        return list(ref);
    }

    @Override
    public List<Categoria> listar(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Categoria buscar(long id) {
        return search(Categoria.class, id);
    }

    @Override
    public Categoria buscar(String ref) {
        return search(ref);
    }

    @Override
    public long id() {
        return maxId(Categoria.class);
    }
    
}
