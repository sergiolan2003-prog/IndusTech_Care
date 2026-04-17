#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

bool esCuadradoMagico(vector<int> cuadrado) {
    int objetivo = cuadrado[0] + cuadrado[1] + cuadrado[2];
                                                                                    
    // Comprueba filas
    for (int i = 0; i < 9; i += 3) 
        if (cuadrado[i] + cuadrado[i+1] + cuadrado[i+2] != objetivo) 
            return false;
    
    // Comprueba columnas
    for (int i = 0; i < 3; i++)     
        if (cuadrado[i] + cuadrado[i+3] + cuadrado[i+6] != objetivo)
            return false;
    
    // diagonal
    if (cuadrado[0] + cuadrado[4] + cuadrado[8] != objetivo)
        return false;
    
    // diagonal segunda
    if (cuadrado[2] + cuadrado[4] + cuadrado[6] != objetivo)
        return false;
    
    return true;
}

int main() {
    vector<int> numeros = {1, 2, 3, 4, 5, 6, 7, 8, 9};
    do {
        if (esCuadradoMagico(numeros)) {
            for (int i = 0; i < 9; i += 3)
                cout << numeros[i] << " " << numeros[i+1] << " " << numeros[i+2] << "\n";
            cout << "\n";
        }
    } while (next_permutation(numeros.begin(), numeros.end()));
    return 0;
}



//prueba de escritorio
//[0][1][2]
//[3][4][5]     //matriz 3x3
//[6][7][8]       