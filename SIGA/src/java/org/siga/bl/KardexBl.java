/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.siga.bl;

import java.util.List;
import org.siga.be.Kardex;
import org.siga.dao.KardexDao;
import org.siga.util.AbstractBL;
import org.siga.util.AbstractDA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("kardexBl")
public class KardexBl extends AbstractBL<Kardex>{
    @Autowired
    @Qualifier("kardexDao")
    private KardexDao dao;
    
    @Override
    public AbstractDA getDAO() {
        return dao;
    }

    @Override
    public void setDA(AbstractDA dao) {
        this.dao = (KardexDao) dao;
    }

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
        return list();
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
        return search(id);
    }

    @Override
    public Kardex buscar(String ref) {
        return search(ref);
    }

    @Override
    public long id() {
        return maxId();
    }

    public long maxNumero() {
        return dao.maxNumero();
    }

    public long maxNumeroxproducto(long idproducto, long idalmacen) {
        return dao.maxNumeroxproducto(idproducto, idalmacen);
    }

    public List<Kardex> generarKardex(long idproducto, long idalmacen) {
        return dao.generarKardex(idproducto, idalmacen);
    }
    
}
