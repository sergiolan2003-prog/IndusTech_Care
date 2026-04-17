#include <iostream>
using namespace std;

struct Estudiante {
    int edad;
    float promedio;
    bool asistencia;
};

int main() {
    int capacidad = 5;
    Estudiante* estudiantes = new Estudiante[capacidad];
    int tamano = 0;
    
    // Agregar estudiantes
    estudiantes[tamano].edad = 20;
    estudiantes[tamano].promedio = 8.5;
    estudiantes[tamano].asistencia = true;
    tamano++;
    
    estudiantes[tamano].edad = 22;
    estudiantes[tamano].promedio = 7.8;
    estudiantes[tamano].asistencia = false;
    tamano++;
    
    // Mostrar estudiantes
    for(int i = 0; i < tamano; i++) {
        cout << "Estudiante " << i << ":" << endl;
        cout << "  Edad: " << estudiantes[i].edad << endl;
        cout << "  Promedio: " << estudiantes[i].promedio << endl;
        cout << "  Asistencia: " << (estudiantes[i].asistencia ? "Si" : "No") << endl;
        cout << endl;
    }
    
    // Liberar memoria
    delete[] estudiantes;
    
    return 0;
}