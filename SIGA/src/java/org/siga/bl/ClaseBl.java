
package org.siga.bl;

import java.util.List;
import org.siga.be.Clase;
import org.siga.dao.ClaseDao;
import org.siga.util.AbstractBL;
import org.siga.util.AbstractDA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("claseBl")
public class ClaseBl extends AbstractBL<Clase>{
    @Autowired
    @Qualifier("claseDao")
    private ClaseDao dao;
    
    @Override
    public AbstractDA getDAO() {
        return dao;
    }

    @Override
    public void setDA(AbstractDA dao) {
        this.dao = (ClaseDao) dao;
    }

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
        return list();
    }

    @Override
    public List<Clase> listar(String ref) {
        return list(ref);
    }

    @Override
    public List<Clase> listar(long id) {
        return list(id);
    }

    @Override
    public Clase buscar(long id) {
        return search(id);
    }

    @Override
    public Clase buscar(String ref) {
        return search(ref);
    }

    @Override
    public long id() {
        return maxId();
    }

    public List<Clase> listarFull(String string) {
        return dao.listarFull(string);
    }

    public List<Clase> listarClasePorFamilia(long idFam) {
        return dao.listarClasePorFamilia(idFam);
    }
    
}
