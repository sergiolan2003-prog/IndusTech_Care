#include <iostream>
#include <string>

using namespace std;

const int N = 5;  // Tamano de la matriz (5x5)

// Funcion para buscar el patron en la matriz
bool buscarPatron(char matriz[N][N], string patron) {
    int longitud = patron.length();

    // Buscar horizontalmente (de izquierda a derecha y de derecha a izquierda)
    for (int i = 0; i < N; ++i) {
        for (int j = 0; j <= N - longitud; ++j) {
            bool encontrado = true;
            for (int k = 0; k < longitud; ++k) {
                if (matriz[i][j + k] != patron[k]) {
                    encontrado = false;
                    break;
                }
            }
            if (encontrado) {
                cout << "Patron encontrado comenzando en la posicion (" << i << ", " << j << ").\n";
                return true;
            }

            // Buscar de derecha a izquierda
            encontrado = true;
            for (int k = 0; k < longitud; ++k) {
                if (matriz[i][j + longitud - 1 - k] != patron[k]) {
                    encontrado = false;
                    break;
                }
            }
            if (encontrado) {
                cout << "Patron encontrado comenzando en la posicion (" << i << ", " << j + longitud - 1 << ").\n";
                return true;
            }
        }
    }

    // Buscar verticalmente (de arriba hacia abajo y de abajo hacia arriba)
    for (int j = 0; j < N; ++j) {
        for (int i = 0; i <= N - longitud; ++i) {
            bool encontrado = true;
            for (int k = 0; k < longitud; ++k) {
                if (matriz[i + k][j] != patron[k]) {
                    encontrado = false;
                    break;
                }
            }
            if (encontrado) {
                cout << "Patron encontrado comenzando en la posicion (" << i << ", " << j << ").\n";
                return true;
            }

            // Buscar de abajo hacia arriba
            encontrado = true;
            for (int k = 0; k < longitud; ++k) {
                if (matriz[i + longitud - 1 - k][j] != patron[k]) {
                    encontrado = false;
                    break;
                }
            }
            if (encontrado) {
                cout << "Patron encontrado comenzando en la posicion (" << i + longitud - 1 << ", " << j << ").\n";
                return true;
            }
        }
    }

    // Buscar diagonalmente (de arriba a la izquierda a abajo a la derecha)
    for (int i = 0; i <= N - longitud; ++i) {
        for (int j = 0; j <= N - longitud; ++j) {
            bool encontrado = true;
            for (int k = 0; k < longitud; ++k) {
                if (matriz[i + k][j + k] != patron[k]) {
                    encontrado = false;
                    break;
                }
            }
            if (encontrado) {
                cout << "Patron encontrado comenzando en la posicion (" << i << ", " << j << ").\n";
                return true;
            }
        }
    }

    // Si no se encontro en ninguna direccion
    cout << "Patron no encontrado.\n";
    return false;
}

int main() {
    // Definicion de la matriz de ejemplo 5x5
    char matriz[N][N] = {
        {'A', 'B', 'C', 'D', 'E'},
        {'F', 'G', 'H', 'I', 'J'},
        {'K', 'L', 'M', 'N', 'O'},
        {'P', 'Q', 'R', 'S', 'T'},
        {'U', 'V', 'W', 'X', 'Y'}
    };

    // Solicitar el patron a buscar
    string patron;
    cout << "Introduce el patron a buscar: ";
    cin >> patron;

    // Llamar a la funcion para buscar el patron
    buscarPatron(matriz, patron);

    return 0;
}
