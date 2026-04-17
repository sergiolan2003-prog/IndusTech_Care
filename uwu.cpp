#include <iostream>
using namespace std;

const int N = 3;  // Tamaño de la matriz 3x3

// Función recursiva para sumar los elementos de la diagonal principal
int sumaDiagonal(int matriz[N][N], int i) {
    if (i == N)  // si i es = a n entonces ya recorrimos toda la diagpnal
        return 0;
    return matriz[i][i] + sumaDiagonal(matriz, i + 1);  //summa el siguiente elemento ojo con eso
}

int main() {

    int matriz[N][N] = {
        {1, 2, 3},
        {4, 5, 6},
        {7, 8, 9}
    };

    //llama desde 0 la fucniopn
    int resultado = sumaDiagonal(matriz, 0);

    
    cout << "La suma de la diagonal principal es: " << resultado << endl;

    return 0;
}
