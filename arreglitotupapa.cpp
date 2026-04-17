#include <iostream>
#include <vector>
using namespace std;

int main() {
    vector<int> vec = {10, 20, 30, 40, 50};  
    int posicion;

    // Bucle para eliminar elementos
    while (true) {
       
        cout << "Elementos actuales: ";
        for (int i = 0; i < vec.size(); i++) {
            cout << vec[i] << " ";
        }
        cout << endl;

        
        cout << "Introduce la posición a eliminar: ";
        cin >> posicion;

        // Eliminar el elemento si la posición es válida
        if (posicion >= 0 && posicion < vec.size()) {
            vec.erase(vec.begin() + posicion);  // Elimina el elemento en la posición indicada
            cout << "Elemento eliminado.\n";
        } else {
            cout << "Posición inválida.\n";
        }

        // Preguntar si desea continuar
        char continuar;
        cout << "¿Eliminar otro? (s/n): ";
        cin >> continuar;

        if (continuar == 'n' || continuar == 'N') break;  // Salir si el usuario elige no continuar
    }

    return 0;
}
