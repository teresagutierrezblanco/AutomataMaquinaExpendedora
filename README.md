### About
Implementación en Java de un procesador de automatas finitos que pueden ser definidos mediante un archivo de definición. Dicho archivo tendrá el siguiente formato:  
```
#6 A B C D E F
#2 C F
#2 0 1
--TABLA DE TRANSICIONES--
B C # D # #
B # D E F # C F #
A # # #
 # B # C F #
A # # C D #
 # # #
```
En la primera linea se definirá el número de estados del autómata y sus "nombres"
En la segunda, se definirán los estados finales del autómata.
En la tercera, se definirán las entradas posibles.
Tras estas tres primeras filas, se definirá una tabla de transiciones en la que la fila n representará el n-ésimo estado, y la columna n, la n-ésima entrada posible. De esta manera, en cada posición de la matriz se almacenarán los estados siguientes para la combinación estado actual-entrada, separados por el símbolo #. Si el espacio se encuentra vacío, no existe transición para dicha combinación.  
Se puede ver en el ejemplo que existe una columna adicional. Esta columna representa el estado siguiente para la cadena vacía, por lo que este procesador de automatas finitos admite automatas finitos deterministas y no deterministas.  
  
Por lo tanto, el procesador tomará el archivo de definición y creará un procesador para el autómata representado. El usuario podrá introducir por teclado entradas válidas para ver a qué estado avanza el autómata, procesando las cadenas para comprobar si son válidas de acuerdo al autómata.
