#include <iostream>
#include <fstream>
#include <vector>

using namespace std;

// Función para validar filas
bool validarFila(const vector<vector<int>>& tablero, int fila) {
    bool numeros[10] = {false};  // Arreglo para marcar si un número ya ha sido encontrado (0-9)
    
    for (int i = 0; i < 9; i++) {
        int valor = tablero[fila][i];
        if (valor != 0) {  // Ignorar las celdas vacías
            if (numeros[valor]) {
                return false;  // Si el número ya ha sido marcado, es inválido
            }
            numeros[valor] = true;  // Marcar el número como encontrado
        }
    }
    return true;
}

// Función para validar columnas
bool validarColumna(const vector<vector<int>>& tablero, int columna) {
    bool numeros[10] = {false};  // Arreglo para marcar si un número ya ha sido encontrado (0-9)
    
    for (int i = 0; i < 9; i++) {
        int valor = tablero[i][columna];
        if (valor != 0) {  // Ignorar las celdas vacías
            if (numeros[valor]) {
                return false;  // Si el número ya ha sido marcado, es inválido
            }
            numeros[valor] = true;  // Marcar el número como encontrado
        }
    }
    return true;
}

// Función para validar submatrices 3x3
bool validarSubmatriz(const vector<vector<int>>& tablero, int fila, int columna) {
    bool numeros[10] = {false};  // Arreglo para marcar si un número ya ha sido encontrado (0-9)
    
    for (int i = 0; i < 3; i++) {
        for (int j = 0; j < 3; j++) {
            int valor = tablero[fila + i][columna + j];
            if (valor != 0) {  // Ignorar las celdas vacías
                if (numeros[valor]) {
                    return false;  // Si el número ya ha sido marcado, es inválido
                }
                numeros[valor] = true;  // Marcar el número como encontrado
            }
        }
    }
    return true;
}

int main() {
    ifstream archivo("sudoku_input.txt");
    if (!archivo) {
        cerr << "No se pudo abrir el archivo" << endl;
        return 1;
    }

    vector<vector<int>> tablero(9, vector<int>(9));

    // Leer el tablero desde el archivo
    for (int i = 0; i < 9; i++) {
        for (int j = 0; j < 9; j++) {
            archivo >> tablero[i][j];
        }
    }

    bool valido = true;

    // Validar filas
    for (int i = 0; i < 9; i++) {
        if (!validarFila(tablero, i)) {
            cout << "Fila " << i + 1 << " es invalida." << endl;
            valido = false;
        }
    }

    // Validar columnas
    for (int i = 0; i < 9; i++) {
        if (!validarColumna(tablero, i)) {
            cout << "Columna " << i + 1 << " es invalida." << endl;
            valido = false;
        }
    }

    // Validar submatrices de 3x3
    for (int i = 0; i < 9; i += 3) {
        for (int j = 0; j < 9; j += 3) {
            if (!validarSubmatriz(tablero, i, j)) {
                cout << "Submatriz de 3x3 en la posicion (" << i / 3 + 1 << ", " << j / 3 + 1 << ") es invalida." << endl;
                valido = false;
            }
        }
    }

    if (valido) {
        cout << "El tablero de Sudoku es valido." << endl;
    } else {
        cout << "El tablero de Sudoku es invalido." << endl;
    }

    return 0;
}
