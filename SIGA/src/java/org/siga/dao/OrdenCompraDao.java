
package org.siga.dao;

import java.util.List;
import org.siga.be.OrdenCompra;
import org.siga.util.AbstractDA;
import org.springframework.stereotype.Repository;
@Repository("ordenCompraDao")
public class OrdenCompraDao extends AbstractDA<OrdenCompra>{

    @Override
    public long registrar(OrdenCompra bean) {
        return save(bean);
    }

    @Override
    public long actualizar(OrdenCompra bean) {
        return update(bean);
    }

    @Override
    public long eliminar(OrdenCompra bean) {
        return delete(bean);
    }

    @Override
    public List<OrdenCompra> listar() {
        return  list(OrdenCompra.class);
    }

    @Override
    public List<OrdenCompra> listar(String ref) {
        return list(ref);
    }

    @Override
    public List<OrdenCompra> listar(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public OrdenCompra buscar(long id) {
        return search(OrdenCompra.class, id);
    }

    @Override
    public OrdenCompra buscar(String ref) {
        return search(ref);
    }

    @Override
    public long id() {
        return maxId(OrdenCompra.class);
    }

    public List<OrdenCompra> listarFull(String string) {
        string = "from OrdenCompra a order by a.numero desc";
        return listar(string);
    }
    
}
