#include <iostream>
using namespace std;

int main() {
    int estudiantes, asignaturas;

    // Solicitar dimensiones de la matriz
    cout << "Ingrese el número de estudiantes: ";
    cin >> estudiantes;
    cout << "Ingrese el número de asignaturas: ";
    cin >> asignaturas;

    // Crear la matriz dinámica
    double** notas = new double*[estudiantes];
    for (int i = 0; i < estudiantes; i++) {
        notas[i] = new double[asignaturas];
    }

    // Llenar la matriz con las notas
    cout << "Ingrese las notas de cada estudiante:" << endl;
    for (int i = 0; i < estudiantes; i++) {
        cout << "Estudiante " << i + 1 << ":" << endl;
        for (int j = 0; j < asignaturas; j++) {
            cout << "Nota " << j + 1 << ": ";
            cin >> notas[i][j];
        }
    }

    // Calcular el promedio de cada estudiante y encontrar el más alto
    double maxPromedio = 0;
    int estudianteDestacado = 0;

    for (int i = 0; i < estudiantes; i++) {
        double suma = 0;
        for (int j = 0; j < asignaturas; j++) {
            suma += notas[i][j];
        }
        double promedio = suma / asignaturas;
        cout << "Promedio del estudiante " << i + 1 << ": " << promedio << endl;

        if (promedio > maxPromedio) {
            maxPromedio = promedio;
            estudianteDestacado = i;
        }
    }

    // Mostrar el estudiante con el promedio más alto
    cout << "El estudiante con el promedio más alto es el Estudiante " 
         << estudianteDestacado + 1 << " con un promedio de " << maxPromedio << endl;

    // Liberar la memoria dinámica
    for (int i = 0; i < estudiantes; i++) {
        delete[] notas[i];
    }
    delete[] notas;

    return 0;
}