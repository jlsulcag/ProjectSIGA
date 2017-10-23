
package org.siga.dao;

import java.util.List;
import org.siga.be.Usuario;
import org.siga.util.AbstractDA;
import org.springframework.stereotype.Repository;
@Repository("usuarioDao")
public class UsuarioDao extends AbstractDA<Usuario>{

    @Override
    public long registrar(Usuario bean) {
        return save(bean);
    }

    @Override
    public long actualizar(Usuario bean) {
        return update(bean);
    }

    @Override
    public long eliminar(Usuario bean) {
        return delete(bean);
    }

    @Override
    public List<Usuario> listar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Usuario> listar(String ref) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Usuario> listar(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Usuario buscar(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Usuario buscar(String ref) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long id() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
