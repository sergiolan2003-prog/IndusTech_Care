#include <iostream>
using namespace std;

// Función recursiva para sumar los elementos de un arreglo
int sumaArreglo(int arreglo[], int tamano) {
    // Caso base: Si el tamaño del arreglo es 0, la suma es 0
    if (tamano == 0) {
        return 0;
    } else {
        // Sumar el primer elemento y llamar recursivamente con el resto del arreglo
        return arreglo[0] + sumaArreglo(arreglo + 1, tamano - 1);
    }
}

int main() {
    int n;

    // Pedir al usuario el tamaño del arreglo
    cout << "Introduce el tamaño del arreglo: ";
    cin >> n;

    int arreglo[n];
    
    // Pedir al usuario los elementos del arreglo
    cout << "Introduce los elementos del arreglo:\n";
    for (int i = 0; i < n; ++i) {
        cin >> arreglo[i];
    }

    // Llamar a la función recursiva para obtener la suma
    int resultado = sumaArreglo(arreglo, n);

    // Mostrar el resultado
    cout << "La suma de los elementos del arreglo es: " << resultado << endl;

    return 0;
}
