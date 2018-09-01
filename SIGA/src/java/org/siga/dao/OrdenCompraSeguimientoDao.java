
package org.siga.dao;

import java.util.List;
import org.siga.be.OrdenCompra;
import org.siga.be.OrdenCompraSeguimiento;
import org.siga.util.AbstractDA;

public class OrdenCompraSeguimientoDao extends AbstractDA<OrdenCompraSeguimiento>{

    @Override
    public long registrar(OrdenCompraSeguimiento bean) {
        return save(bean);
    }

    @Override
    public long actualizar(OrdenCompraSeguimiento bean) {
        return update(bean);
    }

    @Override
    public long eliminar(OrdenCompraSeguimiento bean) {
        return delete(bean);
    }

    @Override
    public List<OrdenCompraSeguimiento> listar() {
        return list(OrdenCompraSeguimiento.class);
    }

    @Override
    public List<OrdenCompraSeguimiento> listar(String ref) {
        return list(ref);
    }

    @Override
    public List<OrdenCompraSeguimiento> listar(long id) {
        return list(OrdenCompraSeguimiento.class, OrdenCompra.class, id);
    }

    @Override
    public OrdenCompraSeguimiento buscar(long id) {
        return search(OrdenCompraSeguimiento.class, id);
    }

    @Override
    public OrdenCompraSeguimiento buscar(String ref) {
        return search(ref);
    }

    @Override
    public long id() {
        return maxId(OrdenCompraSeguimiento.class);
    }
    
}
