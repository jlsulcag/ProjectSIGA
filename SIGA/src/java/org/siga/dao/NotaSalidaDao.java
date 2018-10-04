
package org.siga.dao;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.siga.be.NotaSalida;
import org.siga.util.AbstractDA;
import org.springframework.stereotype.Repository;

@Repository("notaSalidaDao")
public class NotaSalidaDao extends AbstractDA<NotaSalida>{

    @Override
    public long registrar(NotaSalida bean) {
        return save(bean);
    }

    @Override
    public long actualizar(NotaSalida bean) {
        return update(bean);
    }

    @Override
    public long eliminar(NotaSalida bean) {
        return delete(bean);
    }

    @Override
    public List<NotaSalida> listar() {
        return list(NotaSalida.class);
    }

    @Override
    public List<NotaSalida> listar(String ref) {
        return list(ref);
    }

    @Override
    public List<NotaSalida> listar(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public NotaSalida buscar(long id) {
        return search(NotaSalida.class, id);
    }

    @Override
    public NotaSalida buscar(String ref) {
        return search(ref);
    }

    @Override
    public long id() {
        return maxId(NotaSalida.class);
    }

    public int maxNumero() {
        Session s = getSession();
        Transaction t = s.beginTransaction();
        int num = 0;
        try {
            String hql = "select max(a.numero) from NotaSalida a";
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

    public NotaSalida buscarxIdPedido(long idpedido) {
        String hql = "from NotaSalida a left join fetch a.pedido b left join fetch a.tipomovimiento c where b.idpedido = "+idpedido;
        return buscar(hql);
    }
    
}
