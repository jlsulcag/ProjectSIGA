
package org.siga.bl;

import java.util.List;
import org.siga.be.Equivalencia;
import org.siga.dao.EquivalenciaDao;
import org.siga.util.AbstractBL;
import org.siga.util.AbstractDA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author JSULCAG
 */
@Service(value="equivalenciaBl")
public class EquivalenciaBl extends AbstractBL<Equivalencia>{
    @Autowired
    @Qualifier("equivalenciaDao")
    private EquivalenciaDao dao;

    @Override
    public AbstractDA getDAO() {
        return dao;
    }

    @Override
    public void setDA(AbstractDA dao) {
        this.dao = (EquivalenciaDao) dao;        
    }

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
        return list();
    }

    @Override
    public List<Equivalencia> listar(String ref) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Equivalencia> listar(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Equivalencia buscar(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Equivalencia buscar(String ref) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long id() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<Equivalencia> listarXidUnidadMedida(long idunidadmedida) {
        return dao.listarXidUnidadMedida(idunidadmedida);
    }

    public List<Equivalencia> listarEquivalenciaxUnidadMedida(long idunidadmedida) {
        return dao.listarXidUnidadMedida(idunidadmedida);
    }
}
