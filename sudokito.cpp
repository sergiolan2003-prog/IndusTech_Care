#include <iostream>
#include <vector>
#include <sstream>
#include <fstream>
using namespace std;

// Función para imprimir el tablero
void imprimirTablero(vector<vector<int>>& tablero, int n = 3) {
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            if (tablero[i][j] == 1)
                cout << "1 "; // representa 1 cosa
            else
                cout << "0 "; // . representa una casilla vacia
        }
        cout << endl;
    }
    cout << endl;




      ifstream entrada ("miarchivo.txt");
    if (!entrada.is_open()){
        cout << "error no hay ningun archivo leido";

    }
    string linea;
    for (int i = 0; i < 9; i++){

        getline (entrada,linea);
        stringstream ss(linea);
        for (int j = 0; j < 9; j++){
            ss >> tablero[i][j];
        }
    }

}





//verifica que sea una posicion segura 
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
bool resolversudoku(vector<vector<int>>& tablero, int col, int n) {

    if (col >= n)
        return true;

  
    for (int i = 0; i < n; i++) {
      
        if (esPosicionSegura(tablero, i, col, n)) {
        
            tablero[i][col] = 1;

           
            if (resolversudoku(tablero, col + 1, n))
                return true;

            // Si no encontramos solución la quitamos
            tablero[i][col] = 0;
        }
    }

    return false;
}

int main() {
        ofstream salida ("final.txt");
        salida <<   '5' '3' '0' '0''7''0''0''0''0'
                    '6' '0' '0' '1''9''5''0''0''0'
                    '0' '9' '8' '0''0''0''0''6''0'
                    '8' '0' '0' '0''6''0''0''0''3'
                    '4' '0' '0' '8''0''3''0''0''1'
                    '7' '0' '0' '0''2''0''0''0''6'
                    '0' '6' '0' '0''0''0''2''8''0'
                    '0' '0' '0' '4''1''9''0''0''5'
                    '0' '0''0' '0''8''0''0''7' '9'
    

    
    int n;
    cout << "Ingrese el tamano del tablero (N): ";
    cin >> n;

    
    vector<vector<int>> tablero(n, vector<int>(n, 0));

    if (resolversudoku(tablero, 0, n)) {
        cout << "tablero valido:" << endl;
        imprimirTablero(tablero, n);
    } else {
        cout << "el tablero no es valido " << n << endl;
    }

    return 0;
}
// bueno el codigo  se que presenta errores pero por falta de tiempo no lo pude terminar :( usamos vectores para imprimir el tablero las posiciones seguras tambien usamos boolianos para usar banderazos llamamos las funciones en el int main  tambien usamos recursividad en resolver y que no sae repitan los numeros etc )
// para que veas que si funciona borra el ofstream :) auque solo pone 0 y 1 :( depresion)