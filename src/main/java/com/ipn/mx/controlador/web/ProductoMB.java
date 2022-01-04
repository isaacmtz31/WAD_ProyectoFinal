package com.ipn.mx.controlador.web;



import com.ipn.mx.entidades.Categoria;
import com.ipn.mx.entidades.Producto;
import com.ipn.mx.entidades.Tienda;
import com.ipn.mx.entidades.dao.CategoriaDAO;
import com.ipn.mx.entidades.dao.ProductoDAO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 *
 * @author Isaac
 */
@ManagedBean(name = "productoMB")
@SessionScoped
public class ProductoMB extends BaseBean implements Serializable {
    
    private final ProductoDAO dao = new ProductoDAO();
    private final CategoriaDAO cat_dao = new CategoriaDAO();
    private Tienda t_dto;
    private Producto dto;
    private List<Producto> product_list;
    private List<Categoria> category_list;
    
    
    public ProductoMB() {
    }

    public Producto getDto() {
        return dto;
    }

    public void setDto(Producto dto) {
        this.dto = dto;
    }

    public List<Producto> getProduct_list() {
        return product_list;
    }

    public void setProduct_list(List<Producto> product_list) {
        this.product_list = product_list;
    }

    public List<Categoria> getCategory_list() {
        return category_list;
    }

    public void setCategory_list(List<Categoria> category_list) {
        this.category_list = category_list;
    }
    
    @PostConstruct
    public void init(){
        product_list = new ArrayList<>();
        product_list = dao.read_all();
        category_list = new ArrayList<>();
        category_list = cat_dao.read_all();
    }
    
    public String prepareAdd(){
        dto = new Producto();
        setAction(ACT_CREATE);
        return "/producto/productoForm?faces-redirect=true";
    }
    
    public String prepareUpdate(){        
        setAction(ACT_UPDATE);
        return "/producto/productoForm?faces-redirect=true";
    }
    
    public String prepareIndex(){
        init();
        return "/producto/listadoProducto?faces-redirect=true";
    }
    
    public boolean validate(){
        boolean valid = true;
        //Aqui van las reglas de validacion
        if(dto.getProduct_name() != null){
            //do something
        }else{
            //do something else
        }
        return valid;
    }
    
    public String add(){
        boolean valid = validate();
        if (valid) {
            Categoria cat = new Categoria();
            cat.setCategory_id(dto.getId_cat());
            cat = cat_dao.read(cat);
            dto.setCategory_id(cat);
            
            dao.create(dto);
            if (valid) {
                return prepareIndex();
            }else{
                return prepareAdd();
            }
        }
        return prepareAdd();
    }
    
    public String update(){
        boolean valid = validate();
        if (valid) {
            Categoria cat = new Categoria();
            cat.setCategory_id(dto.getId_cat());
            cat = cat_dao.read(cat);
            dto.setCategory_id(cat);
            dao.update(dto);
            if (valid) {
                return prepareIndex();
            }else{
                return prepareUpdate();
            }
        }
        return prepareAdd();
    }
    
    public String delete(){
        dao.delete(dto);
        return prepareIndex();
    }
        
    
    public void select_one_product(ActionEvent event){
        String selected_key = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("claveSel");
        dto = new Producto();
        dto.setProduct_id(Integer.parseInt(selected_key));
        try {
            dto = dao.read(dto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public String get_filtered_products(){
        product_list = dao.read_all_by_store(t_dto);
        return "/producto/listadoProductoIntern?faces-redirect=true";
    }
    
    public void seleccionarProductosdeTienda(ActionEvent event){
        String claveSeleccionada = FacesContext.getCurrentInstance().
                getExternalContext().getRequestParameterMap().get("claveSel");
        
        try{
            t_dto = new Tienda();
            t_dto.setIdTienda(Integer.parseInt(claveSeleccionada));
            
        }catch(Exception e){
            
            e.printStackTrace();
        }
    }
    
}
