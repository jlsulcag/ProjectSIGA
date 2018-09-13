
package org.siga.bl;

import java.util.List;
import org.siga.be.OrdenCompraSeguimiento;
import org.siga.dao.OrdenCompraSeguimientoDao;
import org.siga.util.AbstractBL;
import org.siga.util.AbstractDA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("ordenCompraSeguimientoBl")
public class OrdenCompraSeguimientoBl extends AbstractBL<OrdenCompraSeguimiento>{

    @Autowired
    @Qualifier("ordenCompraSeguimientoDao")
    private OrdenCompraSeguimientoDao dao;
    
    @Override
    public AbstractDA getDAO() {
        return dao;
    }

    @Override
    public void setDA(AbstractDA dao) {
        this.dao = (OrdenCompraSeguimientoDao) dao;
    }

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
        return list();
    }

    @Override
    public List<OrdenCompraSeguimiento> listar(String ref) {
        return list(ref);
    }

    @Override
    public List<OrdenCompraSeguimiento> listar(long id) {
        return list(id);
    }

    @Override
    public OrdenCompraSeguimiento buscar(long id) {
        return search(id);
    }

    @Override
    public OrdenCompraSeguimiento buscar(String ref) {
        return search(ref);
    }

    @Override
    public long id() {
        return maxId();
    }

    public int maxNumero(long idordencompra) {
        return dao.maxNumero(idordencompra);
    }
    
    public List<OrdenCompraSeguimiento> listarxEstado(long idestado) {
        return dao.listarxEstado(idestado);
    }

    public List<OrdenCompraSeguimiento> listarxEstado(long idestado1, long idestado2) {
        return dao.listarxEstado(idestado1, idestado2);
    }

    public OrdenCompraSeguimiento buscarxidCompra(long idordencompra) {
        return dao.buscarxidCompra(idordencompra);
    }
    
}
