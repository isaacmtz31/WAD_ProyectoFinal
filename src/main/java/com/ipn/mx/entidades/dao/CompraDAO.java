/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.entidades.dao;
/**
 *
 * @author darkdestiny
 */

import com.ipn.mx.entidades.Compra;

import com.ipn.mx.utillerias.HibernateUtil;

import java.io.Serializable;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author darkdestiny
 */
public class CompraDAO implements Serializable {

    public Connection get_my_dao_connection(){
        SessionFactory session = HibernateUtil.get_sessionFactory();
        return HibernateUtil.get_connection(session);
    }
    
    public void create(Compra dto) {
        Session s = HibernateUtil.get_sessionFactory().getCurrentSession();
        Transaction t = s.getTransaction();
        try {
            t.begin();
            s.save(dto);
            t.commit();
        } catch (HibernateException he) {
            if (t != null && t.isActive()) {
                t.rollback();
            }
        }
    }

    public void update(Compra dto) {
        Session s = HibernateUtil.get_sessionFactory().getCurrentSession();
        Transaction t = s.getTransaction();
        try {
            t.begin();
            s.update(dto);
            t.commit();
        } catch (HibernateException he) {
            if (t != null && t.isActive()) {
                t.rollback();
            }
        }
    }

    public void delete(Compra dto) {
        Session s = HibernateUtil.get_sessionFactory().getCurrentSession();
        Transaction t = s.getTransaction();
        try {
            t.begin();
            s.delete(dto);
            t.commit();
        } catch (HibernateException he) {
            if (t != null && t.isActive()) {
                t.rollback();
            }
        }
    }

    public Compra read(Compra dto) {
        Session session = HibernateUtil.get_sessionFactory().getCurrentSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            dto = session.get(dto.getClass(), dto.getIdCompra());
            transaction.commit();
        } catch (HibernateException he) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        }
        return dto;
    }

    public List<Compra> readAll() {
        Session s = HibernateUtil.get_sessionFactory().openSession();
        Transaction t = s.getTransaction();
        List<Compra> lista = new ArrayList();
        try {
            t.begin();
            Query q = s.createQuery("from Compra p order by p.idCompra");
            lista = q.list();
//            for (Categoria c: (List<Categoria>) q.list()) {
//                CategoriaDTO dto = new CategoriaDTO();
//                dto.setEntidad(c);
//                lista.add(dto);
//            }

            t.commit();
        } catch (HibernateException he) {
            if (t != null && t.isActive()) {
                t.rollback();
            }
        }

        return lista;
    }
    public List<GraficaDTO> readAll2() {
        Session s = HibernateUtil.get_sessionFactory().openSession();
        Transaction t = s.getTransaction();
        List<GraficaDTO> lista = new ArrayList();
        try {
            t.begin();
            //SELECT DISTINCT e FROM Employee e INNER JOIN e.tasks t where t.supervisor='Denise'
            Query q = s.createQuery("select c.nombreCategoria, count(p) from Compra p INNER JOIN Categoria c ON p.idCategoria = c.idCategoria group by c.nombreCategoria");
            
            //SELECT d.name, e.name, e.email, e.address FROM department d INNER JOIN employee e ON d.id = e.dept_id;
            //Query q = s.createQuery("select c.nombreCategoria, p.nombreCompra from Compra p INNER JOIN Categoria c ON p.idCategoria = c.idCategoria");
            
            /*
            SELECT p.nombrecompra, count(*)
FROM compra p
	INNER JOIN categoria c ON 
	 p.idcategoria = c.idcategoria
            */
            //lista = q.list();
            /*
             GraficaDTO dto = new GraficaDTO();
                 dto.setNombreCategoria(rs.getString("nombreCategoria"));
                 dto.setCantidad(rs.getInt("cantidad"));
                 lista.add(dto);
            */
            
            for (GraficaDTO c: (List<GraficaDTO>) q.list()) {
                GraficaDTO dto = new GraficaDTO();
                dto.setName(c.getName());
                dto.setValue(c.getValue());
                lista.add(dto);
            }

            t.commit();
        } catch (HibernateException he) {
            if (t != null && t.isActive()) {
                t.rollback();
            }
        }

        return lista;
    }

    public static void main(String[] args) {
        Compra dto = new Compra();
//        dto.setNombreCompra("tv");
//        dto.setDescripcionCompra("tv Chida");
//        dto.setExistenciaCompra(100);
//        dto.setPrecioCompra(5000);
//        Categoria cate  = new Categoria();
//        cate.setIdCategoria(2L);
//        dto.setIdCategoria(cate);

      //  dto.setIdCompra(1);
        CompraDAO dao = new CompraDAO();
        // dao.create(dto);
        //dao.update(dto);
        System.out.println(dao.readAll2());
        //System.out.println(dao.read(dto));
        //dao.delete(dto);
    }
}

