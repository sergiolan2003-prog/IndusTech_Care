#include <iostream>
using namespace std;

int main() {
    int filas, columnas;

    // Solicitar dimensiones de la matriz
    cout << "Ingrese el número de filas: ";
    cin >> filas;
    cout << "Ingrese el número de columnas: ";
    cin >> columnas;

    // Crear la matriz dinámica
    double** matriz = new double*[filas];
    for (int i = 0; i < filas; i++) {
        matriz[i] = new double[columnas];
    }

    // Llenar la matriz con datos ingresados por el usuario
    cout << "Ingrese los elementos de la matriz:" << endl;
    for (int i = 0; i < filas; i++) {
        for (int j = 0; j < columnas; j++) {
            cout << "Elemento [" << i << "][" << j << "]: ";
            cin >> matriz[i][j];
        }
    }

    // Calcular y mostrar el promedio de cada columna
    cout << "Promedio de cada columna:" << endl;
    for (int j = 0; j < columnas; j++) {
        double sumaColumna = 0;
        for (int i = 0; i < filas; i++) {
            sumaColumna += matriz[i][j];
        }
        double promedio = sumaColumna / filas;
        cout << "Columna " << j + 1 << ": " << promedio << endl;
    }

    // Liberar la memoria dinámica
    for (int i = 0; i < filas; i++) {
        delete[] matriz[i];
    }
    delete[] matriz;

    return 0;