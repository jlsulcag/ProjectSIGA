package org.siga.dao;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.siga.be.OrdenCompra;
import org.siga.util.AbstractDA;
import org.siga.util.HibernateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository("ordenCompraDao")
public class OrdenCompraDao extends AbstractDA<OrdenCompra> {

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

//    private void iniciarOperacion() throws HibernateException {
//        sesion = HibernateUtil.getSessionFactory().openSession();
//        tx = sesion.beginTransaction();
//    }
//    
//    private void manejaExcepcion(HibernateException he) {
//        tx.rollback();
//        throw new HibernateException("Ocurrió un error en la capa de acceso a datos", he);
//    }
    @Override
    public long registrar(OrdenCompra bean) {
        return save(bean);
    }

    @Override
    public long actualizar(OrdenCompra bean) {
        return update(bean);
    }

    @Override
    public long eliminar(OrdenCompra bean) {
        return delete(bean);
    }

    @Override
    public List<OrdenCompra> listar() {
        return list(OrdenCompra.class);
    }

    @Override
    public List<OrdenCompra> listar(String ref) {
        return list(ref);
    }

    @Override
    public List<OrdenCompra> listar(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public OrdenCompra buscar(long id) {
        return search(OrdenCompra.class, id);
    }

    @Override
    public OrdenCompra buscar(String ref) {
        return search(ref);
    }

    @Override
    public long id() {
        return maxId(OrdenCompra.class);
    }

    public List<OrdenCompra> listarFull(String string) {
        string = "from OrdenCompra a left join fetch a.proveedor b order by a.numero desc";
        return listar(string);
    }

    public long buscarUltimoNumero() {
        Session s = getSession();
        Transaction t = s.beginTransaction();
        long num = 0;
        try {
            String hql = "select max(a.numero) from OrdenCompra a";
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

    public List<OrdenCompra> listOrdenCompraXEstado(String estado) {
        String hql = "from OrdenCompra a left join fetch a.proveedor b left join fetch a.almacenDestino c where a.estado = '"+estado+"'";
        return listar(hql);
    }

    public OrdenCompra buscarXId(long idordencompra) {
        String hql = "from OrdenCompra a left join fetch a.proveedor b left join fetch a.almacenDestino c where a.idordencompra = "+idordencompra;
        return buscar(hql);
    }

}
