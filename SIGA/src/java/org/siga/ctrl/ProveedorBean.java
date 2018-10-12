package org.siga.ctrl;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import org.siga.be.Familia;
import org.siga.be.Proveedor;
import org.siga.be.ProveedorFamilia;
import org.siga.bl.FamiliaBl;
import org.siga.bl.ProveedorBl;
import org.siga.bl.ProveedorFamiliaBl;
import org.siga.util.MensajeView;

@ManagedBean
@SessionScoped
public class ProveedorBean {

    @ManagedProperty(value = "#{proveedor}")
    private Proveedor proveedor;
    @ManagedProperty(value = "#{proveedorBl}")
    private ProveedorBl proveedorBl;

    @ManagedProperty(value = "#{proveedorFamilia}")
    private ProveedorFamilia proveedorFamilia;
    @ManagedProperty(value = "#{proveedorFamiliaBl}")
    private ProveedorFamiliaBl proveedorFamiliaBl;

    @ManagedProperty(value = "#{familiaBl}")
    private FamiliaBl familiaBl;
    @ManagedProperty(value = "#{familia}")
    private Familia familia;

    private List<Proveedor> listProveedores;
    private List<SelectItem> selectOneItemsProveedores;
    private String txtBusqueda;
    private long res;
    //private Object[] selectedFamilias;
    private List<String> listFamilia;
    private List<SelectItem> selectOneItemsFamilia;
    private List<Familia> listFamilias;

    public ProveedorBean() {
    }

    public void registrar() {
        proveedor.setCodigo(proveedor.getCodigo().toUpperCase());
        proveedor.setRazonSocial(proveedor.getRazonSocial().toUpperCase());
        proveedor.setRuc(proveedor.getRuc().toUpperCase());
        proveedor.setDireccion(proveedor.getDireccion().toUpperCase());
        proveedor.setTelefono(proveedor.getTelefono().toUpperCase());
        proveedor.setEmail(proveedor.getEmail().toUpperCase());
        proveedor.setContacto(proveedor.getContacto().toUpperCase());
        proveedor.setEstado("ACT");
        proveedor.setFechaReg(new Date());
        res = proveedorBl.registrar(proveedor);
        if (res == 0) {
            MensajeView.registroCorrecto();

//            if (!listFamilia.isEmpty()) {
//                for (String obj : listFamilia) {
//                    System.out.println("objeto  select .... "+obj);
//                }
//                System.out.println("proveedor ... "+proveedor.getIdproveedor());
//                registrarFamiliaProveedor(listFamilia, proveedor);
//            }
        } else {
            MensajeView.registroError();
        }
        listar();

    }

    public void registrarFamiliaProveedor(List<String> list, Proveedor proveedor) {

        for (String obj : list) {
            long id = Long.valueOf(obj);
            proveedorFamilia.setFamilia(familiaBl.buscar(id));
            proveedorFamilia.setProveedor(proveedor);
            proveedorFamilia.setEstado("ACT");
            proveedorFamiliaBl.registrar(proveedorFamilia);
        }
    }

    public List<SelectItem> getSelectOneItemsFamilia() {
        this.selectOneItemsFamilia = new LinkedList<SelectItem>();
        for (Familia fam : listarxTipo()) {
            this.setFamilia(fam);
            SelectItem selectItem = new SelectItem(getFamilia().getIdfamilia(), getFamilia().getDescripcion());
            this.selectOneItemsFamilia.add(selectItem);
        }
        return selectOneItemsFamilia;
    }

    public List<Familia> listarxTipo() {
        if (proveedor.isDeproducto() && proveedor.isDeservicio()) {
            return familiaBl.listarAmbosTipos("PRODUCTO", "SERVICIO");
        } else if (proveedor.isDeproducto()) {
            return familiaBl.listarxTipo("PRODUCTO");
        } else if (proveedor.isDeservicio()) {
            return familiaBl.listarxTipo("SERVICIO");
        } else {
            return familiaBl.listarxTipo("NINGUNO");
        }

    }

