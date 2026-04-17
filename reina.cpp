#include <iostream>
#include <vector>
using namespace std;

// Función para imprimir el tablero
void imprimirTablero(vector<vector<int>>& tablero, int n) {
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            if (tablero[i][j] == 1)
                cout << "R "; // R representa una reina
            else
                cout << ". "; // . representa una casilla vacia
        }
        cout << endl;
    }
    cout << endl;
}


bool esPosicionSegura(vector<vector<int>>& tablero, int fila, int col, int n) {
    
    for (int j = 0; j < col; j++) {
        if (tablero[fila][j] == 1)
            return false;
    }

    // Revisar diagonal superior izquierda
    for (int i = fila, j = col; i >= 0 && j >= 0; i--, j--) {
        if (tablero[i][j] == 1)
            return false;
    }

    // Revisar diagonal inferior izquierda
    for (int i = fila, j = col; i < n && j >= 0; i++, j--) {
        if (tablero[i][j] == 1)
            return false;
    }

    return true;
}

// Función recursiva para resolver el problema
bool resolverNReinas(vector<vector<int>>& tablero, int col, int n) {

    if (col >= n)
        return true;

  
    for (int i = 0; i < n; i++) {
      
        if (esPosicionSegura(tablero, i, col, n)) {
        
            tablero[i][col] = 1;

           
            if (resolverNReinas(tablero, col + 1, n))
                return true;

            // Si no encontramos solución, quitamos la reina (backtracking)
            tablero[i][col] = 0;
        }
    }

    return false;
}

int main() {





    
    int n;
    cout << "Ingrese el tamano del tablero (N): ";
    cin >> n;

    
    vector<vector<int>> tablero(n, vector<int>(n, 0));

    if (resolverNReinas(tablero, 0, n)) {
        cout << "Solucion encontrada:" << endl;
        imprimirTablero(tablero, n);
    } else {
        cout << "No existe solución para N = " << n << endl;
    }

    return 0;
}

