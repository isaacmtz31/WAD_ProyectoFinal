package com.ipn.mx.entidades.dao;

//@author Isaac
import com.ipn.mx.entidades.Categoria;
import com.ipn.mx.entidades.Gerente;
import com.ipn.mx.entidades.Tienda;

import com.ipn.mx.utillerias.HibernateUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class CategoriaDAO {

    public void create(Categoria dto) {
        Session session = HibernateUtil.get_sessionFactory().getCurrentSession();
        Transaction tran = session.getTransaction();
        try {
            tran.begin();
            session.save(dto);
            tran.commit();
        } catch (HibernateException he) {
            if (tran != null && tran.isActive()) {
                tran.rollback();
            }
        }
    }

    public void update(Categoria dto) {
        Session session = HibernateUtil.get_sessionFactory().getCurrentSession();
        Transaction tran = session.getTransaction();
        try {
            tran.begin();
            session.update(dto);
            tran.commit();
        } catch (HibernateException he) {
            if (tran != null && tran.isActive()) {
                tran.rollback();
            }
        }
    }

    public void delete(Categoria dto) {
        Session session = HibernateUtil.get_sessionFactory().getCurrentSession();
        Transaction tran = session.getTransaction();
        try {
            tran.begin();
            Categoria ct = session.get(Categoria.class, dto.getCategory_id());            
            if (ct != null){
                session.delete(ct);
            }            
            tran.commit();
        } catch (HibernateException he) {
            if (tran != null && tran.isActive()) {
                tran.rollback();
            }
        }
    }

    public Categoria read(Categoria dto) {
        Session session = HibernateUtil.get_sessionFactory().getCurrentSession();
        Transaction tran = session.getTransaction();
        try {
            tran.begin();
            dto = (session.get(Categoria.class, dto.getCategory_id()));
            tran.commit();
        } catch (HibernateException e) {
            if (tran != null && tran.isActive()) {
                tran.rollback();
            }
        }
        return dto;
    }

    public List read_all_by_store(Tienda dto){
        Session session = HibernateUtil.get_sessionFactory().getCurrentSession();
        Transaction tran = session.getTransaction();
        List<Categoria> results = new ArrayList<>();
        try {
            tran.begin();
            Query q = session.createSQLQuery("select * from Categoria c where category_id in (select category_id from cat_tienda ct where idtienda in (" + dto.getIdTienda()+ ")) order by category_id");
                 
            List<Object[]>  rows = q.list();
            
            for (Object[] row : rows) {         
                
                Categoria c = new Categoria();
                c.setCategory_id(Long.parseLong(row[0].toString()));
                c.setCategory_description(row[1].toString());
                c.setCategory_name(row[2].toString());
                results.add(c);
            }
            tran.commit();
        } catch (HibernateException he) {
            if (tran != null && tran.isActive()) {
                tran.rollback();
            }
        }

        return results;
        
    }
    
     public int create_rel_cat_tienda(Categoria c, Tienda t ){
        Session session = HibernateUtil.get_sessionFactory().getCurrentSession();
        Transaction tran = session.getTransaction();
        int x = -1;
        try {
            tran.begin();
            Query q = session.createSQLQuery("insert into cat_tienda values("+t.getIdTienda()+","+c.getCategory_id()+");");
            x = q.executeUpdate();
            tran.commit();
        } catch (HibernateException he) {
            if (tran != null && tran.isActive()) {
                tran.rollback();
            }
        }
        return x;
    }
    public List read_all() {
        Session session = HibernateUtil.get_sessionFactory().getCurrentSession();
        Transaction tran = session.getTransaction();
        List results = new ArrayList<>();
        try {
            tran.begin();
            Query q = session.createQuery("from Categoria c order by c.category_id");
            //results = q.list();
            
            for (Categoria c : (List<Categoria>) q.list()) {
                
                results.add(c);
            }
            tran.commit();
        } catch (HibernateException he) {
            if (tran != null && tran.isActive()) {
                tran.rollback();
            }
        }

        return results;
    }
    
   

    public static void main(String[] args) {
        Categoria dto = new Categoria();
        CategoriaDAO cat_dao = new CategoriaDAO();
        
        
        dto.setCategory_id(Long.parseLong(6+""));
        /*
        dto.setCategory_name("Electronicos nuevs");
        dto.setCategory_description("Articulos electrónicos en generales");
        cat_dao.create(dto);
        
        Set<Categoria> subs = new HashSet<Categoria>();
        subs.add(dto);
        */
        Tienda dot_t = new Tienda();
        dot_t.setIdTienda(1);
        /*
        dot_t.setIdGer(Long.parseLong(1+""));
        dot_t.setDireccionTienda("test");
        dot_t.setNombreTienda("asfs");
        dot_t.setTelefonoTienda(5555555);
        
        Gerente d = new Gerente();
        GerenteDAO g_dao = new GerenteDAO();        
        //d.setIdGerente();
        d.setNombreGerente("Juan");
        g_dao.create(d);
        
        dot_t.setIdGerente(d);
        TiendaDAO dao = new TiendaDAO();
        dot_t.setCategorias(subs)
               ;
        
        dao.create(dot_t);
        //dao.update(dto);
        //dao.create(dto);
        System.out.println();
        System.out.println(dao.readAll());*/
        //System.out.println(cat_dao.read_all_by_store("1"));
        System.out.println(cat_dao.create_rel_cat_tienda(dto, dot_t));
    }
}
