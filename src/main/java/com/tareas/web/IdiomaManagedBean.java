/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tareas.web;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

/**
 *
 * @author user
 */
@Named(value = "idiomaMB")
@SessionScoped
public class IdiomaManagedBean implements Serializable {

    private String idioma = "es";
    
    public IdiomaManagedBean() {
    }

    public String cambiarIdioma(String idioma) {
        this.idioma = idioma;
        return null;
    }

    public String getIdioma() {
        return idioma;
    }

}
