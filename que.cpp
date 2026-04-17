#include <iostream>  
#include <string>    

using namespace std;  

int main() {  
    string cadena = "Hola Mundo Programacion Hola Hola Mundo"; 
    string palabra; 
    int conteo;

    // Recorremos la cadena hasta que se vacíe
    while (!cadena.empty()) {  // Mientras la cadena no esté vacía.
        
        size_t pos = cadena.find(' ');  // Buscar el primer espacio en la cadena.
        
        if (pos == string::npos) {  // Si no se encontró un espacio, es la última palabra.
            palabra = cadena;  // Asignar toda la cadena a 'palabra'.
            cadena.clear();    // Vaciar la cadena original.
        } else {
            palabra = cadena.substr(0, pos);  // Extraer la palabra hasta el primer espacio.
            cadena = cadena.substr(pos + 1);  // Eliminar la palabra extraída de la cadena original.
        }

        conteo = 0;  // Inicializar el contador de repeticiones a 0.
        size_t inicio = 0;  // Variable para controlar la posición de búsqueda de la palabra.

        // Buscar repeticiones de la palabra
        while ((inicio = cadena.find(palabra, inicio)) != string::npos) {  // Buscar repeticiones de la palabra.
            conteo++;  // Incrementar el contador de repeticiones.
            inicio += palabra.length();  // Avanzar la posición de búsqueda para evitar contar la misma ocurrencia.
        }

        cout << palabra << ": " << conteo << " veces" << endl;  // Imprimir la palabra y su conteo.
    }

    return 0;  // Fin de la función principal.
}
