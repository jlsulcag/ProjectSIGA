/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.siga.bl;

import java.util.List;
import org.siga.be.Familia;
import org.siga.dao.FamiliaDao;
import org.siga.util.AbstractBL;
import org.siga.util.AbstractDA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("familiaBl")
public class FamiliaBl extends AbstractBL<Familia>{
    
    @Autowired
    @Qualifier("familiaDao")
    private FamiliaDao dao;

    @Override
    public AbstractDA getDAO() {
        return dao;
    }

    @Override
    public void setDA(AbstractDA dao) {
        this.dao = (FamiliaDao) dao;
    }

    @Override
    public long registrar(Familia bean) {
        return save(bean);
    }

    @Override
    public long actualizar(Familia bean) {
        return update(bean);
    }

    @Override
    public long eliminar(Familia bean) {
        return delete(bean);
    }

    @Override
    public List<Familia> listar() {
        return list();
    }

    @Override
    public List<Familia> listar(String ref) {
        return list(ref);
    }

    @Override
    public List<Familia> listar(long id) {
        return list(id);
    }

    @Override
    public Familia buscar(long id) {
        return search(id);
    }

    @Override
    public Familia buscar(String ref) {
        return search(ref);
    }

    @Override
    public long id() {
        return maxId();
    }

    public List<Familia> listarRef(String txtBusqueda) {
        return dao.listarRef(txtBusqueda);
    }

    public List listarNombresFamilias() {
        return dao.listarNombresFamilias();
    }
    
}
