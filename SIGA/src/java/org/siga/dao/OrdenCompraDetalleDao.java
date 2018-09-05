
package org.siga.dao;

import java.util.List;
import org.siga.be.OrdenCompra;
import org.siga.be.OrdenCompraDetalle;
import org.siga.util.AbstractDA;
import org.springframework.stereotype.Repository;
@Repository("ordenCompraDetalleDao")
public class OrdenCompraDetalleDao extends AbstractDA<OrdenCompraDetalle>{

    @Override
    public long registrar(OrdenCompraDetalle bean) {
        return save(bean);
    }

    @Override
    public long actualizar(OrdenCompraDetalle bean) {
        return update(bean);
    }

    @Override
    public long eliminar(OrdenCompraDetalle bean) {
        return delete(bean);
    }

    @Override
    public List<OrdenCompraDetalle> listar() {
        return list(OrdenCompraDetalle.class);
    }

    @Override
    public List<OrdenCompraDetalle> listar(String ref) {
        return list(ref);
    }

    @Override
    public List<OrdenCompraDetalle> listar(long id) {
        return list(OrdenCompraDetalle.class, OrdenCompra.class, id);
    }

    @Override
    public OrdenCompraDetalle buscar(long id) {
        return search(OrdenCompraDetalle.class, id);
    }

    @Override
    public OrdenCompraDetalle buscar(String ref) {
        return search(ref);
    }

    @Override
    public long id() {
        return maxId(OrdenCompraDetalle.class);
    }

    public List<OrdenCompraDetalle> listarFull() {
        String hql = "from OrdenCompraDetalle a left join fetch a.ordenCompra b left join fetch a.producto c";
        return listar(hql);
    }

    public List<OrdenCompraDetalle> listarXIdOrdenCompra(long id) {
        String hql = "from OrdenCompraDetalle a left join fetch a.ordenCompra b left join fetch a.producto c where b.idordencompra = "+id;
        return listar(hql);
    }

    
    
}
