
package org.siga.dao;

import java.util.List;
import org.siga.be.PedidoEstados;
import org.siga.util.AbstractDA;
import org.springframework.stereotype.Repository;

@Repository("pedidoEstadoDao")
public class PedidoEstadoDao extends AbstractDA<PedidoEstados>{

    @Override
    public long registrar(PedidoEstados bean) {
        return save(bean);
    }

    @Override
    public long actualizar(PedidoEstados bean) {
        return update(bean);
    }

    @Override
    public long eliminar(PedidoEstados bean) {
        return delete(bean);
    }

    @Override
    public List<PedidoEstados> listar() {
        return list(PedidoEstados.class);
    }

    @Override
    public List<PedidoEstados> listar(String ref) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<PedidoEstados> listar(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PedidoEstados buscar(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PedidoEstados buscar(String ref) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long id() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
