/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.entidades.dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Isaac
 */
public class GraficaDAO {
    private Connection connection;
    
    private static final String SQL_GRAFICAR = "select  p2.product_name , count(*) as quantity from compra c2 inner join producto p2 on c2.product_id = p2.product_id group by 1 having count(*) > 0 ;";
    
    private void get_connection(){
        /*
        String user = "isfqhowexuxlcj";
        String passsword = "61280a1b8a97ba1076b0b9b613fe7faa3f3e1c15b171736e519e0109ddec7410";
        String url = "jdbc:postgresql://ec2-52-0-93-3.compute-1.amazonaws.com:5432/dc0l8bhmmeosrk";
        String driver_postgresql = "org.postgresql.Driver";   */
        
        String user = "postgres";
        String passsword = "n0m3l0";
        String url = "jdbc:postgresql://localhost:5432/wad_database_proyecto_final";
        String driver_postgresql = "org.postgresql.Driver";
        try {
            Class.forName(driver_postgresql); // Load the driver in the Class Loader
            connection = DriverManager.getConnection(url, user, passsword);
        }catch (ClassNotFoundException| SQLException e) {
            Logger.getLogger(GraficaDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    };
    
    public List get_graph_data() throws SQLException{
        get_connection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List result = new ArrayList();
        try {
            ps = connection.prepareStatement(SQL_GRAFICAR);
            rs = ps.executeQuery();
            while (rs.next()){
                GraficaDTO dto = new GraficaDTO();
                dto.setName(rs.getString("product_name"));
                dto.setValue(rs.getInt("quantity"));
                result.add(dto);
            }
        } finally {
            if (rs != null) rs.close();
            if (ps != null) rs.close();
            if (connection != null) connection.close();
        }   
                
        return result;
    }
}
