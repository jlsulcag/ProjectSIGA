
package org.siga.dao;

import java.util.List;
import org.siga.be.PedidoDetalle;
import org.siga.util.AbstractBL;
import org.siga.util.AbstractDA;
import org.springframework.stereotype.Repository;

@Repository("pedidoDetalleDao")
public class PedidoDetalleDao extends AbstractDA<PedidoDetalle>{

    @Override
    public long registrar(PedidoDetalle bean) {
        return save(bean);
    }

    @Override
    public long actualizar(PedidoDetalle bean) {
        return update(bean);
    }

    @Override
    public long eliminar(PedidoDetalle bean) {
        return delete(bean);
    }

    @Override
    public List<PedidoDetalle> listar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<PedidoDetalle> listar(String ref) {
        return list(ref);
    }

    @Override
    public List<PedidoDetalle> listar(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PedidoDetalle buscar(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PedidoDetalle buscar(String ref) {
        return search(ref);
    }

    @Override
    public long id() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<PedidoDetalle> listarxIdPedido(long id) {
        String hql = "from PedidoDetalle a left join fetch a.pedido b left join fetch a.producto c where b.idpedido = "+id;
        return listar(hql);
    }

    public PedidoDetalle buscarxID(long idpedidodetalle) {
        String hql = "from PedidoDetalle a left join fetch a.pedido b left join fetch a.producto c where a.idpedidodetalle = "+idpedidodetalle;
        return buscar(hql);
    }
    
   
    
}
