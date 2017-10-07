
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
        return list(ref);
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
        return search(ref);
    }

    @Override
    public long id() {
        return maxId(Producto.class);
    }
    
    public List<Producto> listarFull(String ref) {
        ref = "from Producto p left join fetch p.clase left join fetch p.familia left join fetch p.unidadMedida";
        return listar(ref);
    }

    public List<Producto> listarRef(String txtBusqueda) {
        String ref = "from Producto p left join fetch p.clase left join fetch p.familia left join fetch p.unidadMedida where p.descripcion like '%"+txtBusqueda+"%'";
        return listar(ref);
    }

    public Producto buscarxID(long idproducto) {
        String hql = "from Producto p left join fetch p.clase left join fetch p.familia left join fetch p.unidadMedida where p.idproducto = "+idproducto;
        return buscar(hql);
    }
    
}
