
package org.siga.dao;

import java.util.List;
import org.siga.be.Rol;
import org.siga.util.AbstractDA;
import org.springframework.stereotype.Repository;
@Repository("rolDao")
public class RolDao extends AbstractDA<Rol>{
    
    @Override
    public long registrar(Rol bean) {
        return save(bean);
    }

    @Override
    public long actualizar(Rol bean) {
        return update(bean);
    }

    @Override
    public long eliminar(Rol bean) {
        return delete(bean);
    }

    @Override
    public List<Rol> listar() {
        return list(Rol.class);
    }

    @Override
    public List<Rol> listar(String ref) {
        return list("from Rol p where p.descripcion like '%"+ref+"%'");
    }

    @Override
    public List<Rol> listar(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Rol buscar(long id) {
        return search(Rol.class, id);
    }

    @Override
    public Rol buscar(String ref) {
        return search("from Rol p where p.descripcion = '"+ref+"'");
    }

    @Override
    public long id() {
        return maxId(Rol.class);
    }

    public Rol buscarxIdRol(long idrol) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
