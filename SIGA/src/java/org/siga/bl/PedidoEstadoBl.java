/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.siga.bl;

import java.util.List;
import org.siga.be.PedidoEstados;
import org.siga.dao.PedidoEstadoDao;
import org.siga.util.AbstractBL;
import org.siga.util.AbstractDA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("pedidoEstadoBl")
public class PedidoEstadoBl extends AbstractBL<PedidoEstados>{
    @Autowired
    @Qualifier("pedidoEstadoDao")
    private PedidoEstadoDao dao;

    @Override
    public AbstractDA getDAO() {
        return dao;
    }

    @Override
    public void setDA(AbstractDA dao) {
        this.dao = (PedidoEstadoDao) dao;
    }

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
        return list();
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

    public PedidoEstados buscarRef(String registrado) {
        return dao.buscarRef(registrado);
    }
}
