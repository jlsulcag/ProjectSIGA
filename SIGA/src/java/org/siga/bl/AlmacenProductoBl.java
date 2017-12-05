
package org.siga.bl;

import java.util.List;
import org.siga.be.Almacen;
import org.siga.be.AlmacenProducto;
import org.siga.be.Producto;
import org.siga.dao.AlmacenProductoDao;
import org.siga.util.AbstractBL;
import org.siga.util.AbstractDA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
@Service("almacenProductoBl")
public class AlmacenProductoBl extends AbstractBL<AlmacenProducto>{
    @Autowired
    @Qualifier("almacenProductoDao")
    private AlmacenProductoDao dao;
    
    @Override
    public AbstractDA getDAO() {
        return dao;
    }

    @Override
    public void setDA(AbstractDA dao) {
        this.dao = (AlmacenProductoDao) dao;
    }

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
        return list();
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
        return search(id);
    }

    @Override
    public AlmacenProducto buscar(String ref) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long id() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public AlmacenProducto buscarProductoxAlmacenyLote(String lote, long idAlmacendestino, Producto producto) {
        return dao.buscarProductoxAlmacenyLote(lote, idAlmacendestino, producto);
    }

    public List<AlmacenProducto> listarXAlmacen(long idalmacen) {
        return dao.listarXAlmacen(idalmacen);
    }

    public List<AlmacenProducto> listarRef(String ref, long idalmacen) {
        return dao.listarRef(ref, idalmacen);
    }

    public int obtenerUltimoNumero(long idproducto) {
        return dao.obtenerUltimoNumero(idproducto);
    }

    public List<AlmacenProducto> listarFull() {
        return dao.listarFull();
    }

    public AlmacenProducto buscarxId(long idalmacenproducto) {
        return dao.buscarxId(idalmacenproducto);
    }

    public long buscarMinNumeroOrdenxProducto(long idproducto) {
        return dao.buscarMinNumeroOrdenxProducto(idproducto);
    }

    public int buscarStockxProducto(long idproducto) {
        return dao.buscarStockxProducto(idproducto);
    }

    
}