    public void actualizar() {
        Proveedor temp = new Proveedor();
        temp = buscarId();
        temp.setCodigo(proveedor.getCodigo().toUpperCase());
        temp.setRazonSocial(proveedor.getRazonSocial().toUpperCase());
        temp.setRuc(proveedor.getRuc().toUpperCase());
        temp.setDireccion(proveedor.getDireccion().toUpperCase());
        temp.setTelefono(proveedor.getTelefono().toUpperCase());
        temp.setEmail(proveedor.getEmail().toUpperCase());
        temp.setContacto(proveedor.getContacto().toUpperCase());
        temp.setEstado(proveedor.getEstado().toUpperCase());
        res = proveedorBl.actualizar(temp);
        if (res == 0) {
            MensajeView.actCorrecto();
        } else {
            MensajeView.actError();
        }
        listar();

    }

    public void buscarRef() {
        setListProveedores(proveedorBl.buscarRef(txtBusqueda.toUpperCase()));
    }

    public List<Proveedor> complete(String query) {
        return proveedorBl.buscarRef(query);
    }

    public void limpiar() {
        proveedor.setIdproveedor(0);
        proveedor.setCodigo("");
        proveedor.setRazonSocial("");
        proveedor.setRuc("");
        proveedor.setDireccion("");
        proveedor.setTelefono("");
        proveedor.setEmail("");
        proveedor.setContacto("");
        proveedor.setTelfFijo("");
        proveedor.setTipoPersona("");
        proveedor.setDeproducto(false);
        proveedor.setDeservicio(false);
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public ProveedorBl getProveedorBl() {
        return proveedorBl;
    }

    public void setProveedorBl(ProveedorBl proveedorBl) {
        this.proveedorBl = proveedorBl;
    }

    public List<Proveedor> getListProveedores() {
        return listProveedores;
    }

    public void setListProveedores(List<Proveedor> listProveedores) {
        this.listProveedores = listProveedores;
    }

    public String getTxtBusqueda() {
        return txtBusqueda;
    }

    public void setTxtBusqueda(String txtBusqueda) {
        this.txtBusqueda = txtBusqueda;
    }

    @PostConstruct
    private void listar() {
        setListProveedores(proveedorBl.listar());
    }

    private Proveedor buscarId() {
        return proveedorBl.buscar(proveedor.getIdproveedor());
    }

    public List<SelectItem> getSelectOneItemsProveedores() {
        this.selectOneItemsProveedores = new LinkedList<SelectItem>();
        for (Proveedor obj : listarProveedor()) {
            this.setProveedor(obj);
            SelectItem selectItem = new SelectItem(getProveedor().getIdproveedor(), getProveedor().getRazonSocial());
            this.selectOneItemsProveedores.add(selectItem);
        }
        return selectOneItemsProveedores;
    }

    public void setSelectOneItemsProveedores(List<SelectItem> selectOneItemsProveedores) {
        this.selectOneItemsProveedores = selectOneItemsProveedores;
    }

    private List<Proveedor> listarProveedor() {
        setListProveedores(proveedorBl.listar());
        return getListProveedores();
    }

    public ProveedorFamilia getProveedorFamilia() {
        return proveedorFamilia;
    }

    public void setProveedorFamilia(ProveedorFamilia proveedorFamilia) {
        this.proveedorFamilia = proveedorFamilia;
    }

    public ProveedorFamiliaBl getProveedorFamiliaBl() {
        return proveedorFamiliaBl;
    }

    public void setProveedorFamiliaBl(ProveedorFamiliaBl proveedorFamiliaBl) {
        this.proveedorFamiliaBl = proveedorFamiliaBl;
    }

    public List<String> getListFamilia() {
        return listFamilia;
    }

    public void setListFamilia(List<String> listFamilia) {
        this.listFamilia = listFamilia;
    }

    public FamiliaBl getFamiliaBl() {
        return familiaBl;
    }

    public void setFamiliaBl(FamiliaBl familiaBl) {
        this.familiaBl = familiaBl;
    }

    public void setSelectOneItemsFamilia(List<SelectItem> selectOneItemsFamilia) {
        this.selectOneItemsFamilia = selectOneItemsFamilia;
    }

    public List<Familia> getListFamilias() {
        return listFamilias;
    }

    public void setListFamilias(List<Familia> listFamilias) {
        this.listFamilias = listFamilias;
    }

    public Familia getFamilia() {
        return familia;
    }

    public void setFamilia(Familia familia) {
        this.familia = familia;
    }

}
