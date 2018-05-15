
package org.siga.dao;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.siga.be.Almacen;
import org.siga.be.AlmacenProducto;
import org.siga.be.Producto;
import org.siga.util.AbstractDA;
import org.springframework.stereotype.Repository;
@Repository("almacenProductoDao")
public class AlmacenProductoDao extends AbstractDA<AlmacenProducto>{

    @Override
    public long registrar(AlmacenProducto bean) {
        return save(bean);
    }

    @Override
    public long actualizar(AlmacenProducto bean) {
        return update(bean);
    }

    @Override
    public long eliminar(AlmacenProducto bean) {
        return delete(bean);
    }

    @Override
    public List<AlmacenProducto> listar() {
        return list(AlmacenProducto.class);
    }

    @Override
    public List<AlmacenProducto> listar(String ref) {
        return list(ref);
    }

    @Override
    public List<AlmacenProducto> listar(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public AlmacenProducto buscar(long id) {
        return search(AlmacenProducto.class, id);
    }

    @Override
    public AlmacenProducto buscar(String ref) {
        return search(ref);
    }

    @Override
    public long id() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public AlmacenProducto buscarProductoxAlmacenyLote(String lote, long idAlmacendestino, Producto producto) {
        String hql = "from AlmacenProducto a where a.almacen.idalmacen = "+idAlmacendestino+" and a.lote = '"+ lote +"' and a.producto.idproducto = "+producto.getIdproducto();
        return buscar(hql);
    }

    public List<AlmacenProducto> listarXAlmacen(long idalmacen) {
        String hql = "from AlmacenProducto a left join fetch a.producto b left join fetch a.almacen c left join fetch b.clase d left join fetch b.familia e left join fetch b.unidadMedida g where c.idalmacen = "+idalmacen;
        return listar(hql);
    }

    public List<AlmacenProducto> listarRef(String ref, long idalmacen) {
        String hql = "from AlmacenProducto a left join fetch a.producto b left join fetch a.almacen c left join fetch b.clase d left join fetch b.familia e left join fetch b.unidadMedida g where b.descripcion like '%"+ref+"%' and c.idalmacen = "+idalmacen;
        return listar(hql);
    }

    public int obtenerUltimoNumero(long idproducto) {
        Session s = getSession();
        Transaction t = s.beginTransaction();
        int num = 0;
        try {
            String hql = "select max(a.ordenIngreso) from AlmacenProducto a where a.producto.idproducto = "+idproducto;
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

    public List<AlmacenProducto> listarFull() {
        String hql = "from AlmacenProducto a left join fetch a.producto b left join fetch a.almacen c left join fetch b.clase d left join fetch b.familia e left join fetch b.unidadMedida g ";
        return listar(hql);
    }
    
    public List<AlmacenProducto> listarPeps() {
        String hql = "from AlmacenProducto a left join fetch a.producto b left join fetch a.almacen c left join fetch b.clase d left join fetch b.familia e left join fetch b.unidadMedida g where a.stockActual > 0 and a.ordenIngreso=(select min(ap2.ordenIngreso) from AlmacenProducto ap2 where ap2.producto.idproducto = b.idproducto and ap2.stockActual >0)";
        return listar(hql);
    }

    public AlmacenProducto buscarxId(long idalmacenproducto) {
        String hql = "from AlmacenProducto a left join fetch a.producto b left join fetch a.almacen c left join fetch b.clase d left join fetch b.familia e left join fetch b.unidadMedida g where a.idalmacenproducto = "+idalmacenproducto;
        return buscar(hql);
    }

    public long buscarMinNumeroOrdenxProducto(long idproducto) {
        Session s = getSession();
        Transaction t = s.beginTransaction();
        long num = 0;
        try {
            String hql = "select a.idalmacenproducto from AlmacenProducto a where a.ordenIngreso = (select min(b.ordenIngreso) from AlmacenProducto b where b.producto.idproducto = "+idproducto+" and b.stockActual > 0) and a.producto.idproducto = "+idproducto+" and a.stockActual>0";
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

    public int buscarStockxProducto(long idproducto) {
        Session s = getSession();
        Transaction t = s.beginTransaction();
        int num = 0;
        try {
            String hql = "select a.stockActual from AlmacenProducto a where a.ordenIngreso = (select min(b.ordenIngreso) from AlmacenProducto b where b.producto.idproducto = "+idproducto+" and b.stockActual > 0) and a.producto.idproducto = "+idproducto+" and a.stockActual>0";
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
    
}
