/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package practica2;

/**
 *
 * @author teres
 */
public class Transicion {
    private Estado estadoOrigen;
    private Estado estadoDestino;
    private String entrada;

    public Transicion(Estado estadoOrigen, Estado estadoDestino, String entrada) {
        this.estadoOrigen = estadoOrigen;
        this.estadoDestino = estadoDestino;
        this.entrada = entrada;
    }


    public Estado getEstadoDestino() {
        return estadoDestino;
    }



    public String getEntrada() {
        return entrada;
    }


    
    
    
    
}
