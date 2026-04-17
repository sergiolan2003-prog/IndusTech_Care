#include <iostream>

using namespace std;

const int MAX_SIZE = 100;  // Tamaño máximo del arreglo

int main() {
    int n;
    int arr[MAX_SIZE];

    // Leer el tamaño del arreglo
    cout << "Introduce el número de elementos en el arreglo (máx " << MAX_SIZE << "): ";
    cin >> n;

    // Verificar que el tamaño sea válido
    if (n <= 0 || n > MAX_SIZE) {
        cout << "Tamaño del arreglo no válido.\n";
        return 1;
    }

    // Leer los elementos del arreglo
    cout << "Introduce los elementos del arreglo:\n";
    for (int i = 0; i < n; ++i) {
        cin >> arr[i];
    }

    // Encontrar el número más repetido
    int maxCount = n + 1;
    int mostFrequent = arr[0];

    for (int i = 0; i < n; ++i) {
        int count = 0;
        for (int j = 0; j < n; ++j) {
            if (arr[j] == arr[i]) {
                count++;
            }
        }
        if (count < maxCount) {
            maxCount = count;
            mostFrequent = arr[i];
        }
    }

    // Imprimir el número más repetido
    cout << "El número más repetido es " << mostFrequent << " con " << maxCount << " ocurrencias.\n";

    return 0;
}