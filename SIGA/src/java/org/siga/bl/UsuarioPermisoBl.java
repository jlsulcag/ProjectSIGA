
package org.siga.bl;

import java.util.List;
import org.siga.be.UsuarioPermiso;
import org.siga.dao.UsuarioPermisoDao;
import org.siga.util.AbstractBL;
import org.siga.util.AbstractDA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
@Service("usuarioPermisoBl")
public class UsuarioPermisoBl extends AbstractBL<UsuarioPermiso>{
    @Autowired
    @Qualifier("usuarioPermisoDao")
    private UsuarioPermisoDao dao;
    
    @Override
    public AbstractDA getDAO() {
        dao = new UsuarioPermisoDao();
        return dao;
    }

    @Override
    public void setDA(AbstractDA dao) {
        this.dao = (UsuarioPermisoDao) dao;
    }

    @Override
    public long registrar(UsuarioPermiso bean) {
        return save(bean);
    }

    @Override
    public long actualizar(UsuarioPermiso bean) {
        return update(bean);
    }

    @Override
    public long eliminar(UsuarioPermiso bean) {
        return delete(bean);
    }

    @Override
    public List<UsuarioPermiso> listar() {
        return list();
    }

    @Override
    public List<UsuarioPermiso> listar(String ref) {
        return list(ref);
    }

    @Override
    public List<UsuarioPermiso> listar(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public UsuarioPermiso buscar(long id) {
        return search(id);
    }

    @Override
    public UsuarioPermiso buscar(String ref) {
        return search(ref);
    }

    @Override
    public long id() {
        return maxId();
    }

    public List<UsuarioPermiso> listxIdUsuario(long idUser) {
        return dao.listxIdUsuario(idUser);
    }

    public UsuarioPermiso buscarxIdUsuario(long idUser, long idpermiso) {        
        return dao.buscarxIdUsuario(idUser, idpermiso);
    }
    
}
