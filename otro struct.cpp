#include <iostream>
#include <string>
using namespace std;

struct Vuelo {
    string origen;
    string destino;
    string fecha;
    int asientosDisponibles;
    int numeroVuelo;
};

void mostrarVuelos(Vuelo* vuelos, int tamano) {
    cout << "\nVuelos disponibles:" << endl;
    for(int i = 0; i < tamano; i++) {
        cout << "\nVuelo #" << vuelos[i].numeroVuelo << endl;
        cout << "Origen: " << vuelos[i].origen << endl;
        cout << "Destino: " << vuelos[i].destino << endl;
        cout << "Fecha: " << vuelos[i].fecha << endl;
        cout << "Asientos disponibles: " << vuelos[i].asientosDisponibles << endl;
        cout << "------------------------" << endl;
    }
}

void agregarVuelo(Vuelo* vuelos, int& tamano) {
    cout << "\nIngrese datos del nuevo vuelo:" << endl;
    
    cout << "Número de vuelo: ";
    cin >> vuelos[tamano].numeroVuelo;
    
    cout << "Origen: ";
    cin >> vuelos[tamano].origen;
    
    cout << "Destino: ";
    cin >> vuelos[tamano].destino;
    
    cout << "Fecha: ";
    cin >> vuelos[tamano].fecha;
    
    cout << "Número de asientos disponibles: ";
    cin >> vuelos[tamano].asientosDisponibles;
    
    tamano++;
}

bool reservarAsiento(Vuelo* vuelos, int tamano, int numeroVuelo) {
    for(int i = 0; i < tamano; i++) {
        if(vuelos[i].numeroVuelo == numeroVuelo) {
            if(vuelos[i].asientosDisponibles > 0) {
                vuelos[i].asientosDisponibles--;
                cout << "Reserva realizada con éxito!" << endl;
                return true;
            } else {
                cout << "Lo siento, no hay asientos disponibles." << endl;
                return false;
            }
        }
    }
    cout << "Vuelo no encontrado." << endl;
    return false;
}

bool cancelarReserva(Vuelo* vuelos, int tamano, int numeroVuelo) {
    for(int i = 0; i < tamano; i++) {
        if(vuelos[i].numeroVuelo == numeroVuelo) {
            vuelos[i].asientosDisponibles++;
            cout << "Reserva cancelada con éxito!" << endl;
            return true;
        }
    }
    cout << "Vuelo no encontrado." << endl;
    return false;
}

int main() {
    int capacidad = 10;
    Vuelo* vuelos = new Vuelo[capacidad];
    int tamano = 0;
    
    char opcion;
    do {
        cout << "\nMenú de Gestión de Vuelos:" << endl;
        cout << "1. Agregar vuelo" << endl;
        cout << "2. Mostrar vuelos disponibles" << endl;
        cout << "3. Reservar asiento" << endl;
        cout << "4. Cancelar reserva" << endl;
        cout << "5. Salir" << endl;
        cout << "Seleccione una opción: ";
        cin >> opcion;
        
        int numVuelo;
        
        switch(opcion) {
            case '1':
                agregarVuelo(vuelos, tamano);
                break;
                
            case '2':
                mostrarVuelos(vuelos, tamano);
                break;
                
            case '3':
                cout << "Ingrese número de vuelo para reservar: ";
                cin >> numVuelo;
                reservarAsiento(vuelos, tamano, numVuelo);
                break;
                
            case '4':
                cout << "Ingrese número de vuelo para cancelar reserva: ";
                cin >> numVuelo;
                cancelarReserva(vuelos, tamano, numVuelo);
                break;
        }
    } while(opcion != '5');
    
    delete[] vuelos;
    return 0;
  }  