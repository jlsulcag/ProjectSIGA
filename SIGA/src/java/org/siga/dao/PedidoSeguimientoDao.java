
package org.siga.dao;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.siga.be.PedidoSeguimiento;
import org.siga.util.AbstractDA;
import org.springframework.stereotype.Repository;

@Repository("pedidoSeguimientoDao")
public class PedidoSeguimientoDao extends AbstractDA<PedidoSeguimiento>{

    @Override
    public long registrar(PedidoSeguimiento bean) {
        return save(bean);
    }

    @Override
    public long actualizar(PedidoSeguimiento bean) {
        return update(bean);
    }

    @Override
    public long eliminar(PedidoSeguimiento bean) {
        return delete(bean);
    }

    @Override
    public List<PedidoSeguimiento> listar() {
        return list(PedidoSeguimiento.class);
    }

    @Override
    public List<PedidoSeguimiento> listar(String ref) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<PedidoSeguimiento> listar(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PedidoSeguimiento buscar(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PedidoSeguimiento buscar(String ref) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long id() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

  
    public int maxNumero(long idpedido) {
        Session s = getSession();
        Transaction t = s.beginTransaction();
        int num = 0;
        try {
            String hql = "select max(a.numero) from PedidoSeguimiento a left join fetch a.pedido b where b.idpedido = "+idpedido;
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
    
}
