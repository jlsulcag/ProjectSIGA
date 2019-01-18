package org.siga.dao;

import java.util.LinkedList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.siga.be.Moneda;
import org.siga.util.MensajeView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository("monedaDao")
public class MonedaDao {

    private Session sesion;
    private Transaction tx;
    private final int SUCCESS = 0;
    private final int ERROR = -1;
    @Autowired
    @Qualifier("sessionFactory")
    private SessionFactory sessionFactory;
    private List<Moneda> listMonedas;

    public List<Moneda> listar() throws HibernateException {
        try {
            iniciarOperacion();
            String hql = "from Moneda a order by 1";
            Query q = sesion.createQuery(hql);
            listMonedas = q.list();
            tx.commit();
        } catch (HibernateException e) {
            manejaExcepcion(e);
            listMonedas = new LinkedList<>();
        } finally {
            sesion.close();
        }
        return listMonedas;
    }

    private void iniciarOperacion() throws HibernateException {
        try {
            sesion = getSessionFactory().getCurrentSession();
        } catch (HibernateException e) {
            sesion = getSessionFactory().openSession();
        }
        tx = sesion.beginTransaction();
    }

    private void manejaExcepcion(HibernateException he) {
        tx.rollback();
        MensajeView.errorFatal(he);
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
