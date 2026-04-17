#include <iostream>
#include <fstream>
#include <vector>

using namespace std;

const int TAMANO_SUDOKU = 9; // Definimos una constante para el tamaño del Sudoku (9x9)

// Función para verificar si una celda del Sudoku es válida
bool esValido(const vector<vector<int>>& tablero, int fila, int columna, int numero) {
    // Verificar fila: Recorremos todas las columnas de la fila actual
    for (int i = 0; i < TAMANO_SUDOKU; i++) {
        if (tablero[fila][i] == numero and i != columna) { // Si encontramos el número en la fila y no es la misma columna
            return false; // No es válido
        }
    }

    // Verificar columna: Recorremos todas las filas de la columna actual
    for (int i = 0; i < TAMANO_SUDOKU; i++) {
        if (tablero[i][columna] == numero and i != fila) { // lo mismo de arriba pero con columna
            return false; // No es válido
        }
    }

    // Verificar submatriz 
    int inicioFila = (fila / 3) * 3;// recorre las 3 filas y 3 columnas de la submatriz 3x3 
    int inicioColumna = (columna / 3) * 3;// recorre las 3 columnas y 3 columnas de la submatriz 3x3 
    for (int i = 0; i < 3; ++i) { // Recorremos las filas de la submatriz
        for (int j = 0; j < 3; ++j) { // Recorremos las columnas de la submatriz
            if (tablero[inicioFila + i][inicioColumna + j] == numero and
                (inicioFila + i != fila || inicioColumna + j != columna)) { // Si encontramos el número y no es la misma celda
                return false; // No es válido
            }
        }
    }

    return true; // Si pasa todas las verificaciones la celda es válida
}

void imprimirTablero(const vector<vector<int>>& tablero, ostream& salida) {
    for (int i = 0; i < TAMANO_SUDOKU; i++) { //filas 
        for (int j = 0; j < TAMANO_SUDOKU; j++) { //columnas
            salida << tablero[i][j] << " ";
        }
        salida << endl; 
    }
}

int main() {
    vector<vector<int>> tablero(TAMANO_SUDOKU, vector<int>(TAMANO_SUDOKU));

    ifstream entrada("sudoku_input.txt"); 
    if (!entrada.is_open()) { // Si no se puede abrir
        cerr << "No se pudo abrir el archivo sudoku_input.txt" << endl; // Mensaje de error
        return 1; // Terminamos el programa con código de error
    }

    for (int i = 0; i < TAMANO_SUDOKU; ++i) { // filitas
        for (int j = 0; j < TAMANO_SUDOKU; ++j) { //columnitas
            entrada >> tablero[i][j]; 
        }
    }
    entrada.close(); 

    bool valido = true;//mirar si es valido o no por eso un booooooool
    for (int i = 0; i < TAMANO_SUDOKU; ++i) { // Recorremos las filas
        for (int j = 0; j < TAMANO_SUDOKU; ++j) { // Recorremos las columnas
            if (tablero[i][j] != 0 && !esValido(tablero, i, j, tablero[i][j])) { // Si la celda no es vacía y no es válida
                valido = false;
                break;
            }
        }
        if (!valido) break; //si es invalido ya nos salimos
    }

    // Abrir el archivo de salida
    ofstream salida("sudoku_input2.txt"); 
    if (!salida.is_open()) {
        cerr << "No se pudo abrir el archivo sudoku_input2.txt" << endl; 
        return 1;
    }

    if (valido) {
        salida << "El tablero es valido:" << endl;
        imprimirTablero(tablero, salida); 
    } else { 
        salida << "El tablero es invalido." << endl;
    }
    salida.close(); 

    return 0; 
}
