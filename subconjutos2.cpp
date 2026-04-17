#include <iostream>
#include <vector>

using namespace std;

int main() {
    int objetivo, tamano;

   
    cout << "Ingrese el valor objetivo: ";
    cin >> objetivo;

    cout << "Ingrese el tamano del conjunto: ";
    cin >> tamano;

    
    vector<int> conjunto(tamano);
    cout << "Ingrese los elementos del conjunto:\n";
    for (int i = 0; i < tamano; ++i) {
        cin >> conjunto[i];
    }

    int maxSuma = 0;
    vector<int> mejorSubconjunto;

    
    for (int i = 1; i < 1 << tamano; ++i) {
        vector<int> subconjunto;
        int suma = 0;

    
        for (int j = 0; j < tamano; ++j) {
            if ((i >> j) % 2 == 1) { 
                subconjunto.push_back(conjunto[j]);
                suma += conjunto[j];
            }
        }

        // no puede superar al objetivo
        if (suma <= objetivo) {
    // eta suma verifica :)
        if (suma > maxSuma) {
            // si la suma es mayor se actualiza (NO OLVIDAR)
        maxSuma = suma;

        mejorSubconjunto = subconjunto;
    }
}

    }

   if (maxSuma > 0) {
    cout << "Subconjunto con la mayor suma posible: ";
    // ahora solo imprimir el subconjuntos si señor
    for (int i = 0; i < mejorSubconjunto.size(); ++i) {
        cout << mejorSubconjunto[i] << " ";  
    }
  

     cout << "No se encontro ningun subconjunto que no supere el objetivo." << endl;
   }


        cout << "La suma total es: " << maxSuma << endl;
         

        
         

    return 0;


    }
      

