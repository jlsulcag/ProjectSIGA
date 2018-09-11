package org.siga.dao;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.siga.be.Menu;
import org.siga.be.UsuarioRol;
import org.siga.util.AbstractDA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class MenuDao extends AbstractDA<Menu> {

    @Autowired
    @Qualifier("sessionFactory")
    private SessionFactory sessionFactory;

    @Override
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Override
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
    public long registrar(Menu bean) {
        return save(bean);
    }

    @Override
    public long actualizar(Menu bean) {
        return update(bean);
    }

    @Override
    public long eliminar(Menu bean) {
        return delete(bean);
    }

    @Override
    public List<Menu> listar() {
        return list(Menu.class);
    }

    @Override
    public List<Menu> listar(String ref) {
        return list(ref);
    }

    @Override
    public List<Menu> listar(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Menu buscar(long id) {
        return search(Menu.class, id);
    }

    @Override
    public Menu buscar(String ref) {
        return search(ref);
    }

    @Override
    public long id() {
        return maxId(Menu.class);
    }


}
