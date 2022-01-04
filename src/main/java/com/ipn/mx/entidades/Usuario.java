package com.ipn.mx.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Persistence;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

//@author Isaac
 
@Entity
@Table(name = "Usuario", schema = "public")
@Data
@NoArgsConstructor

public class Usuario implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int user_id;
    
    @Column(name = "user_name", length = 50)
    private String user_name;
    @Column(name = "user_lastname", length = 50)
    private String user_lastname;
    @Column(name = "email", length = 100, unique = true)    
    private String email;
    @Column(name = "image")
    private String image;
    @Size(min = 6, max = 20, message = "El valor debe de estar entre 6 y 20")
    @Column(name = "nickname", length = 20)
    private String nickname;
    @Column(name = "password", length = 32)
    private String password;
    @Temporal(TemporalType.DATE)
    @Column(name = "createdAt")
    private Date createAt;
    
    @PrePersist
    public void prePersist(){
        this.createAt = new Date();
    }
    
    public static void main(String[] args) {
        Usuario u = new Usuario();
        
//        u.setUser_name("Pablo");
//        u.setUser_lastname("Perez");
//        u.setEmail("pablo@mail.com");
//        u.setImage("sin_imagen.jpg");
//        u.setNickname("pablto");
//        u.setPassword("psw");
        u.setUser_id(2);

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("test_project_v5PU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        //em.persist(u);
        //System.out.println(em.find(Usuario.class, u.getUser_id()));
        //em.remove(em.find(Usuario.class, u.getUser_id()));
        //em.merge(u); actualziar
        em.getTransaction().commit();        
    }

}
