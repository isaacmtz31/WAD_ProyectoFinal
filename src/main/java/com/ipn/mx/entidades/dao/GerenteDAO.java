/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.entidades.dao;
        /*
 *
 * @author darkdestiny
 */



//@author Isaac
import com.ipn.mx.entidades.Categoria;
import com.ipn.mx.entidades.Gerente;

import com.ipn.mx.utillerias.HibernateUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author darkdestiny
 */
public class GerenteDAO implements Serializable {

    public void create(Gerente dto) {
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

    public void update(Gerente dto) {
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

    public void delete(Gerente dto) {
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

    public Gerente read(Gerente dto) {
        Session session = HibernateUtil.get_sessionFactory().getCurrentSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            dto = session.get(dto.getClass(), dto.getIdGerente());
            transaction.commit();
        } catch (HibernateException he) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        }
        return dto;
    }

    public List<Gerente> readAll() {
        Session s = HibernateUtil.get_sessionFactory().openSession();
        Transaction t = s.getTransaction();
        List<Gerente> lista = new ArrayList();
        try {
            t.begin();
            Query q = s.createQuery("from Gerente p order by p.idGerente");
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
    
    public List<Object> readAll2() {
        Session s = HibernateUtil.get_sessionFactory().openSession();
        Transaction t = s.getTransaction();
        List<Object> lista = new ArrayList();
        /*
        try {
            t.begin();
            //SELECT DISTINCT e FROM Employee e INNER JOIN e.tasks t where t.supervisor='Denise'
            Query q = s.createQuery("select c.nombreCategoria, count(p) from Gerente p INNER JOIN Categoria c ON p.idCategoria = c.idCategoria group by c.nombreCategoria");
            
            //SELECT d.name, e.name, e.email, e.address FROM department d INNER JOIN employee e ON d.id = e.dept_id;
            //Query q = s.createQuery("select c.nombreCategoria, p.nombreGerente from Gerente p INNER JOIN Categoria c ON p.idCategoria = c.idCategoria");
            
            /*
            SELECT p.nombregerente, count(*)
FROM gerente p
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
            /*
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
        }*/

        return lista;
    }
    
    public Gerente read_login(Gerente dto){
        Session session = HibernateUtil.get_sessionFactory().getCurrentSession();
        Transaction tran = session.getTransaction();
        List results = new ArrayList<>();
        try {
            tran.begin();
            Query q = session.createQuery("from Gerente U where U.email=" + "'" +dto.getEmail() +"'" + "and U.password=" + "'" +dto.getPassword() + "'");
            
            for (Gerente c : (List<Gerente>) q.list()) {                
                results.add(c);
            }
            tran.commit();
        } catch (HibernateException e) {
            if (tran != null && tran.isActive()) {
                tran.rollback();
            }
        }
        if(results.size() > 0 && results.size() < 2)
            return (Gerente)results.get(0);
        return null;
    }

    public static void main(String[] args) {
        Gerente dto = new Gerente();
        dto.setEmail("isaac@mail.com");
        dto.setPassword("123");
//        dto.setNombreGerente("tv");
//        dto.setDescripcionGerente("tv Chida");
//        dto.setExistenciaGerente(100);
//        dto.setPrecioGerente(5000);
//        Categoria cate  = new Categoria();
//        cate.setIdCategoria(2L);
//        dto.setIdCategoria(cate);

      //  dto.setIdGerente(1);
        GerenteDAO dao = new GerenteDAO();
        // dao.create(dto);
        //dao.update(dto);
        //dto.setIdGerente(Long.parseLong(8+""));
        System.out.println(dao.read_login(dto));
        //dao.delete(dto);
    }
    
}

