/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.entidades;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@author Isaac
 
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity //Clase para reprensetar una entidad en BD
@Table(name = "Categoria") //Representa una tabla que existirá en la BD
public class Categoria implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long category_id;
    
    @Column(name = "category_name", length = 50, nullable = false)
    private String category_name;
    
    @Column(name = "category_description", length = 100, nullable = false)
    private String category_description;
    
    @ManyToMany(mappedBy="categorias")
    private Set<Tienda> tiendas;
}
