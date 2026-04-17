#include <iostream>
#include <vector>
using namespace std;

int main() {
    vector<int> vec;  // Creamos un vector vacío de enteros
    int opcion, valor, posicion;

    while (true) {
        //el menu
        cout << "\nMenu de opciones:\n";
        cout << "1. Agregar al final\n";
        cout << "2. Eliminar el último\n";
        cout << "3. Insertar en posición\n";
        cout << "4. Eliminar en posición\n";
        cout << "5. Imprimir\n";
        cout << "6. Salir\n";
        cout << "Elija una opción: ";
        cin >> opcion;

        if (opcion == 1) {  // Agregar al final
            cout << "Introduce el valor a agregar: ";
            cin >> valor;
            vec.push_back(valor);  // Añadir al final
        } else if (opcion == 2) {  // Eliminar el último
            if (!vec.empty()) {
                vec.pop_back();  // Eliminar el último
            } else {
                cout << "El vector está vacío.\n";
            }
        } else if (opcion == 3) {  // Insertar en una posición
            cout << "Introduce la posición: ";
            cin >> posicion;
            if (posicion >= 0 && posicion <= vec.size()) {
                cout << "Introduce el valor a insertar: ";
                cin >> valor;
                vec.insert(vec.begin() + posicion, valor);  // Insertar en la posición
            } else {
                cout << "Posición inválida.\n";
            }
        } else if (opcion == 4) {  // Eliminar en una posición
            cout << "Introduce la posición a eliminar: ";
            cin >> posicion;
            if (posicion >= 0 && posicion < vec.size()) {
                vec.erase(vec.begin() + posicion);  // Eliminar en la posición
            } else {
                cout << "Posición inválida.\n";
            }
        } else if (opcion == 5) {  // Imprimir el vector
            cout << "Elementos del vector: ";
            for (int i = 0; i < vec.size(); i++) {
                cout << vec[i] << " ";
            }
            cout << endl;
        } else if (opcion == 6) {  // Salir
            break;
        } else {
            cout << "Opción no válida.\n";
        }
    }

    return 0;
}
