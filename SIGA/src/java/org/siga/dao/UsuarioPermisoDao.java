package org.siga.dao;

import java.util.List;
import org.siga.be.UsuarioPermiso;
import org.siga.util.AbstractDA;
import org.springframework.stereotype.Repository;

@Repository("usuarioPermisoDao")
public class UsuarioPermisoDao extends AbstractDA<UsuarioPermiso> {

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
        return list(UsuarioPermiso.class);
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
        return search(UsuarioPermiso.class, id);
    }

    @Override
    public UsuarioPermiso buscar(String ref) {
        return search(ref);
    }

    @Override
    public long id() {
        return maxId(UsuarioPermiso.class);
    }

    public List<UsuarioPermiso> listxIdUsuario(long idUser) {
        String hql = "from UsuarioPermiso a left join fetch a.usuario b left join fetch a.permiso c where b.idusuario = "+idUser;
        return listar(hql);
    }

    public UsuarioPermiso buscarxIdUsuario(long idUser, long idpermiso) {
        String hql = "from UsuarioPermiso a left join fetch a.usuario b left join fetch a.permiso c where b.idusuario = "+idUser + " and c.idpermiso = "+idpermiso;
        return buscar(hql);
    }

}
