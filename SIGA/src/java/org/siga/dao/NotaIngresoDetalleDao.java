
package org.siga.dao;

import java.util.List;
import org.siga.be.NotaEntradaDetalle;
import org.siga.util.AbstractDA;
import org.springframework.stereotype.Repository;

@Repository("notaIngresoDetalleDao")
public class NotaIngresoDetalleDao extends AbstractDA<NotaEntradaDetalle>{

    @Override
    public long registrar(NotaEntradaDetalle bean) {
        return save(bean);
    }

    @Override
    public long actualizar(NotaEntradaDetalle bean) {
        return update(bean);
    }

    @Override
    public long eliminar(NotaEntradaDetalle bean) {
        return delete(bean);
    }

    @Override
    public List<NotaEntradaDetalle> listar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<NotaEntradaDetalle> listar(String ref) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<NotaEntradaDetalle> listar(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public NotaEntradaDetalle buscar(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public NotaEntradaDetalle buscar(String ref) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long id() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
