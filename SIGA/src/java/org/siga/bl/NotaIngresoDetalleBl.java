
package org.siga.bl;

import java.util.List;
import org.siga.be.NotaEntradaDetalle;
import org.siga.dao.NotaIngresoDetalleDao;
import org.siga.util.AbstractBL;
import org.siga.util.AbstractDA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
@Service("notaIngresoDetalleBl")
public class NotaIngresoDetalleBl extends AbstractBL<NotaEntradaDetalle>{
    
    @Autowired
    @Qualifier("notaIngresoDetalleDao")
    private NotaIngresoDetalleDao dao;
            
    @Override
    public AbstractDA getDAO() {
        return dao;
    }

    @Override
    public void setDA(AbstractDA dao) {
        this.dao = (NotaIngresoDetalleDao) dao;
    }

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

    public NotaIngresoDetalleDao getDao() {
        return dao;
    }

    public void setDao(NotaIngresoDetalleDao dao) {
        this.dao = dao;
    }

    public int getCantIngresada(long idproducto, long id) {
        return dao.getCantIngresada(idproducto, id);
    }
    
}
