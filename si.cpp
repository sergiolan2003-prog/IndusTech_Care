#include <iostream>
#include <vector>

using namespace std;

int main() {
    int objetivo, tamano;

    // Solicitar al usuario el valor objetivo y el tamaño del conjunto
    cout << "Ingrese el valor objetivo" << endl;
    cin >> objetivo;

    cout << "Ingrese el tamano del conjunt" << endl;
    cin >> tamano;

    // Crear el conjunto de números
    vector<int> conjunto(tamano);
    cout << "Ingrese los elementos del conjunto:";
    for (int i = 0; i < tamano; ++i) {
        cin >> conjunto[i];
    }

    int maxSuma = 0;
    vector<int> mejorSubconjunto;

    // Generar todos los subconjuntos posibles
    // Vamos a iterar a través de todos los subconjuntos posibles utilizando un contador de 0 a 2^tamano - 1
    for (int i = 1; i < (1 << tamano); ++i) {
        vector<int> subconjunto;
        int suma = 0;

    
        for (int j = 0; j < tamano; ++j) {
            // Si 'i' tiene un valor en la posición j, agregamos el elemento correspondiente al subconjunto
            
            if ((i / (1 << j)) % 2 == 1) {
                subconjunto.push_back(conjunto[j]);
                suma += conjunto[j];
            }
        }

        //verificamos
        if (suma <= objetivo && suma > maxSuma) {
            maxSuma = suma;
            mejorSubconjunto = subconjunto;
        }
    }

    // Mostrar el resultado
    if (maxSuma > 0) {
        cout << "Subconjunto con la mayor suma posible sin superar el objetivo: ";
        for (int num : mejorSubconjunto) {
            cout << num << " ";
        }
        cout << "\nLa suma total es: " << maxSuma << endl;
    } else {
        cout << "No se encontro ningun resultado " << endl;
    }

    return 0;
}
