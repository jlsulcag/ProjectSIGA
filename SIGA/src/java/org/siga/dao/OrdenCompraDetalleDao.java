
package org.siga.dao;

import java.util.List;
import org.siga.be.OrdenCompraDetalle;
import org.siga.util.AbstractDA;
import org.springframework.stereotype.Repository;
@Repository("ordenCompraDetalleDao")
public class OrdenCompraDetalleDao extends AbstractDA<Object>{

    @Override
    public long registrar(Object bean) {
        return save(bean);
    }

    @Override
    public long actualizar(Object bean) {
        return update(bean);
    }

    @Override
    public long eliminar(Object bean) {
        return delete(bean);
    }

    @Override
    public List<Object> listar() {
        return list(OrdenCompraDetalle.class);
    }

    @Override
    public List<Object> listar(String ref) {
        return list(ref);
    }

    @Override
    public List<Object> listar(long id) {
        return list(OrdenCompraDetalle.class, OrdenCompraDetalle.class, id);
    }

    @Override
    public Object buscar(long id) {
        return search(OrdenCompraDetalle.class, id);
    }

    @Override
    public Object buscar(String ref) {
        return search(ref);
    }

    @Override
    public long id() {
        return maxId(OrdenCompraDetalle.class);
    }
    
}
