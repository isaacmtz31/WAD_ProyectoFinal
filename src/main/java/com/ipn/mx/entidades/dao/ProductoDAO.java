package com.ipn.mx.entidades.dao;

//@author Isaac
import com.ipn.mx.entidades.Categoria;
import com.ipn.mx.entidades.Producto;
import com.ipn.mx.entidades.Tienda;
import com.ipn.mx.utillerias.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class ProductoDAO{

    public void create(Producto dto) {
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

    public void update(Producto dto) {
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

    public void delete(Producto dto) {
        Session session = HibernateUtil.get_sessionFactory().getCurrentSession();
        Transaction tran = session.getTransaction();
        try {
            tran.begin();
            Producto ct = session.get(Producto.class, dto.getProduct_id());            
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

    public Producto read(Producto dto) {
        Session session = HibernateUtil.get_sessionFactory().getCurrentSession();
        Transaction tran = session.getTransaction();
        try {
            tran.begin();
            dto = session.get(Producto.class, dto.getProduct_id());
            tran.commit();
        } catch (HibernateException e) {
            if (tran != null && tran.isActive()) {
                tran.rollback();
            }
        }
        return dto;
    }

    public List read_all() {
        Session session = HibernateUtil.get_sessionFactory().getCurrentSession();
        Transaction tran = session.getTransaction();
        List results = new ArrayList<>();
        try {
            tran.begin();
            Query q = session.createQuery("from Producto p order by p.product_id");
            results = q.list();            
            tran.commit();
        } catch (HibernateException he) {
            if (tran != null && tran.isActive()) {
                tran.rollback();
            }
        }

        return results;
    }
    
    public List read_all_by_store(Tienda dto){
        
        Session session = HibernateUtil.get_sessionFactory().getCurrentSession();
        Transaction tran = session.getTransaction();
        List<Producto> results = new ArrayList<>();
        CategoriaDAO cat_dao = new CategoriaDAO();
        try {
            tran.begin();
            Query q = session.createSQLQuery("select p.product_id, p.product_description , p.product_name , p.product_price , p.product_stock , categoria.category_id , categoria.category_description, categoria.category_name from producto p inner join categoria on p.category_id = categoria.category_id where p.category_id in (select category_id from categoria c where category_id in (select category_id from cat_tienda ct where idtienda in ("+dto.getIdTienda()+")));");
            
            List<Object[]>  rows = q.list();
            
            for (Object[] row : rows) {         
                Categoria cat_dto = new Categoria();
                Producto c = new Producto();
                c.setProduct_id(Integer.parseInt(row[0].toString()));
                c.setProduct_description(row[1].toString());
                c.setProduct_name(row[2].toString());
                c.setProduct_price(Double.parseDouble(row[3].toString()));
                c.setProduct_stock(Integer.parseInt(row[4].toString()));
                cat_dto.setCategory_id(Long.parseLong(row[5].toString()));     
                cat_dto.setCategory_description(row[6].toString());
                cat_dto.setCategory_name(row[7].toString());
                c.setCategory_id(cat_dto);
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
        Producto dto = new Producto();
        dto.setProduct_id(2);
        
       Tienda d = new Tienda();
       d.setIdTienda(1);
        //dto.getEntity().setCategory_id(2);
        //dto.getEntity().setCategory_name("Electronicos");
        //dto.getEntity().setCategory_description("Articulos electrónicos en general");
        ProductoDAO dao = new ProductoDAO();
        //dao.update(dto);
        //System.out.println(dao.read(dto));
        System.out.println(dao.read_all_by_store(d));
        //System.out.println(dao.read_all());
        //System.out.println(dao.read_all());
    }
}
