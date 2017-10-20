package org.siga.bl;

import java.util.List;
import org.siga.be.Pedido;
import org.siga.dao.PedidoDao;
import org.siga.util.AbstractBL;
import org.siga.util.AbstractDA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("pedidoBl")
public class PedidoBl extends AbstractBL<Pedido> {

    @Autowired
    @Qualifier("pedidoDao")
    private PedidoDao dao;

    @Override
    public AbstractDA getDAO() {
        return dao;
    }

    @Override
    public void setDA(AbstractDA dao) {
        this.dao = (PedidoDao) dao;
    }

    @Override
    public long registrar(Pedido bean) {
        return save(bean);
    }

    @Override
    public long actualizar(Pedido bean) {
        return update(bean);
    }

    @Override
    public long eliminar(Pedido bean) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Pedido> listar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Pedido> listar(String ref) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Pedido> listar(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Pedido buscar(long id) {
        return search(id);
    }

    @Override
    public Pedido buscar(String ref) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long id() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<Pedido> listarFull(String string) {
        return dao.listarFull(string);
    }

    public long buscarMaxNumero() {
        return dao.buscarMaxNumero();
    }
}
