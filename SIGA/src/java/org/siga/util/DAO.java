package org.siga.util;

import java.util.List;

public interface DAO<Tipo> {

    public long registrar(Tipo bean);

    public long actualizar(Tipo bean);

    public long eliminar(Tipo bean);

    public List<Tipo> listar();

    public List listarSQL(String ref);

    public List<Tipo> listar(String ref);

    public List<Tipo> listar(long id);

    public Tipo buscar(long id);

    public Tipo buscar(String ref);

    public long id();
}
