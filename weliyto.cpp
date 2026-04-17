#include <iostream>
#include <string>
using namespace std;

// Estructura para representar un vuelo
struct Vuelo {
    string origen;
    string destino;
    int asientosDisponibles;
};

int main() {
    // Vuelos predefinidos
    Vuelo vuelos[3] = {
        {"España", "china", 50},
        {"Colombia", "Haiti", 30},
        {"Paris", "marte (no hay regreso)", 20}
    };

    int opcion, vueloSeleccionado;

    while (true) {
        cout << "\n1. Mostrar vuelos disponibles\n";
        cout << "2. Reservar asiento\n";
        cout << "3. Salir\n";
        cout << "Elija una opcion: ";
        cin >> opcion;

        if (opcion == 1) {
            // Mostrar vuelos disponibles
            for (int i = 0; i < 3; i++) {
                cout << i + 1 << ". " << vuelos[i].origen << " - " << vuelos[i].destino
                     << " | Asientos disponibles: " << vuelos[i].asientosDisponibles << endl;
            }
        }
        else if (opcion == 2) {
            // Reservar asiento
            cout << "Seleccione el vuelo (número): ";
            cin >> vueloSeleccionado;
            vueloSeleccionado--;  // empezar dedsde 0 asd



           // este if verifica asientos disponible que bendicion viajar a paris 
            if (vuelos[vueloSeleccionado].asientosDisponibles > 0) {
                vuelos[vueloSeleccionado].asientosDisponibles--; 
                cout << "Reserva exitosa en el vuelo: " 
                     << vuelos[vueloSeleccionado].origen << " -> " 
                     << vuelos[vueloSeleccionado].destino << endl;
            } else {
                cout << "No hay asientos disponibles.\n";
            }
        }
        else if (opcion == 3) {
            break; // Salir
        } else {
            cout << "Opción no válida.\n";
        }
    }

    return 0;
}
