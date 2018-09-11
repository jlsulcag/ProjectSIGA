
package org.siga.ds;

import javax.faces.bean.ManagedProperty;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class DsConexion {
    
    @ManagedProperty(value = "#{sessionFactory}")
    private SessionFactory sessionFactory;
    
    @Deprecated
    public Session getSession() {
        //<editor-fold defaultstate="collapsed" desc="CUERPO">
        try {
            return getSessionFactory().getCurrentSession();
        } catch (HibernateException e) {
            return getSessionFactory().openSession();
        }
        //</editor-fold>
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
