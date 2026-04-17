#include <iostream>
#include <fstream>
#include <vector>

using namespace std;

const int TAMANO_SUDOKU = 9;

// Función para validar una fila
bool validarFila(const vector<vector<int>>& tablero, int fila, vector<int>& errores) {
    vector<bool> numeros(TAMANO_SUDOKU + 1, false);
    for (int j = 0; j < TAMANO_SUDOKU; ++j) {
        int num = tablero[fila][j];
        if (num != 0) {
            if (numeros[num]) {
                errores.push_back(fila);
                return false;
            }
            numeros[num] = true;
        }
    }
    return true;
}

// Función para validar una columna
bool validarColumna(const vector<vector<int>>& tablero, int columna, vector<int>& errores) {
    vector<bool> numeros(TAMANO_SUDOKU + 1, false);
    for (int i = 0; i < TAMANO_SUDOKU; ++i) {
        int num = tablero[i][columna];
        if (num != 0) {
            if (numeros[num]) {
                errores.push_back(columna);
                return false;
            }
            numeros[num] = true;
        }
    }
    return true;
}

// Función para validar una submatriz
bool validarSubmatriz(const vector<vector<int>>& tablero, int inicioFila, int inicioColumna, vector<pair<int, int>>& errores) {
    vector<bool> numeros(TAMANO_SUDOKU + 1, false);
    for (int i = 0; i < 3; ++i) {
        for (int j = 0; j < 3; ++j) {
            int num = tablero[inicioFila + i][inicioColumna + j];
            if (num != 0) {
                if (numeros[num]) {
                    errores.emplace_back(inicioFila, inicioColumna);
                    return false;
                }
                numeros[num] = true;
            }
        }
    }
    return true;
}

// Función para imprimir el tablero en un formato legible
void imprimirTablero(const vector<vector<int>>& tablero, ostream& salida) {
    for (int i = 0; i < TAMANO_SUDOKU; i++) {
        for (int j = 0; j < TAMANO_SUDOKU; j++) {
            salida << tablero[i][j] << " ";
        }
        salida << endl;
    }
}

int main() {
    vector<vector<int>> tablero(TAMANO_SUDOKU, vector<int>(TAMANO_SUDOKU));

    // Abrir el archivo de entrada
    ifstream entrada("sudoku_input.txt");
    if (!entrada.is_open()) {
        cerr << "No se pudo abrir el archivo sudoku_input.txt" << endl;
        return 1;
    }

    // Leer el tablero desde el archivo de entrada
    for (int i = 0; i < TAMANO_SUDOKU; ++i) {
        for (int j = 0; j < TAMANO_SUDOKU; ++j) {
            entrada >> tablero[i][j];
        }
    }
    entrada.close();

    // Validar filas, columnas y submatrices
    vector<int> filasConError;
    vector<int> columnasConError;
    vector<pair<int, int>> submatricesConError;

    bool valido = true;

    // Validar filas
    for (int i = 0; i < TAMANO_SUDOKU; ++i) {
        if (!validarFila(tablero, i, filasConError)) {
            valido = false;
        }
    }

    // Validar columnas
    for (int j = 0; j < TAMANO_SUDOKU; ++j) {
        if (!validarColumna(tablero, j, columnasConError)) {
            valido = false;
        }
    }

    // Validar submatrices
    for (int i = 0; i < TAMANO_SUDOKU; i += 3) {
        for (int j = 0; j < TAMANO_SUDOKU; j += 3) {
            if (!validarSubmatriz(tablero, i, j, submatricesConError)) {
                valido = false;
            }
        }
    }

    // Abrir el archivo de salida
    ofstream salida("sudoku_output.txt");
    if (!salida.is_open()) {
        cerr << "No se pudo abrir el archivo sudoku_output.txt" << endl;
        return 1;
    }

    // Escribir el tablero original
    salida << "Tablero de entrada:" << endl;
    imprimirTablero(tablero, salida);

    // Escribir la validación
    if (valido) {
        salida << "\nEl tablero es v\xE1lido." << endl;
    } else {
        salida << "\nEl tablero es inv\xE1lido." << endl;

        if (!filasConError.empty()) {
            salida << "Filas con errores: ";
            for (int fila : filasConError) {
                salida << fila + 1 << " ";
            }
            salida << endl;
        }

        if (!columnasConError.empty()) {
            salida << "Columnas con errores: ";
            for (int columna : columnasConError) {
                salida << columna + 1 << " ";
            }
            salida << endl;
        }

        if (!submatricesConError.empty()) {
            salida << "Submatrices con errores (coordenadas de la esquina superior izquierda): ";
            for (const auto& [fila, columna] : submatricesConError) {
                salida << "(" << fila + 1 << ", " << columna + 1 << ") ";
            }
            salida << endl;
        }
    }

    // Justificación técnica
    salida << "\nJustificaci\xF3n t\xE9cnica:" << endl;
    salida << "El programa utiliza vectores de booleanos para verificar la unicidad de los n\xFAmeros en filas, columnas y submatrices, 
"
           << "lo que asegura una validaci\xF3n eficiente y clara para cada secci\xF3n del tablero." << endl;

    salida.close();

    return 0;
}
