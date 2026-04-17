#include <iostream>
using namespace std;

int main() {
    int filas, columnas;

    // Solicitar dimensiones de la matriz
    cout << "Ingrese el numero de filas: ";
    cin >> filas;
    cout << "Ingrese el numero de columnas: ";
    cin >> columnas;

    // Crear la matriz dinámica
    int** matriz = new int*[filas];
    for (int i = 0; i < filas; i++) {
        matriz[i] = new int[columnas];
    }

    // Llenar la matriz con datos ingresados por el usuario
    cout << "Ingrese los elementos de la matriz:" << endl;
    for (int i = 0; i < filas; i++) {
        for (int j = 0; j < columnas; j++) {
            cout << "Elemento [" << i << "][" << j << "]: ";
            cin >> matriz[i][j];
        }
    }

    // Calcular y mostrar la suma de cada fila
    cout << "Suma de cada fila:" << endl;
    for (int i = 0; i < filas; i++) {
        int sumaFila = 0;
        for (int j = 0; j < columnas; j++) {
            sumaFila += matriz[i][j];
        }
        cout << "Fila " << i + 1 << ": " << sumaFila << endl;
    }

    // Liberar la memoria dinámica
    for (int i = 0; i < filas; i++) {
        delete[] matriz[i];
    }
    delete[] matriz;

    return 0;
}