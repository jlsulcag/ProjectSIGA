
package org.siga.dao;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.siga.be.OrdenCompra;
import org.siga.be.OrdenCompraSeguimiento;
import org.siga.util.AbstractDA;
import org.springframework.stereotype.Repository;

@Repository("ordenCompraSeguimientoDao")
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

    public int maxNumero(long idordencompra) {
        Session s = getSession();
        Transaction t = s.beginTransaction();
        int num = 0;
        try {
            String hql = "select max(a.numero) from OrdenCompraSeguimiento a where a.ordenCompra.idordencompra = "+idordencompra;
            Query query = s.createQuery(hql);
            if (query.uniqueResult() == null) {
                num = 0;
            } else {
                num = (int) query.uniqueResult();
            }
            t.commit();
            s.close();
            return num;
        } catch (HibernateException e) {
            t.rollback();
            return 0;
        }
    }

    public List<OrdenCompraSeguimiento> listarxEstado(long idestado) {
        String hql = "from OrdenCompraSeguimiento a where a.numero = (select max(b.numero) from OrdenCompraSeguimiento b where b.ordenCompra.idordencompra = a.ordenCompra.idordencompra) and a.ordenCompraEstados.idordencompraestados = "+idestado;
        return listar(hql);
    }
    
}
