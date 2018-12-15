
package org.siga.bl;

import java.util.List;
import org.siga.be.NotaEntrada;
import org.siga.dao.NotaIngresoDao;
import org.siga.util.AbstractBL;
import org.siga.util.AbstractDA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("notaIngresoBl")
public class NotaIngresoBl extends AbstractBL<NotaEntrada>{
    @Autowired
    @Qualifier("notaIngresoDao")
    private NotaIngresoDao dao;
    @Override
    public AbstractDA getDAO() {
        return getDao();
    }

    @Override
    public void setDA(AbstractDA dao) {
        this.dao = (NotaIngresoDao) dao;
    }

    @Override
    public long registrar(NotaEntrada bean) {
        return save(bean);
    }

    @Override
    public long actualizar(NotaEntrada bean) {
        return update(bean);
    }

    @Override
    public long eliminar(NotaEntrada bean) {
        return delete(bean);
    }

    @Override
    public List<NotaEntrada> listar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<NotaEntrada> listar(String ref) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<NotaEntrada> listar(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public NotaEntrada buscar(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public NotaEntrada buscar(String ref) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long id() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public NotaIngresoDao getDao() {
        return dao;
    }

    public void setDao(NotaIngresoDao dao) {
        this.dao = dao;
    }
    
    public List<NotaEntrada> listarFull(String string) {
        return dao.listarFull(string);
    }

    public long buscarUltimoNumero() {
        return dao.buscarUltimoNumero();
    }

    
    public NotaEntrada buscarxIdCompra(long idordencompra) {
        return dao.buscarxIdCompra(idordencompra);
    }

    public NotaEntrada buscarxIdPedido(long idpedido) {
        return dao.buscarxIdPedido(idpedido);
    }

    public List<NotaEntrada> listarCompras() {
        return dao.listarCompras();
    }
}
