
package org.siga.dao;

import java.util.List;
import org.siga.be.Producto;
import org.siga.util.AbstractDA;
import org.springframework.stereotype.Repository;
@Repository("productoDao")
public class ProductoDao extends AbstractDA<Producto>{

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
        return list(Producto.class);
    }

    @Override
    public List<Producto> listar(String ref) {
        return list("from Producto p where p.descripcion like '%"+ref+"%'");
    }

    @Override
    public List<Producto> listar(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Producto buscar(long id) {
        return search(Producto.class, id);
    }

    @Override
    public Producto buscar(String ref) {
        return search("from Producto p where p.descripcion = '"+ref+"'");
    }

    @Override
    public long id() {
        return maxId(Producto.class);
    }
    
}
