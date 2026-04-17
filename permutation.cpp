#include <iostream>
#include <vector>
#include <algorithm>  // Para usar next_permutation
using namespace std;

int main() {
    // Definir el arreglo de entrada
    vector<int> arreglo = {3, 1, 2, 6, 7};
    
    // Ordenar el arreglo de forma ascendente antes de generar las permutaciones
    sort(arreglo.begin(), arreglo.end());

    // Generar todas las permutaciones posibles
    cout << "Las permutaciones posibles son: " << endl;
    do {
        // Mostrar la permutación actual
        for (int i = 0; i < arreglo.size(); ++i) {
            cout << arreglo[i] << " ";
        }
        cout << endl;
    } while (next_permutation(arreglo.begin(), arreglo.end()));  // Generar la siguiente permutación
    
    return 0;
}