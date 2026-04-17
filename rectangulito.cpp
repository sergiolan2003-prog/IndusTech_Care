#include <iostream>

using namespace std;

// Definir la estructura Rectangulo
struct Rectangulo {
    float ancho;
    float alto;
};

// Función para ingresar los valores de ancho y alto para un rectángulo
void ingresarRectangulo(Rectangulo& rect) {
    cout << "Ingrese el ancho del rectangulo: ";
    cin >> rect.ancho;
    cout << "Ingrese el alto del rectangulo: ";
    cin >> rect.alto;
}

// Función para mostrar los valores de un rectángulo
void mostrarRectangulo(const Rectangulo& rect) {
    cout << "Ancho: " << rect.ancho << ", Alto: " << rect.alto << endl;
}

int main() {
    int n;

    // Pedir al usuario cuántos rectángulos quiere crear
    cout << "Ingrese el numero de rectangulos: ";
    cin >> n;

    // Crear un arreglo dinámico de rectángulos
    Rectangulo* rectangulos = new Rectangulo[n];

    // Ingresar los datos de cada rectángulo
    for (int i = 0; i < n; ++i) {
        cout << "Rectangulo " << i + 1 << ":" << endl;
        ingresarRectangulo(rectangulos[i]);
    }

    // Mostrar los datos de cada rectángulo
    cout << "\nDatos de los rectangulos ingresados:\n";
    for (int i = 0; i < n; ++i) {
        cout << "Rectangulo " << i + 1 << ": ";
        mostrarRectangulo(rectangulos[i]);
    }

    // Liberar la memoria del arreglo dinámico
    delete[] rectangulos;

    return 0;
}
