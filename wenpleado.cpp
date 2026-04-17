#include <iostream>
#include <string>

using namespace std;

struct Empleado {
    string nombre;
    float salario;
    string departamento;
};

int main() {
    int n;

    // Pedir al usuario el numero de empleados a registrar
    cout << "Ingrese el numero de empleados: ";
    cin >> n;

    // Crear un arreglo dinamico de empleados
    Empleado* empleados = new Empleado[n];

    float sumaSalarios = 0;

    // Ingresar los datos de los empleados y calcular el total de salarios
    for (int i = 0; i < n; ++i) {
        cout << "\nEmpleado " << i + 1 << ":\n";
        cout << "Ingrese el nombre: ";
        cin.ignore();  // Limpiar el buffer
        getline(cin, empleados[i].nombre);
        cout << "Ingrese el salario: ";
        cin >> empleados[i].salario;
        cout << "Ingrese el departamento: ";
        cin.ignore();  // Limpiar el buffer
        getline(cin, empleados[i].departamento);

        sumaSalarios += empleados[i].salario;
    }

    // Calcular el promedio de salarios
    float promedioSalarios = sumaSalarios / n;

    // Mostrar el promedio
    cout << "\nEl promedio de salarios es: " << promedioSalarios << endl;

    // Liberar la memoria del arreglo dinamico
    delete[] empleados;

    return 0;
}
