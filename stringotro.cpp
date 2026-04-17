#include <iostream>
using namespace std;

void imprimirMatriz(int** matriz, int filas, int columnas) {
    for (int i = 0; i < filas; i++) {
        for (int j = 0; j < columnas; j++) {
            cout << matriz[i][j] << " ";
        }
        cout << endl;
    }
}

void ordenarMatriz(int** matriz, int filas, int columnas) {
    // Crear un arreglo para almacenar todos los elementos
    int totalElementos = filas * columnas;
    int* elementos = new int[totalElementos];

    // Recopilar todos los elementos en un arreglo
    int contador = 0;
    for (int i = 0; i < filas; i++) {
        for (int j = 0; j < columnas; j++) {
            elementos[contador++] = matriz[i][j];
        }
    }

    // Ordenar el arreglo usando el método burbuja
    for (int i = 0; i < totalElementos - 1; i++) {
        for (int j = 0; j < totalElementos - i - 1; j++) {
            if (elementos[j] > elementos[j + 1]) {
                // Intercambiar
                int temp = elementos[j];
                elementos[j] = elementos[j + 1];
                elementos[j + 1] = temp;
            }
        }
    }

    // Llenar la matriz original con los elementos ordenados
    contador = 0;  // Reiniciar el contador
    for (int i = 0; i < filas; i++) {
        for (int j = 0; j < columnas; j++) {
            matriz[i][j] = elementos[contador++];
        }
    }

    // Liberar la memoria del arreglo de elementos
    delete[] elementos;
}

int main() {
    int filas, columnas;

    cout << "Ingrese el número de filas: ";
    cin >> filas;
    cout << "Ingrese el número de columnas: ";
    cin >> columnas;

    // Crear matriz dinámica
    int** matriz = new int*[filas];
    for (int i = 0; i < filas; i++) {
        matriz[i] = new int[columnas];
    }

    cout << "Ingrese los elementos de la matriz:" << endl;
    for (int i = 0; i < filas; i++) {
        for (int j = 0; j < columnas; j++) {
            cin >> matriz[i][j];
        }
    }

    cout << "Matriz ingresada:" << endl;
    imprimirMatriz(matriz, filas, columnas);

    // Ordenar la matriz
    ordenarMatriz(matriz, filas, columnas);

    // Imprimir matriz ordenada
    cout << "Matriz ordenada de menor a mayor:" << endl;
    imprimirMatriz(matriz, filas, columnas);

    // Liberar memoria
    for (int i = 0; i < filas; i++) delete[] matriz[i];
    delete[] matriz;

    return 0;
}
