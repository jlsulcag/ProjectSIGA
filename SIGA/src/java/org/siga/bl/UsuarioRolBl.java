
package org.siga.bl;

import java.util.List;
import org.siga.be.UsuarioRol;
import org.siga.dao.UsuarioRolDao;
import org.siga.util.AbstractBL;
import org.siga.util.AbstractDA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("usuarioRolBl")
public class UsuarioRolBl extends AbstractBL<UsuarioRol>{
    @Autowired
    @Qualifier("usuarioRolDao")
    private UsuarioRolDao dao;

    @Override
    public AbstractDA getDAO() {
        return dao;
    }

    @Override
    public void setDA(AbstractDA dao) {
        this.dao = (UsuarioRolDao) dao;
    }

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

    public UsuarioRol buscarxIdUsuario(long idusuario) {
        return dao.buscarxIdUsuario(idusuario);
    }
}
