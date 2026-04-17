#include <iostream>
#include <vector>
#include <fstream>
using namespace std;

// Función de búsqueda binaria
int busquedaBinaria(const vector<int>& vec, int valor) {
    int inicio = 0, fin = vec.size() - 1;

    while (inicio <= fin) {
        int medio = (inicio + fin) / 2;  // Encontrar el punto medio
        if (vec[medio] == valor) return medio;  // Si se encuentra, devolver la posición
        if (vec[medio] < valor) inicio = medio + 1;  // Si es menor, buscar en la mitad derecha
        else fin = medio - 1;  // Si es mayor, buscar en la mitad izquierda
    }
    return -1;  // Si no se encuentra el valor
}

int main() {
    vector<int> vec;
    ifstream archivo("vector_ordenado.txt");  // Abrir el archivo
    int numero;

    // Leer los números desde el archivo
    while (archivo >> numero) vec.push_back(numero);

    cout << "Introduce el valor a buscar: ";
    int valor;
    cin >> valor;

    // Buscar el valor en el vector
    int resultado = busquedaBinaria(vec, valor);

    // Mostrar el resultado
    if (resultado != -1)
        cout << "Elemento encontrado en la posición: " << resultado << endl;
    else
        cout << "Elemento no encontrado." << endl;

    return 0;
}
