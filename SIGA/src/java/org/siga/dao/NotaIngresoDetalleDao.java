
package org.siga.dao;

import java.math.BigDecimal;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.siga.be.NotaEntradaDetalle;
import org.siga.util.AbstractDA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository("notaIngresoDetalleDao")
public class NotaIngresoDetalleDao extends AbstractDA<NotaEntradaDetalle>{

    @Autowired
    @Qualifier("sessionFactory")
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Deprecated
    public Session getSession() {
        try {
            return getSessionFactory().getCurrentSession();
        } catch (HibernateException e) {
            return getSessionFactory().openSession();
        }
    }
    
    @Override
    public long registrar(NotaEntradaDetalle bean) {
        return save(bean);
    }

    @Override
    public long actualizar(NotaEntradaDetalle bean) {
        return update(bean);
    }

    @Override
    public long eliminar(NotaEntradaDetalle bean) {
        return delete(bean);
    }

    @Override
    public List<NotaEntradaDetalle> listar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<NotaEntradaDetalle> listar(String ref) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<NotaEntradaDetalle> listar(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public NotaEntradaDetalle buscar(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public NotaEntradaDetalle buscar(String ref) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long id() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public BigDecimal getCantIngresada(long idproducto, long id) {
        Session s = getSession();
        Transaction t = s.beginTransaction();
        BigDecimal cant = BigDecimal.ZERO;
        try {
            String hql = "select sum(a.cantIngreso) from NotaEntradaDetalle a where a.producto.idproducto = "+idproducto +" and a.notaEntrada.ordenCompra.idordencompra = "+id;
            Query query = s.createQuery(hql);
            if (query.uniqueResult() == null) {
                cant = BigDecimal.ZERO;
            } else {
                cant = (BigDecimal) query.uniqueResult();
            }
            t.commit();
            s.close();
            return cant;
        } catch (HibernateException e) {
            t.rollback();
            return BigDecimal.ZERO;
        }
    }
    
}
