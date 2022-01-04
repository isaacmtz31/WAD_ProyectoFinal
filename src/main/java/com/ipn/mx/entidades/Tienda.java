/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.entidades;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author darkdestiny
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table (name = "Tienda")
public class Tienda implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idTienda;
    private String nombreTienda;
    private long telefonoTienda;
    private String direccionTienda;
        
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="idGerente")
    private Gerente idGerente;
    @Transient
    private Long idGer;
    
    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
        name = "cat_tienda", 
        joinColumns = { @JoinColumn(name = "idTienda") }, 
        inverseJoinColumns = { @JoinColumn(name = "category_id") }
    )
    Set<Categoria> categorias = new HashSet<>();
    
}
