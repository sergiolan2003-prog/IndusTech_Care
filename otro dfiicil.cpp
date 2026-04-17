#include <iostream>
#include <string>
using namespace std;

struct Estudiante {
    string nombre;
    float notaMatematicas;
    float notaCiencias;
    float notaLiteratura;
};

float calcularPromedio(Estudiante e) {
    return (e.notaMatematicas + e.notaCiencias + e.notaLiteratura) / 3.0;
}

int main() {
    int numEstudiantes;
    cout << "Ingrese cantidad de estudiantes: ";
    cin >> numEstudiantes;
    cin.ignore();

    Estudiante* estudiantes = new Estudiante[numEstudiantes];
    
    // Ingresar datos
    for(int i = 0; i < numEstudiantes; i++) {
        cout << "\nEstudiante " << i + 1 << endl;
        cout << "Nombre: ";
        getline(cin, estudiantes[i].nombre);
        cout << "Nota matematicas: ";
        cin >> estudiantes[i].notaMatematicas;
        cout << "Nota ciencias: ";
        cin >> estudiantes[i].notaCiencias;
        cout << "Nota literatura: ";
        cin >> estudiantes[i].notaLiteratura;
        cin.ignore();
    }
    
    // Mostrar todos los estudiantes y sus notas
    cout << "\nLista de estudiantes:" << endl;
    for(int i = 0; i < numEstudiantes; i++) {
        cout << "\nNombre: " << estudiantes[i].nombre << endl;
        cout << "Matematicas: " << estudiantes[i].notaMatematicas << endl;
        cout << "Ciencias: " << estudiantes[i].notaCiencias << endl;
        cout << "Literatura: " << estudiantes[i].notaLiteratura << endl;
        cout << "Promedio: " << calcularPromedio(estudiantes[i]) << endl;
    }
    
    // Calcular promedio de la clase
    float sumaPromedios = 0;
    for(int i = 0; i < numEstudiantes; i++) {
        sumaPromedios += calcularPromedio(estudiantes[i]);
    }
    
    cout << "\nPromedio de toda la clase: " << sumaPromedios / numEstudiantes << endl;
    
    delete[] estudiantes;
    return 0;
// }