/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.siga.dao;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.siga.be.Kardex;
import org.siga.util.AbstractDA;
import org.springframework.stereotype.Repository;

@Repository("kardexDao")
public class KardexDao extends AbstractDA<Kardex>{

    @Override
    public long registrar(Kardex bean) {
        return save(bean);
    }

    @Override
    public long actualizar(Kardex bean) {
        return update(bean);
    }

    @Override
    public long eliminar(Kardex bean) {
        return delete(bean);
    }

    @Override
    public List<Kardex> listar() {
        return list(Kardex.class);
    }

    @Override
    public List<Kardex> listar(String ref) {
        return list(ref);
    }

    @Override
    public List<Kardex> listar(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Kardex buscar(long id) {
        return search(Kardex.class, id);
    }

    @Override
    public Kardex buscar(String ref) {
        return search(ref);
    }

    @Override
    public long id() {
        return maxId(Kardex.class);
    }

    public long maxNumero() {
        Session s = getSession();
        Transaction t = s.beginTransaction();        
        long num = 0;
        try {
            String hql = "select max(a.numero) from Kardex a";
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

    public long maxNumeroxproducto(long idproducto, long idalmacen) {
        Session s = getSession();
        Transaction t = s.beginTransaction();        
        long num = 0;
        try {
            String hql = "select max(a.nroOrden) from Kardex a where a.producto.idproducto = "+idproducto+" and a.almacen.idalmacen = "+idalmacen;
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
