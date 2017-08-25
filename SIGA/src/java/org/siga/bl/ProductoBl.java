
package org.siga.bl;

import java.util.List;
import org.siga.be.Producto;
import org.siga.dao.ProductoDao;
import org.siga.util.AbstractBL;
import org.siga.util.AbstractDA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("productoBl")
public class ProductoBl extends AbstractBL<Producto>{
    @Autowired
    @Qualifier("productoDao")
    private ProductoDao dao;

    @Override
    public AbstractDA getDAO() {
        return dao;
    }

    @Override
    public void setDA(AbstractDA dao) {
        this.dao = (ProductoDao) dao;
    }

    @Override
    public long registrar(Producto bean) {
        return save(bean);
    }

    @Override
    public long actualizar(Producto bean) {
        return update(bean);
    }

    @Override
    public long eliminar(Producto bean) {
        return delete(bean);
    }

    @Override
    public List<Producto> listar() {
        return list();
    }

    @Override
    public List<Producto> listar(String ref) {
        return list(ref);
    }

    @Override
    public List<Producto> listar(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Producto buscar(long id) {
        return search(id);
    }

    @Override
    public Producto buscar(String ref) {
        return search(ref);
    }

    @Override
    public long id() {
        return maxId();
    }
    
    public List<Producto> listarFull(String ref) {
        return dao.listarFull(ref);
    }

    public List<Producto> listarRef(String txtBusqueda) {
        return dao.listarRef(txtBusqueda);
    }

    public Producto buscarxID(long idproducto) {
        return dao.buscarxID(idproducto);
    }
    
}
