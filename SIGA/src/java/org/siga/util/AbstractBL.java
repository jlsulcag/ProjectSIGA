package org.siga.util;

import java.util.List;

public abstract class AbstractBL<T> implements DAO<T>{
    public abstract AbstractDA getDAO();
    public abstract void setDA(AbstractDA dao);
    @Deprecated
    public long save(T bean) {
        return getDAO().registrar(bean);
    }
    @Deprecated
    public long update(T bean) {
        return getDAO().actualizar(bean);
    }
    @Deprecated
    public long delete(T bean) {
        return getDAO().eliminar(bean);
    }
    @Deprecated
    public List<T> list() {
        return getDAO().listar();
    }
    @Deprecated
    public List<T> list(String ref) {
        return getDAO().listar(ref);
    }
    @Deprecated
    public List listSQL(String sql){
        return getDAO().listSQL(sql);
    }
    @Deprecated
    public List<T> list(long id) {
        return getDAO().listar(id);
    }
    @Deprecated
    public T search(long id) {
        return (T)getDAO().buscar(id);
    }
    @Deprecated
    public T search(String ref) {
        return (T)getDAO().buscar(ref);
    }
    @Deprecated
    public long maxId() {
        return getDAO().id();
    }

    @Override
    public List listarSQL(String ref){
        return listSQL(ref);
    }
    
}
