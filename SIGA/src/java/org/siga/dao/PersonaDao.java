/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.siga.dao;

import java.util.List;
import org.siga.be.Persona;
import org.siga.util.AbstractDA;
import org.springframework.stereotype.Repository;
@Repository("personaDao")
public class PersonaDao extends AbstractDA<Persona>{

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
        return list(ref);
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
        String hql = "from Persona a";
        return listar(hql);
    }

    public List<Persona> listarRef(String txtBusqueda) {
        String hql = "from Persona a where (a.apellidoPaterno || ' ' || a.apellidoMaterno || ' ' || a.nombre) like '%"+txtBusqueda+"%'";
        return listar(hql);
    }
    
}
