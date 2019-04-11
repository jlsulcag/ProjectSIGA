package org.siga.dao;

import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.siga.be.AlmacenProducto;
import org.siga.be.Kardex;
import org.siga.be.NotaEntrada;
import org.siga.be.NotaEntradaDetalle;
import org.siga.be.OrdenCompra;
import org.siga.be.OrdenCompraSeguimiento;
import org.siga.bl.AlmacenProductoBl;
import org.siga.bl.KardexBl;
import org.siga.bl.OrdenCompraBl;
import org.siga.bl.OrdenCompraSeguimientoBl;
import org.siga.util.AbstractDA;
import org.siga.util.MensajeView;
import org.siga.util.Utilitarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository("notaIngresoDao")
public class NotaIngresoDao extends AbstractDA<NotaEntrada> {

    private Session sesion;
    private Transaction tx;
    private static final int SUCCESS = 1, ERROR = 0;

    @Autowired
    @Qualifier("sessionFactory")
    private SessionFactory sessionFactory;
    
    //@ManagedProperty(value = "#{almacenProducto}")
    @Autowired
    @Qualifier("almacenProducto")
    private AlmacenProducto almacenProducto;
    //@ManagedProperty(value = "#{almacenProductoBl}")
    @Autowired
    @Qualifier("almacenProductoBl")
    private AlmacenProductoBl almacenProductoBl;
    //@ManagedProperty(value = "#{kardex}")
    @Autowired
    @Qualifier("kardex")
    private Kardex kardex;
    //@ManagedProperty(value = "#{kardexBl}")
    @Autowired
    @Qualifier("kardexBl")
    private KardexBl kardexBl;
    //@ManagedProperty(value = "#{ordenCompraSeguimientoBl}")
    @Autowired
    @Qualifier("ordenCompraSeguimientoBl")
    private OrdenCompraSeguimientoBl ordenCompraSeguimientoBl;
    //@ManagedProperty(value = "#{ordenCompraSeguimiento}")
    @Autowired
    @Qualifier("ordenCompraSeguimiento")
    private OrdenCompraSeguimiento ordenCompraSeguimiento;
    //@ManagedProperty(value = "#{ordenCompraBl}")
    @Autowired
    @Qualifier("ordenCompraBl")
    private OrdenCompraBl ordenCompraBl;

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
        return search(ref);
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

    public NotaEntrada buscarxIdCompra(long idordencompra) {
        String hql = "from NotaEntrada a where a.ordenCompra.idordencompra = " + idordencompra;
        return buscar(hql);
    }

    public NotaEntrada buscarxIdPedido(long idpedido) {
        String hql = "from NotaEntrada a where a.pedido.idpedido = " + idpedido;
        return buscar(hql);
    }

    public List<NotaEntrada> listarCompras() {
        String hql = "from NotaEntrada a left join fetch a.ordenCompra b left join fetch b.proveedor c "
                + "where b.idordencompra is not null "
                + "order by b.numero desc";
        return listar(hql);
    }

