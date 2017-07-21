
package org.siga.bl;

import java.util.List;
import org.siga.be.Categoria;
import org.siga.dao.CategoriaDao;
import org.siga.util.AbstractBL;
import org.siga.util.AbstractDA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("categoriaBl")
public class CategoriaBl extends AbstractBL<Categoria>{
    @Autowired
    @Qualifier("categoriaDao")
    private CategoriaDao categoriaDao;
    
    @Override
    public AbstractDA getDAO() {
        return categoriaDao;
    }

    @Override
    public void setDA(AbstractDA dao) {
        this.categoriaDao = (CategoriaDao) dao;
    }

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
        return list();
    }

    @Override
    public List<Categoria> listar(String ref) {
        return list(ref);
    }

    @Override
    public List<Categoria> listar(long id) {
        return list(id);
    }

    @Override
    public Categoria buscar(long id) {
        return search(id);
    }

    @Override
    public Categoria buscar(String ref) {
        return search(ref);
    }

    @Override
    public long id() {
        return maxId();
    }
    
}
