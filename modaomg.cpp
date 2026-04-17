#include <iostream>
#include <vector> 

using namespace std;

int main() {
 
    vector<int> numeros;
    int cantidad, numero;

    // Ingresar cantidad de números
    cout << "Cuántos números vas a ingresar? ";
    cin >> cantidad;

    // Ingresar los números
    cout << "Ingresa " << cantidad << " números:\n";
    for (int i = 0; i < cantidad; i++) {
        cin >> numero;
        numeros.push_back(numero); // Añade el número al final del vector
    }

    // Encontrar la moda
    int moda = numeros[0];
    int max_frecuencia = 0;

    // Recorrer cada número para ver cuántas veces se repite
    for (int i = 0; i < numeros.size(); i++) {
        int frecuencia_actual = 0;
        
        // Contar cuántas veces aparece este número
        for (int j = 0; j < numeros.size(); j++) {
            if (numeros[i] == numeros[j]) {
                frecuencia_actual++;
            }
        }
        
        // Actualizar moda si encuentro más repeticiones
        if (frecuencia_actual > max_frecuencia) {
            moda = numeros[i];
            max_frecuencia = frecuencia_actual;
        }
    }

    // Mostrar resultados
    cout << "La moda es: " << moda << endl;
    cout << "Se repite " << max_frecuencia << " veces" << endl;

    return 0;
}