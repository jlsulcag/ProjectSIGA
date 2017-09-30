
package org.siga.dao;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.siga.be.NotaEntrada;
import org.siga.util.AbstractDA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
@Repository("notaIngresoDao")
public class NotaIngresoDao extends AbstractDA<NotaEntrada>{

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
    public long registrar(NotaEntrada bean) {
        return save(bean);
    }

    @Override
    public long actualizar(NotaEntrada bean) {
        return update(bean);
    }

    @Override
    public long eliminar(NotaEntrada bean) {
        return delete(bean);
    }

    @Override
    public List<NotaEntrada> listar() {
        return list(NotaEntrada.class);
    }

    @Override
    public List<NotaEntrada> listar(String ref) {
        return list(ref);
    }

    @Override
    public List<NotaEntrada> listar(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public NotaEntrada buscar(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public NotaEntrada buscar(String ref) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long id() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public List<NotaEntrada> listarFull(String string) {
        string = "from NotaEntrada a order by a.numero desc";
        return listar(string);
    }

    public long buscarUltimoNumero() {
        Session s = getSession();
        Transaction t = s.beginTransaction();
        long num = 0;
        try {
            String hql = "select max(a.numero) from NotaEntrada a";
            Query query = s.createQuery(hql);
            if (query.uniqueResult() == null) {
                num = 0;
            } else {
                num = (long) query.uniqueResult();
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
