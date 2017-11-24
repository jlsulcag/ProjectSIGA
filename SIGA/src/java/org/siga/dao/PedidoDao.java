package org.siga.dao;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.siga.be.Pedido;
import org.siga.util.AbstractDA;
import org.springframework.stereotype.Repository;

@Repository("pedidoDao")
public class PedidoDao extends AbstractDA<Pedido> {

    @Override
    public long registrar(Pedido bean) {
        return save(bean);
    }

    @Override
    public long actualizar(Pedido bean) {
        return update(bean);
    }

    @Override
    public long eliminar(Pedido bean) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Pedido> listar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Pedido> listar(String ref) {
        return list(ref);
    }

    @Override
    public List<Pedido> listar(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Pedido buscar(long id) {
       return search(Pedido.class, id);
    }

    @Override
    public Pedido buscar(String ref) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long id() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<Pedido> listarFull(String string) {
        string = "from Pedido a order by a.numero desc";
        return listar(string);
    }

    public long buscarMaxNumero() {
        Session s = getSession();
        Transaction t = s.beginTransaction();
        long num = 0;
        try {
            String hql = "select max(a.numero) from Pedido a";
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

    public List<Pedido> listOrdenPedidoXEstado(String no_atendido) {
        String Hql = "from Pedido p where p.estado = '"+no_atendido+"'";
        return listar(Hql);
    }
}
