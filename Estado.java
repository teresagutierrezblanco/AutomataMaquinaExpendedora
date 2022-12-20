/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package practica2;

import java.util.ArrayList;


public class Estado {
   private ArrayList<Transicion> transiciones;
   private boolean esFinal;
   private String nombre;

    public String getNombre() {
        return nombre;
    }


    public Estado(String nombre) {
        this.nombre=nombre;
    }

    public ArrayList<Transicion> getTransiciones() {
        return transiciones;
    }

    public void setTransiciones(ArrayList<Transicion> transiciones) {
        this.transiciones = transiciones;
    }

    public boolean isEsFinal() {
        return esFinal;
    }

    public void setEsFinal(boolean esFinal) {
        this.esFinal = esFinal;
    }
   
   
   
}
