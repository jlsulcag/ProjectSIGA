
package org.siga.dao;

import java.util.List;
import org.siga.util.AbstractDA;

public class OrdenCompraEstados extends AbstractDA<OrdenCompraEstados>{

    @Override
    public long registrar(OrdenCompraEstados bean) {
        return save(bean);
    }

    @Override
    public long actualizar(OrdenCompraEstados bean) {
        return update(bean);
    }

    @Override
    public long eliminar(OrdenCompraEstados bean) {
        return delete(bean);
    }

    @Override
    public List<OrdenCompraEstados> listar() {
        return list(OrdenCompraEstados.class);
    }

    @Override
    public List<OrdenCompraEstados> listar(String ref) {
        return list(ref);
    }

    @Override
    public List<OrdenCompraEstados> listar(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public OrdenCompraEstados buscar(long id) {
        return search(OrdenCompraEstados.class, id);
    }

    @Override
    public OrdenCompraEstados buscar(String ref) {
        return search(ref);
    }

    @Override
    public long id() {
        return maxId(OrdenCompraEstados.class);
    }
    
}
