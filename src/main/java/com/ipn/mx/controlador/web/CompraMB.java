/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.controlador.web;



import com.ipn.mx.entidades.Categoria;
import com.ipn.mx.entidades.Compra;
import com.ipn.mx.entidades.Producto;
import com.ipn.mx.entidades.dao.CategoriaDAO;
import com.ipn.mx.entidades.dao.CompraDAO;
import com.ipn.mx.entidades.dao.GraficaDAO;
import com.ipn.mx.entidades.dao.GraficaDTO;
import com.ipn.mx.entidades.dao.ProductoDAO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;

@ManagedBean(name = "compraMB")
@SessionScoped
public class CompraMB extends BaseBean implements Serializable {
    
    private final CompraDAO dao = new CompraDAO();
    private final CategoriaDAO catDAO = new CategoriaDAO();
    private final ProductoDAO p_dao = new ProductoDAO();
    
    private Compra dto;
    
    private List<Compra> listaCompras;
    private List<Categoria> listaCategorias;
    private Producto p_dto;
    
    
    public Producto getP_dto() {
        return p_dto;
    }

    public void setP_dto(Producto p_dto) {
        this.p_dto = p_dto;
    }
    
    
    /**
     * Creates a new instance of CompraMB
     */
    public CompraMB() {
    }

    public Compra getDto() {
        return dto;
    }

    public void setDto(Compra dto) {
        this.dto = dto;
    }

    public List<Compra> getListaCompras() {
        return listaCompras;
    }

    public void setListaCompras(List<Compra> listaCompras) {
        this.listaCompras = listaCompras;
    }

    public List<Categoria> getListaCategorias() {
        return listaCategorias;
    }

    public void setListaCategorias(List<Categoria> listaCategorias) {
        this.listaCategorias = listaCategorias;
    }
    
    @PostConstruct
    public void init(){
        listaCompras = new ArrayList<>();
        listaCompras = dao.readAll();
        
        listaCategorias = new ArrayList<>();
        listaCategorias = catDAO.read_all();
    }
    
    public String prepareAdd(){
        dto = new Compra();
        //p_dto = new Producto();
        setAction(ACT_CREATE);
        return "/compraForm?faces-redirect=true";
    }
    public String prepareUpdate(){
        setAction(ACT_UPDATE);
        return "/compraForm?faces-redirect=true";
    }
    public String prepareIndex(){
        init();
        return "/producto/listadoCompras?faces-redirect=true";
    }
    public boolean validate(){
        boolean valido = true;
        //Aqui les toca a ustedes poner todas las reglas de validacion 
       
        return valido;
    }
    
    public String add(){
        boolean valido = validate();
        if(valido){
          Date date = new Date();  
          dto.setFecha(date);
          dto.setIdProducto(p_dto);
          dto.setPago(dto.getCantidad() * p_dto.getProduct_price());
          dao.create(dto);
            if(valido){
                return prepareIndex();
            }else{
                return prepareAdd();
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
    
    public void seleccionarCompra(ActionEvent event){
        String claveSeleccionada = FacesContext.getCurrentInstance().
                getExternalContext().getRequestParameterMap().get("claveSel");
        dto = new Compra();
        dto.setIdCompra(Integer.parseInt(claveSeleccionada));
        try{
            dto = dao.read(dto);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void seleccionarProducto(ActionEvent event){
        String claveSeleccionada = FacesContext.getCurrentInstance().
                getExternalContext().getRequestParameterMap().get("claveSel");
        p_dto = new Producto();
        p_dto.setProduct_id(Integer.parseInt(claveSeleccionada));
        
        try{
            p_dto = p_dao.read(p_dto);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private void mostrarReporte() throws IOException {
       
        FacesContext fc =  FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
        InputStream rps = fc.getExternalContext().getResourceAsStream("/reportes/Cherry_1.jasper");
        ServletOutputStream sos = response.getOutputStream();
        fc.responseComplete();
        response.setContentType("application/pdf");
        
        try {
            JasperRunManager.runReportToPdfStream(rps, sos, null, dao.get_my_dao_connection());                        
            sos.flush();
            sos.close();
        } catch (IOException | JRException ex) {
            
        }
    }

 
    
  public void drawChart(OutputStream out, Object data) throws IOException {

    JFreeChart chart = ChartFactory.createPieChart("Sales", get_pructsData_chart(), true, true, Locale.ENGLISH);
    BufferedImage bufferedImage = chart.createBufferedImage(300, 300);
    ImageIO.write(bufferedImage, "gif", out);
  }

    private PieDataset get_pructsData_chart() {
        DefaultPieDataset dsPie = new DefaultPieDataset();
        try {
            GraficaDAO dao = new GraficaDAO();
            List results = dao.get_graph_data();

            for (int i = 0; i < results.size(); i++) {
                GraficaDTO dto = (GraficaDTO) results.get(i);
                dsPie.setValue(dto.getName(), dto.getValue());
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return dsPie;
    }
}
