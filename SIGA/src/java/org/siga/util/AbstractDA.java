package org.siga.util;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public abstract class AbstractDA<T> implements DAO<T>{
    private static final long SUCCESS=0,ERROR=-1;
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
    public Session getSession(){
        try {
            return getSessionFactory().getCurrentSession();
        } catch (HibernateException e) {
            return getSessionFactory().openSession();
        }        
    }
    @Deprecated
    public long save(T bean) {
        Session s = getSession();
        Transaction t = s.beginTransaction();
        try {           
            s.save(bean);
            t.commit();        
            s.close();    
            return SUCCESS;
        } catch (HibernateException e) {
            t.rollback();
            MensajeView.errorFatal(e);
            e.printStackTrace();            
            return ERROR;
        }
    }
    @Deprecated
    public long update(T bean) {
        Session s = getSession();
        Transaction t = s.beginTransaction();
        try {           
            s.update(bean);
            t.commit();        
            s.close();    
            return SUCCESS;
        } catch (HibernateException e) {
            t.rollback();
            e.printStackTrace();            
            return ERROR;
        }
    }
    @Deprecated
    public long delete(T bean) {
        Session s = getSession();
        Transaction t = s.beginTransaction();
        try {           
            s.delete(bean);
            t.commit();        
            s.close();    
            return SUCCESS;
        } catch (HibernateException e) {
            t.rollback();
            e.printStackTrace();            
            return ERROR;
        }
    }
    @Deprecated
    public List<T> list(Class c) {
        Session s = getSession();
        Transaction t = s.beginTransaction();
        try {            
            List<T> l = s.createQuery("from "+c.getSimpleName()).list();
            t.commit();        
            s.close();    
            return l;
        } catch (HibernateException e) {
            t.rollback();
            e.printStackTrace();            
            return null;
        }
    }
    @Deprecated
    public List<T> list(String query) {
        Session s = getSession();
        Transaction t = s.beginTransaction();
        try {  
            Query q = s.createQuery(query);
            List<T> l = (List)q.list();
            t.commit();        
            s.close();    
            return l;
        } catch (HibernateException e) {
            t.rollback();
            e.printStackTrace();            
            return null;
        }
    }
    @Deprecated
    public List listSQL(String query) {
        Session s = getSession();
        Transaction t = s.beginTransaction();
        try {  
            Query q = s.createSQLQuery(query);
            List l = (List)q.list();
            t.commit();        
            s.close();    
            return l;
        } catch (HibernateException e) {
            t.rollback();
            e.printStackTrace();            
            return null;
        }
    }
    public Object searchSQL(String query) {
        Session s = getSession();
        Transaction t = s.beginTransaction();
        try {  
            Query q = s.createSQLQuery(query);
            Object obj = q.uniqueResult();
            t.commit();        
            s.close();    
            return obj;
        } catch (HibernateException e) {
            t.rollback();
            e.printStackTrace();            
            return null;
        }
    }
    @Deprecated
    public List<T> list(Class cd,Class cm,long id) {
        Session s = getSession();
        Transaction t = s.beginTransaction();
        try {            
            Query q = s.createQuery("from "+cd.getSimpleName()+" where id_"+cm.getSimpleName().toLowerCase()+"=:id");
            q.setParameter("id",id);
            List<T> l = (List)q.list();
            t.commit();        
            s.close();    
            return l;
        } catch (HibernateException e) {
            t.rollback();
            e.printStackTrace();            
            return null;
        }
    }
    @Deprecated
    public T search(Class c,long id) {
        Session s = getSession();
        Transaction t = s.beginTransaction();
        try {            
            Query q = s.createQuery("from "+c.getSimpleName()+" where id"+c.getSimpleName().toLowerCase()+"=:id");
            q.setParameter("id",id);
            List<T> l = (List)q.list();
            t.commit();        
            s.close();    
            for (T t1 : l) {
                return t1;
            }
            return null;
        } catch (HibernateException e) {
            t.rollback();
            e.printStackTrace();            
            return null;
        }
    }
    @Deprecated
    public T search(String query) {
        Session s = getSession();
        Transaction t = s.beginTransaction();
        try {            
            Query q = s.createQuery(query);
            T obj = (T) q.uniqueResult();
            t.commit();        
            s.close();
            return obj;
        } catch (HibernateException e) {
            t.rollback();
            e.printStackTrace();            
            return null;
        }
    }
    @Deprecated
    public long maxId(Class c) {
        Session s = getSession();
        Transaction t = s.beginTransaction();
        try {            
            List<T> l = (List)s.createQuery("max(id"+c.getSimpleName().toLowerCase()+") from "+c.getSimpleName()).list();
            t.commit();        
            s.close(); 
            return l.size()>0?l.size()+1:1;
        } catch (HibernateException e) {
            t.rollback();
            e.printStackTrace();            
            return 0;
        }
    }
    
    @Override
    public List listarSQL(String ref){
        return listSQL(ref);
    }
}
