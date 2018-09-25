/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.siga.dao;

import java.util.List;
import org.siga.be.Familia;
import org.siga.util.AbstractDA;
import org.springframework.stereotype.Repository;

@Repository("familiaDao")
public class FamiliaDao extends AbstractDA<Familia>{

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
        return list(Familia.class);
    }

    @Override
    public List<Familia> listar(String ref) {
        return list(ref);
    }

    @Override
    public List<Familia> listar(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Familia buscar(long id) {
        return search(Familia.class, id);
    }

    @Override
    public Familia buscar(String ref) {
        return search(ref);
    }

    @Override
    public long id() {
        return maxId(Familia.class);
    }

    public List<Familia> listarRef(String txtBusqueda) {
        String hql = "From Familia f where f.descripcion like '%"+txtBusqueda+"%'";
        return listar(hql);
    }

    public List listarNombresFamilias() {
        String hql = "Select f.descripcion From Familia f where f.estado = 'ACT'";
        return listSQL(hql);
    }
    
}
