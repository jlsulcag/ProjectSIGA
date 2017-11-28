
package org.siga.bl;

import java.util.List;
import org.siga.be.Persona;
import org.siga.dao.PersonaDao;
import org.siga.util.AbstractBL;
import org.siga.util.AbstractDA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("personaBl")
public class PersonaBl extends AbstractBL<Persona>{
    
    @Autowired
    @Qualifier("personaDao")
    private PersonaDao dao;
    
    @Override
    public AbstractDA getDAO() {
        return dao;
    }

    @Override
    public void setDA(AbstractDA dao) {
        this.dao = (PersonaDao) dao;
    }

    @Override
    public long registrar(Persona bean) {
        return save(bean);
    }

    @Override
    public long actualizar(Persona bean) {
        return update(bean);
    }

    @Override
    public long eliminar(Persona bean) {
        return delete(bean);
    }

    @Override
    public List<Persona> listar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Persona> listar(String ref) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Persona> listar(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Persona buscar(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Persona buscar(String ref) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long id() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<Persona> listarAll() {
        return dao.listarAll();
    }

    public List<Persona> listarRef(String txtBusqueda) {
        return dao.listarRef(txtBusqueda);
    }
    
}
