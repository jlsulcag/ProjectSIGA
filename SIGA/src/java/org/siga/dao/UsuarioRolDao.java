
package org.siga.dao;

import java.util.List;
import org.siga.be.UsuarioRol;
import org.siga.util.AbstractDA;
import org.springframework.stereotype.Repository;

@Repository("usuarioRolDao")
public class UsuarioRolDao extends AbstractDA<UsuarioRol>{

    @Override
    public long registrar(UsuarioRol bean) {
        return save(bean);
    }

    @Override
    public long actualizar(UsuarioRol bean) {
        return update(bean);
    }

    @Override
    public long eliminar(UsuarioRol bean) {
        return delete(bean);
    }

    @Override
    public List<UsuarioRol> listar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<UsuarioRol> listar(String ref) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<UsuarioRol> listar(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public UsuarioRol buscar(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public UsuarioRol buscar(String ref) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long id() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
