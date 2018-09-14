
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
        return search(ref);
    }

    @Override
    public long id() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public UsuarioRol buscarxIdUsuario(long idusuario) {
        String hql = "from UsuarioRol a left join fetch a.usuario b left join fetch a.rol c where b.idusuario = "+idusuario;
        System.out.println("        .... aqioiii");
        return buscar(hql);
    }
    
}
