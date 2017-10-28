
package org.siga.bl;

import java.util.List;
import org.siga.be.Dependencia;
import org.siga.dao.DependenciaDao;
import org.siga.util.AbstractBL;
import org.siga.util.AbstractDA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
@Service(value = "dependenciaBl")
public class DependenciaBl extends AbstractBL<Dependencia>{
    @Autowired
    @Qualifier("dependenciaDao")
    private DependenciaDao dao;

    @Override
    public AbstractDA getDAO() {
        return dao;
    }

    @Override
    public void setDA(AbstractDA dao) {
        this.dao = (DependenciaDao) dao;
    }

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
        return list();
    }

    @Override
    public List<Dependencia> listar(String ref) {
        return list(ref);
    }

    @Override
    public List<Dependencia> listar(long id) {
        return list(id);
    }

    @Override
    public Dependencia buscar(long id) {
        return search(id);
    }

    @Override
    public Dependencia buscar(String ref) {
        return search(ref);
    }

    @Override
    public long id() {
        return maxId();
    }

    public List<Dependencia> listarRef(String toUpperCase) {
        return dao.listarRef(toUpperCase);
    }

    public List<Dependencia> listarFull() {
        return dao.listarFull();
    }

    public Dependencia buscarXId(long iddependencia) {
        return dao.buscarXId(iddependencia);
    }
    
}
