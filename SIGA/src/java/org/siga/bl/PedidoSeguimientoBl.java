
package org.siga.bl;

import java.util.List;
import org.siga.be.PedidoSeguimiento;
import org.siga.dao.PedidoSeguimientoDao;
import org.siga.util.AbstractBL;
import org.siga.util.AbstractDA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("pedidoSeguimientoBl")
public class PedidoSeguimientoBl extends AbstractBL<PedidoSeguimiento>{
    @Autowired
    @Qualifier("pedidoSeguimientoDao")
    private PedidoSeguimientoDao dao;

    @Override
    public AbstractDA getDAO() {
        return dao;
    }

    @Override
    public void setDA(AbstractDA dao) {
        this.dao = (PedidoSeguimientoDao) dao;
    }

    @Override
    public long registrar(PedidoSeguimiento bean) {
        return save(bean);
    }

    @Override
    public long actualizar(PedidoSeguimiento bean) {
        return update(bean);
    }

    @Override
    public long eliminar(PedidoSeguimiento bean) {
        return delete(bean);
    }

    @Override
    public List<PedidoSeguimiento> listar() {
        return list();
    }

    @Override
    public List<PedidoSeguimiento> listar(String ref) {
        return list(ref);
    }

    @Override
    public List<PedidoSeguimiento> listar(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PedidoSeguimiento buscar(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PedidoSeguimiento buscar(String ref) {
        return search(ref);
    }

    @Override
    public long id() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    public int maxNumero(long idpedido) {
        return dao.maxNumero(idpedido);
    }

    public List<PedidoSeguimiento> listarxEstado(long idestado) {
        return dao.listarxEstado(idestado);
    }

    public PedidoSeguimiento buscarxidPedido(long idpedido) {
        return dao.buscarxidPedido(idpedido);
    }
    
    public List<PedidoSeguimiento> listarxidPedido(long idpedido) {
        return dao.listarxidPedido(idpedido);
    }   
    
}
