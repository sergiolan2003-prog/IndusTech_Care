#include <iostream>
using namespace std;

struct Fecha {
    int dia;
    int mes;
    int anio;
};

// Procedimiento para mostrar la fecha en formato "dd/mm/aaaa"
void mostrarFecha(const Fecha& fecha) {
    cout << (fecha.dia < 10 ? "0" : "") << fecha.dia << "/"
         << (fecha.mes < 10 ? "0" : "") << fecha.mes << "/"
         << fecha.anio << endl;
}

int main() {
    // Crear una instancia de la estructura Fecha
    Fecha fecha = {5, 12, 2024};

    // Mostrar la fecha utilizando el procedimiento
    mostrarFecha(fecha);

    return 0;
}