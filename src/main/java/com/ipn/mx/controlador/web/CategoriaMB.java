/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.controlador.web;




import com.ipn.mx.entidades.Categoria;
import com.ipn.mx.entidades.Tienda;
import com.ipn.mx.entidades.dao.CategoriaDAO;
import com.ipn.mx.entidades.dao.TiendaDAO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.apache.commons.collections.CollectionUtils;

/**
 *
 * @author darkdestiny
 */
@ManagedBean(name = "categoriaMB")
@SessionScoped
public class CategoriaMB extends BaseBean implements Serializable {
    private final CategoriaDAO dao = new CategoriaDAO();
    private final TiendaDAO tDAO = new TiendaDAO();
    
    private Categoria dto;
    private List<Categoria> listaCategorias = dao.read_all();
    private Tienda t_dto;
    private List<Tienda> listaTiendas = tDAO.readAll();

    public List<Tienda> getListaTiendas() {
        return listaTiendas;
    }

    public void setListaTiendas(List<Tienda> listaTiendas) {
        this.listaTiendas = listaTiendas;
    }
    
    
    public Tienda getT_dto() {
        return t_dto;
    }

    public void setT_dto(Tienda t_dto) {
        this.t_dto = t_dto;
    }
    
    /**
     * Creates a new instance of CategoriaMB
     */
    public CategoriaMB() {
    }

    public Categoria getDto() {
        return dto;
    }

    public void setDto(Categoria dto) {
        this.dto = dto;
    }

    public List<Categoria> getListaCategorias() {
        return listaCategorias;
    }

    public void setListaCategorias(List<Categoria> listaCategorias) {
        this.listaCategorias = listaCategorias;
    }

    
    @PostConstruct
    public void init(){
        listaCategorias = new ArrayList<>();
        listaCategorias = dao.read_all();                
    }
    
    public String prepareAdd(){
        dto = new Categoria();        
        setAction(ACT_CREATE);
        return "/producto/categoriaForm?faces-redirect=true";
    }
    
    public String prepareAddCatTienda(){
        dto = new Categoria();  
        List<Categoria> t_listaCategorias = dao.read_all();                
        
        

        
        
        listaCategorias = (ArrayList<Categoria>)CollectionUtils.subtract(t_listaCategorias, listaCategorias);
        setAction(ACT_CREATE);
        return "/producto/categoriaTiendaForm?faces-redirect=true";
    }
    
    public String prepareUpdate(){
        setAction(ACT_UPDATE);
        return "/producto/categoriaForm?faces-redirect=true";
    }
    public String prepareIndex(){
        init();
        return "/producto/listadoCategorias?faces-redirect=true";
    }
    public boolean validate(){
        boolean valido = true;
        //Aqui les toca a ustedes poner todas las reglas de validacion 
       
        return valido;
    }
    
    public String add(){
        boolean valido = validate();
        if(valido){            
            dao.create(dto);
            if(valido){
                return prepareIndex();
            }else{
                return prepareAdd();
            }
        }
        return prepareAdd();
    }
    
    public String addTiendaCat(){
        boolean valido = validate();
        if(valido){                                   
            int x = dao.create_rel_cat_tienda(dto, t_dto);
            if(x == 1){
                return get_filtered_categories();
            }else{
                return prepareAddCatTienda();
            }
        }
        return prepareAdd();
    }
    
    public String update(){
        boolean valido = validate();
        if(valido){            
            dao.update(dto);
            if(valido){
                return prepareIndex();
            }else{
                return prepareUpdate();
            }
        }
        return prepareUpdate();
    }
    public String delete(){
        dao.delete(dto);
        return prepareIndex();
    }
    public void seleccionarCategoria(ActionEvent event){
        String claveSeleccionada = FacesContext.getCurrentInstance().
                getExternalContext().getRequestParameterMap().get("claveSel");
        dto = new Categoria();
        dto.setCategory_id(Long.parseLong(claveSeleccionada));
        try{
            dto = dao.read(dto);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public String get_filtered_categories(){
        listaCategorias = dao.read_all_by_store(t_dto);
        return "/producto/listadoCategoriasIntern?faces-redirect=true";
    }
    
    public void seleccionarCategoriasdeTiendas(ActionEvent event){
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
