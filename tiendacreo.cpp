#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

int main() {
    vector<int> tiempotrabajo = {3, 1, 2, 5};
    vector<int> recargospordia = {4, 1000, 2, 5};

    int diasacumulados = 0;
    int costototal = 0;

    // Trabajo 1
    cout << "Trabajo 1:\n";
    cout << "- Toma 3 dias\n";
    diasacumulados = 3;
    costototal += 3 * 4;
    cout << "Dias acumulados: " << diasacumulados << "\n";
    cout << "Costo parcial: " << costototal << "\n\n";

    // Trabajo 2
    cout << "Trabajo 2:\n";
    cout << "- Toma 1 dia\n";
    diasacumulados += 1;
    cout << "Dias acumulados: " << diasacumulados << "\n";
    costototal += 4000;
    cout << "Costo parcial: " << costototal << "\n\n";

    // Trabajo 3
    cout << "Trabajo 3:\n";
    cout << "- Toma 2 dias\n";
    diasacumulados += 2;
    cout << "Dias acumulados: " << diasacumulados << "\n";
    costototal += 12;
    cout << "Costo parcial: " << costototal << "\n\n";

    // Trabajo 4
    cout << "Trabajo 4:\n";
    cout << "- Toma 5 dias\n";
    diasacumulados += 5;
    costototal += 55;
    cout << "Dias acumulados: " << diasacumulados << "\n";
    cout << "Costo parcial: " << costototal << "\n\n";

    cout << "El costo total es: " << costototal << " pesos\n\n";


    cout << "permutaciones :\n";
    do {
        diasacumulados = 0;
        costototal = 0;
        for (size_t i = 0; i < tiempotrabajo.size(); i++) {
            diasacumulados += tiempotrabajo[i];
            costototal += tiempotrabajo[i] * recargospordia[i];
            cout << "Trabajo " << i+1 << ":\n";
            cout << "- Toma " << tiempotrabajo[i] << " dias\n";
            cout << "Dias acumulados: " << diasacumulados << "\n";
            cout << "Costo parcial: " << costototal << "\n\n";
        }
        cout << "El costo total es: " <<costototal << " pesos\n\n";
    } while (next_permutation(tiempotrabajo.begin(), tiempotrabajo.end()));

    return 0;
}