    public int registrar(NotaEntrada notaEntrada, List<NotaEntradaDetalle> listNotaEntradaDetalle) {
        try {
            iniciarOperacion();
            sesion.save(notaEntrada);
            for (NotaEntradaDetalle obj : listNotaEntradaDetalle) {
                obj.setNotaEntrada(notaEntrada);
                sesion.save(obj);                
                //stock  almacen
                getAlmacenProducto().setProducto(obj.getProducto());
                getAlmacenProducto().setStockActual(obj.getTotalProductos());
                getAlmacenProducto().setProducto(obj.getProducto());
                getAlmacenProducto().setAlmacen(obj.getNotaEntrada().getAlmacenDestino());
                getAlmacenProducto().setLote(obj.getLote());
                getAlmacenProducto().setFechaVencimiento(obj.getFechaVencimiento());
                getAlmacenProducto().setValorCompraUnitario(obj.getValorCompra());
                getAlmacenProducto().setIdEquivalencia(obj.getIdEquivalencia());
                getAlmacenProducto().setFechaIngreso(new Date());
                //registrar el orden de ingreso para cumplir con FIFO
                int numOrden = getAlmacenProductoBl().obtenerUltimoNumero(obj.getProducto().getIdproducto());
                getAlmacenProducto().setOrdenIngreso(numOrden + 1);
                getAlmacenProducto().setUnidad(obj.getUnidadMedida());
                sesion.save(getAlmacenProducto());
                //kardex
                kardex.setProducto(obj.getProducto());
                kardex.setAlmacen(notaEntrada.getAlmacenDestino());
                kardex.setFechaMov(new Date());
                kardex.setMovimiento("INGRESO");
                kardex.setDetalle(notaEntrada.getTipoIngreso());
                kardex.setCantidad(obj.getTotalProductos());
                kardex.setValorUnit(obj.getValorCompra());
                kardex.setHoraMov(Utilitarios.horaActual());
                kardex.setNumero(getKardexBl().maxNumero() + 1);
                kardex.setNroOrden(getKardexBl().maxNumeroxproducto(obj.getProducto().getIdproducto(), notaEntrada.getAlmacenDestino().getIdalmacen()) + 1);
                kardex.setComprobante("");
                kardex.setNroComprobante("");
                sesion.save(kardex);
            }
            // Actualizar el estado de la orden de compra, solo si es que el ingreso proviene de una orden de compra
            if (notaEntrada.getOrdenCompra() != null && notaEntrada.getOrdenCompra().getIdordencompra() != 0) {
                OrdenCompra temp = new OrdenCompra();
                temp = getOrdenCompraBl().buscar(notaEntrada.getOrdenCompra().getIdordencompra());
                getOrdenCompraSeguimiento().setOrdenCompra(notaEntrada.getOrdenCompra());
                getOrdenCompraSeguimiento().setFecha(new Date());
                getOrdenCompraSeguimiento().setHora(Utilitarios.horaActual());
                getOrdenCompraSeguimiento().setNumero(getOrdenCompraSeguimientoBl().maxNumero(notaEntrada.getOrdenCompra().getIdordencompra()) + 1);
                HttpSession sesionUser = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
                if (sesionUser.getAttribute("idUsuario") != null) {
                    getOrdenCompraSeguimiento().setIdUser(Long.parseLong(sesionUser.getAttribute("idUsuario").toString()));
                } else {
                    getOrdenCompraSeguimiento().setIdUser(0);
                }
                getOrdenCompraSeguimiento().setObservacion("");
                sesion.save(getOrdenCompraSeguimiento());
            }
            tx.commit();
            return SUCCESS;
        } catch (HibernateException e) {
            manejaExcepcion(e);
            return ERROR;
        } finally {
            sesion.close();
        }
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

    public AlmacenProducto getAlmacenProducto() {
        return almacenProducto;
    }

    public void setAlmacenProducto(AlmacenProducto almacenProducto) {
        this.almacenProducto = almacenProducto;
    }

    public AlmacenProductoBl getAlmacenProductoBl() {
        return almacenProductoBl;
    }

    public void setAlmacenProductoBl(AlmacenProductoBl almacenProductoBl) {
        this.almacenProductoBl = almacenProductoBl;
    }

    public Kardex getKardex() {
        return kardex;
    }

    public void setKardex(Kardex kardex) {
        this.kardex = kardex;
    }

    public KardexBl getKardexBl() {
        return kardexBl;
    }

    public void setKardexBl(KardexBl kardexBl) {
        this.kardexBl = kardexBl;
    }

    public OrdenCompraSeguimientoBl getOrdenCompraSeguimientoBl() {
        return ordenCompraSeguimientoBl;
    }

    public void setOrdenCompraSeguimientoBl(OrdenCompraSeguimientoBl ordenCompraSeguimientoBl) {
        this.ordenCompraSeguimientoBl = ordenCompraSeguimientoBl;
    }

    public OrdenCompraSeguimiento getOrdenCompraSeguimiento() {
        return ordenCompraSeguimiento;
    }

    public void setOrdenCompraSeguimiento(OrdenCompraSeguimiento ordenCompraSeguimiento) {
        this.ordenCompraSeguimiento = ordenCompraSeguimiento;
    }

    public OrdenCompraBl getOrdenCompraBl() {
        return ordenCompraBl;
    }

    public void setOrdenCompraBl(OrdenCompraBl ordenCompraBl) {
        this.ordenCompraBl = ordenCompraBl;
    }
}
