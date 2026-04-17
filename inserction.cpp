#include <iostream>
#include <string>
#include <vector>
using namespace std;

// Definición de la estructura Estudiante
struct Estudiante {
    string nombre;
    float promedio;
};

// Función para ordenar estudiantes por promedio usando Insertion Sort (orden descendente)
void insertionSort(vector<Estudiante>& estudiantes) {
    int n = estudiantes.size();
    
    for(int i = 1; i < n; i++) {
        Estudiante actual = estudiantes[i];
        int j = i - 1;
        
        // Mover elementos que son menores que el promedio actual
        // a una posición adelante de su posición actual
        while(j >= 0 && estudiantes[j].promedio < actual.promedio) {
            estudiantes[j + 1] = estudiantes[j];
            j--;
        }
        estudiantes[j + 1] = actual;
    }
}

// Función para imprimir la lista de estudiantes
void imprimirEstudiantes(const vector<Estudiante>& estudiantes) {
    cout << "\n=== Lista de Estudiantes ===\n";
    cout << "Nombre\t\tPromedio\n";
    cout << "-----------------------------------\n";
    
    for(const Estudiante& est : estudiantes) {
        // Ajustamos la precisión del promedio a 2 decimales manualmente
        int parteEntera = est.promedio;
        int parteDecimal = (est.promedio - parteEntera) * 100;
        
        cout << est.nombre;
        // Agregamos tabuladores según la longitud del nombre
        int tabs = (est.nombre.length() < 8) ? 2 : 1;
        for(int i = 0; i < tabs; i++) {
            cout << "\t";
        }
        cout << parteEntera << "." 
             << (parteDecimal < 10 ? "0" : "") // Asegura que se muestren dos decimales
             << parteDecimal << endl;
    }
    cout << endl;
}

// Función para validar el promedio
bool validarPromedio(float promedio) {
    return promedio >= 0 && promedio <= 10;
}

int main() {
    vector<Estudiante> estudiantes;
    char continuar;
    
    cout << "Programa de Ordenamiento de Estudiantes por Promedio\n";
    cout << "=================================================\n\n";
    
    do {
        Estudiante estudiante;
        string nombre;
        float promedio;
        
        // Ingreso de datos con validación
        cout << "Ingrese el nombre del estudiante: ";
        cin.ignore(); // Limpiar el buffer
        getline(cin, nombre);
        estudiante.nombre = nombre;
        
        do {
            cout << "Ingrese el promedio (0-10): ";
            cin >> promedio;
            
            if(!validarPromedio(promedio)) {
                cout << "Error: El promedio debe estar entre 0 y 10.\n";
            }
        } while(!validarPromedio(promedio));
        
        estudiante.promedio = promedio;
        
        // Agregar estudiante al vector
        estudiantes.push_back(estudiante);
        
        cout << "\n¿Desea ingresar otro estudiante? (s/n): ";
        cin >> continuar;
        cout << endl;
        
    } while(continuar == 's' || continuar == 'S');
    
    // Mostrar lista original
    cout << "\nLista Original:";
    imprimirEstudiantes(estudiantes);
    
    // Ordenar estudiantes
    insertionSort(estudiantes);
    
    // Mostrar lista ordenada
    cout << "Lista Ordenada por Promedio (Mayor a Menor):";
    imprimirEstudiantes(estudiantes);
    
    return 0;
}