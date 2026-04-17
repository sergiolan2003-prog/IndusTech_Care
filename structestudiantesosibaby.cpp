#include <iostream>
#include <string>
using namespace std;

struct Estudiante {
    string nombre;
    int edad;
    float promedio;
};

// Función para actualizar el promedio
void actualizarPromedio(Estudiante& estudiante, float nuevoPromedio) {
    estudiante.promedio = nuevoPromedio;
}

int main() {
    Estudiante estudiante = {"Juan", 20, 8.5};

    cout << "Promedio actual: " << estudiante.promedio << endl;

    actualizarPromedio(estudiante, 9.3);

    cout << "Promedio actualizado: " << estudiante.promedio << endl;

    return 0;
}