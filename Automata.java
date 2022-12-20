/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package practica2;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;



/**
 * @author teres
 */
public class Automata {
    private ArrayList<Estado> estados = new ArrayList<Estado>();
    private ArrayList<String> entradas = new ArrayList<String>();
    private ArrayList<Estado> estadosActivos = new ArrayList<Estado>();


    public Automata() {
        //llamada a la función que lee la definicion del automata
        lector();
        String entrada;

        Scanner scan = new Scanner(System.in);
        //imprimimos estado inicial + a los que se accede con cadena vacia desde el inicial
        System.out.print("Estados Activos: ");
        for (Estado e : estadosActivos) {
            System.out.print(e.getNombre() + " ");
        }
        System.out.print("\n");
        while (true) {
            //leemos la entrada
            System.out.println("Introduce entrada:");
            entrada = scan.next();
            //analizamos uno a uno los elementos de la entrada
            for (int i = 0; i < entrada.length(); i++) {
                //comprobamos que esté en el alfabeto
                if (entradas.contains(String.valueOf(entrada.charAt(i)))) {
                    System.out.print("Entrada: " + String.valueOf(entrada.charAt(i)) + "\t");
                    ArrayList<Estado> estadosSiguientes = new ArrayList<Estado>();
                    //miramos los estados activos
                    for (Estado e : estadosActivos) {
                        //miramos a partir de cada estado activo a cual avanza con dicha entrada y lo almacenamos
                        for (Transicion t : e.getTransiciones()) {
                            if (t.getEntrada().equals(String.valueOf(entrada.charAt(i)))) {
                                estadosSiguientes.add(t.getEstadoDestino());
                            }

                        }

                    }
                    //borramos los estados pasados
                    estadosActivos.clear();
                    //añadimos los nuevos en estados activos
                    for (Estado e : estadosSiguientes) {
                        if (!estadosActivos.contains(e))
                            estadosActivos.add(e);
                    }
                    //comprobamos transiciones de cadena vacía
                    //esto hay que hacerlo hasta que no encontremos más
                    //ya que si a un estado se accede por cadena vacía y de este
                    //se puede acceder a otro por cadena vacía, hay que añadir este otro
                    do {
                        estadosSiguientes.clear();

                        for (Estado e : estadosActivos) {
                            for (Transicion t : e.getTransiciones()) {
                                if (t.getEntrada().equals("")) {
                                    if (!estadosActivos.contains(t.getEstadoDestino()))
                                        estadosSiguientes.add(t.getEstadoDestino());
                                }

                            }

                        }
                        //añadimos los estados a los que se accede por cadena vacía
                        for (Estado e : estadosSiguientes) {
                            estadosActivos.add(e);
                        }
                    } while (!estadosSiguientes.isEmpty());
                    System.out.print("Estados Activos: ");
                    for (Estado e : estadosActivos) {
                        System.out.print(e.getNombre() + " ");
                    }
                    System.out.print("\n");
                }else{
                    System.out.println("Entrada no valida");
                }
            }
        }
    }



    //funcion que lee el fichero de definición del automata
    public void lector() {
        String orden;
        BufferedReader bufferLector;
        Integer linea = 0;
        FileReader lector = null;
        int numeroEntradas = 0;
        String nombre;
        Scanner scan = new Scanner(System.in);
        System.out.println("Introduce el nombre del fichero");
        nombre = scan.next();
        File fichero;
        if( !(fichero = new File(nombre)).exists()){
            System.out.println("No existe el fichero");
            System.exit(0);
        };
        try {
            lector = new FileReader(fichero);
            bufferLector = new BufferedReader(lector);
            while ((orden = bufferLector.readLine()) != null) {

                String[] partes = orden.split(" "); //separamos lo que leemos del fichero
                switch (linea) {
                    //primera linea, estados
                    case 0:
                        //quitamos el # y nos quedamos con el numero de estados
                        partes[0] = partes[0].replace("#", "");
                        int numeroEstados = Integer.parseInt(String.valueOf(partes[0]));
                        //leemos ese número de estados
                        for (int i = 1; i <= numeroEstados; i++) {
                            //lo almacenamos en la clase Estado, que almacena su nombre, sus transiciones y si es final
                            Estado estado = new Estado(partes[i]);
                            //lo añadimos al arraylist de estados
                            estados.add(estado);
                            //si es el primero que leemos es el inicial y por lo tanto está activo
                            if (i == 1) {
                                estadosActivos.add(estado);
                            }
                        }
                        break;
                        //segunda linea, estados finales
                    case 1:
                        //leemos el número de estados finales
                        partes[0] = partes[0].replace("#", "");
                        int numeroEstadosFinales = Integer.parseInt(String.valueOf(partes[0]));
                        for (int i = 1; i <= numeroEstadosFinales; i++) {
                            for (Estado e : estados) {
                                //buscamos en el arraylist de estados y los que sean los añadimos
                                if (e.getNombre().equals(partes[i])) {
                                    e.setEsFinal(true);
                                }
                            }
                        }
                        break;
                        //tercera linea, entradas
                    case 2:
                        //leemos el numero de entradas
                        partes[0] = partes[0].replace("#", "");
                        numeroEntradas = Integer.parseInt(String.valueOf(partes[0]));
                        //las guardamos
                        for (int i = 1; i <= numeroEntradas; i++) {
                            entradas.add(partes[i]);
                        }
                        break;
                    case 3://linea separadora, la ignoramos
                        break;
                        //las siguientes lineas son las que definen el comportamiento del automata
                    default:
                        //separamos por columnas
                        String[] partes1 = orden.split("#");
                        ArrayList<Transicion> transiciones = new ArrayList<Transicion>();
                        for (int i = 0; i < partes1.length; i++) {
                            //separamos cada columna
                            partes = partes1[i].split(" ");
                            for (int j = 0; j < partes.length; j++) {
                                String entrada;
                                //si estamos dentro de las columnas correspondientes al alfabeto, cogemos la que toca
                                if (i < numeroEntradas) {
                                    entrada = entradas.get(i);
                                } else {
                                    //cadena vacía
                                    entrada = "";
                                }
                                //buscamos el estado indicado
                                for (Estado e : estados) {
                                    if (e.getNombre().equals(partes[j])) {
                                        //por cada linea de transiciones, 1 estado, por lo que el estado origen es el
                                        //que toca por linea
                                        Transicion transicion = new Transicion(estados.get(linea - 4), e, entrada);
                                        transiciones.add(transicion);
                                        //si es una transicion de cadena vacia y estamos en el estado inicial,
                                        //el estado al que se accede por cadena vacía tambien es "inicial"
                                        if(entrada.equals("") && estadosActivos.contains(estados.get(linea-4))){
                                            estadosActivos.add(e);
                                        }
                                    }
                                }
                            }
                        }
                        //almacenamos las transiciones en el estado correspondiente
                        estados.get(linea - 4).setTransiciones(transiciones);
                }
                linea++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
