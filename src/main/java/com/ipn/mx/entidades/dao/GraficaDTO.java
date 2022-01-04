/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.entidades.dao;


import java.io.Serializable;
import lombok.Data;

/**
 *
 * @author darkdestiny
 */
@Data
public class GraficaDTO implements Serializable {

    private String name;
    private int value;
}
