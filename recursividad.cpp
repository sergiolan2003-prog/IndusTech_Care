#include <iostream>     // Incluye la biblioteca para entrada/salida
using namespace std;    // Permite usar cout y cin sin escribir std::

// Función que suma números de 1 hasta n usando recursividad
int sumaRecursiva(int n) {      // Recibe un número n y retorna un entero
    if (n == 1) {               // Si n es 1 (caso base)
        return 1;               // Devuelve 1 y termina la recursión
    }
    return n + sumaRecursiva(n - 1);  // Si no, suma n + la suma de números anteriores
}

int main() {                    // Función principal del programa
    int numero;                 // Declara variable para guardar el número
    
    cout << "Ingrese un numero: ";    // Pide al usuario que ingrese un número
    cin >> numero;                    // Lee el número y lo guarda en 'numero'
    
    // Muestra el resultado de la suma llamando a sumaRecursiva
    cout << "La suma de 1 hasta " << numero << " es: " << sumaRecursiva(numero) << endl;
    
    // Muestra el proceso de la suma
    cout << "Proceso: ";              // Título para mostrar el proceso
    for(int i = 1; i <= numero; i++) {    // Bucle desde 1 hasta numero
        cout << i;                        // Muestra el número actual
        if(i < numero) {                  // Si no es el último número
            cout << " + ";                // Muestra un signo +
        }
    }
    cout << endl;                    // Salto de línea al final
    
    return 0;                        // Termina el programa
}