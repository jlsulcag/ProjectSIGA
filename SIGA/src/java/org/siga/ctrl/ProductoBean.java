package org.siga.ctrl;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import org.siga.be.Clase;
import org.siga.be.Equivalencia;
import org.siga.be.Familia;
import org.siga.be.Producto;
import org.siga.be.UnidadMedida;
import org.siga.bl.ClaseBl;
import org.siga.bl.EquivalenciaBl;
import org.siga.bl.FamiliaBl;
import org.siga.bl.ProductoBl;
import org.siga.util.MensajeView;

@ManagedBean
@ViewScoped
public class ProductoBean {

    @ManagedProperty(value = "#{productoBl}")
    private ProductoBl productoBl;
    @ManagedProperty(value = "#{producto}")
    private Producto producto;
    @ManagedProperty(value = "#{claseBl}")
    private ClaseBl claseBl;
    @ManagedProperty(value = "#{clase}")
    private Clase clase;
    @ManagedProperty(value = "#{familiaBl}")
    private FamiliaBl familiaBl;
    @ManagedProperty(value = "#{familia}")
    private Familia familia;

    private List<SelectItem> selectOneItemsFamilia;
    private List<SelectItem> selectOneItemsClase;
    private List<SelectItem> selectOneItemsProducto;
    private List<Familia> listFamilias;
    private List<Clase> listClases;
    private String txtBusqueda;
    private List<Producto> listaProductos;
    private long res;
    private long idFamiliaTemp;

    //Metodos transaccionales
    @PostConstruct
    public void listar() {
        setListaProductos(productoBl.listarFull(""));
    }

    public List<Familia> listarFamilia() {
        setListFamilias(familiaBl.listar());
        return getListFamilias();
    }

    public void registrar() {
        //producto.setIdFamilia(idFamiliaTemp);
        producto.setDescripcion(producto.getDescripcion().toUpperCase());
        producto.setCodigo(producto.getCodigo().toUpperCase());
        producto.setFechaReg(new Date());
        producto.setEstado("ACT");
        res = productoBl.registrar(producto);
        if (res == 0) {
            MensajeView.registroCorrecto();
        } else {
            MensajeView.registroError();
        }
        producto = new Producto();
        listar();

    }

    public void actualizar() {
        Producto temp = new Producto();
        temp = buscarId();
        temp.setDescripcion(producto.getDescripcion().toUpperCase());
        temp.setCodigo(producto.getCodigo().toUpperCase());
        temp.setStockMinimo(producto.getStockMinimo());
        temp.setStockMaximo(producto.getStockMaximo());
        temp.setUnidadMedida(producto.getUnidadMedida());
        temp.setEstado(producto.getEstado());
        temp.setFechaReg(new Date());
        temp.setFamilia(producto.getFamilia());
        temp.setClase(producto.getClase());
        temp.setFraccion(producto.getFraccion());
        res = productoBl.actualizar(temp);
        if (res == 0) {
            MensajeView.actCorrecto();
        } else {
            MensajeView.actError();
        }
        listar();
    }

    public void limpiar() {
        producto.setIdproducto(0);
        producto.setDescripcion("");
        producto.setCodigo("");
        producto.setStockMinimo(0);
        producto.setStockMaximo(0);
        producto.setUnidadMedida(new UnidadMedida());
        producto.setEstado("");
        producto.setFechaReg(null);
        producto.setClase(new Clase());
        producto.setFamilia(new Familia());
        producto.setFraccion(0);
    }

    public void buscarRef() {
        setListaProductos(productoBl.listarRef(getTxtBusqueda().toUpperCase()));
    }

    public void buscarProducto(long idproducto) {
        producto = getProductoBl().buscarxID(idproducto);
    }

    public List<Clase> listarClasesXFamilia() {
        return claseBl.listarClasePorFamilia(producto.getFamilia().getIdfamilia());
    }

    public List<SelectItem> getSelectOneItemsFamilia() {
        this.selectOneItemsFamilia = new LinkedList<SelectItem>();
        for (Familia fam : listarFamilia()) {
            this.setFamilia(fam);
            SelectItem selectItem = new SelectItem(getFamilia().getIdfamilia(), getFamilia().getDescripcion());
            this.selectOneItemsFamilia.add(selectItem);
        }
        return selectOneItemsFamilia;
    }
    //fin Metodos

    public ProductoBean() {
    }

    public ProductoBl getProductoBl() {
        return productoBl;
    }

    public void setProductoBl(ProductoBl productoBl) {
        this.productoBl = productoBl;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public String getTxtBusqueda() {
        return txtBusqueda;
    }

    public void setTxtBusqueda(String txtBusqueda) {
        this.txtBusqueda = txtBusqueda;
    }

    public List<Producto> getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(List<Producto> listaProductos) {
        this.listaProductos = listaProductos;
    }

    private Producto buscarId() {
        return productoBl.buscarxID(producto.getIdproducto());
    }

    public ClaseBl getClaseBl() {
        return claseBl;
    }

    public void setClaseBl(ClaseBl claseBl) {
        this.claseBl = claseBl;
    }

    public Clase getClase() {
        return clase;
    }

    public void setClase(Clase clase) {
        this.clase = clase;
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

    public FamiliaBl getFamiliaBl() {
        return familiaBl;
    }

    public void setFamiliaBl(FamiliaBl familiaBl) {
        this.familiaBl = familiaBl;
    }

    public Familia getFamilia() {
        return familia;
    }

    public void setFamilia(Familia familia) {
        this.familia = familia;
    }

    public long getIdFamiliaTemp() {
        return idFamiliaTemp;
    }

    public void setIdFamiliaTemp(long idFamiliaTemp) {
        this.idFamiliaTemp = idFamiliaTemp;
    }

    public List<SelectItem> getSelectOneItemsClase() {
        this.selectOneItemsClase = new LinkedList<SelectItem>();
        for (Clase obj : listarClasesXFamilia()) {
            this.setClase(obj);
            SelectItem selectItem = new SelectItem(getClase().getIdclase(), getClase().getDescripcion());
            this.selectOneItemsClase.add(selectItem);
        }
        return selectOneItemsClase;
    }

    public void setSelectOneItemsClase(List<SelectItem> selectOneItemsClase) {
        this.selectOneItemsClase = selectOneItemsClase;
    }

    public List<Clase> getListClases() {
        return listClases;
    }

    public void setListClases(List<Clase> listClases) {
        this.listClases = listClases;
    }

    public List<SelectItem> getSelectOneItemsProducto() {
        this.selectOneItemsProducto = new LinkedList<SelectItem>();
        for (Producto obj : listarProducto()) {
            this.setProducto(obj);
            SelectItem selectItem = new SelectItem(getProducto().getIdproducto(), getProducto().getDescripcion());
            this.selectOneItemsProducto.add(selectItem);
        }
        return selectOneItemsProducto;
    }

    public void setSelectOneItemsProducto(List<SelectItem> selectOneItemsProducto) {
        this.selectOneItemsProducto = selectOneItemsProducto;
    }

    private Iterable<Producto> listarProducto() {
        setListaProductos(productoBl.listar());
        return getListaProductos();
    }

}
