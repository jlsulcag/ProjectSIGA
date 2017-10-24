
package org.siga.dao;

import java.util.List;
import org.siga.be.NotaSalidaDetalle;
import org.siga.util.AbstractDA;
import org.springframework.stereotype.Repository;

@Repository("notaSalidaDetalleDao")
public class NotaSalidaDetalleDao extends AbstractDA<NotaSalidaDetalle>{

    @Override
    public long registrar(NotaSalidaDetalle bean) {
        return save(bean);
    }

    @Override
    public long actualizar(NotaSalidaDetalle bean) {
        return update(bean);
    }

    @Override
    public long eliminar(NotaSalidaDetalle bean) {
        return delete(bean);
    }

    @Override
    public List<NotaSalidaDetalle> listar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<NotaSalidaDetalle> listar(String ref) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<NotaSalidaDetalle> listar(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public NotaSalidaDetalle buscar(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public NotaSalidaDetalle buscar(String ref) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long id() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
