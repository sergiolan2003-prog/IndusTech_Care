#include <iostream>
using namespace std;

// Función recursiva para calcular la potencia de un número
int potencia(int base, int exponente) {
    // Caso base: cualquier número elevado a la potencia 0 es 1
    if (exponente == 0) {
        return 1;
    }
    // Caso recursivo: base * potencia(base, exponente - 1)
    return base * potencia(base, exponente - 1);
}

int main() {
    int base, exponente;
    
    // Pedir al usuario que ingrese la base y el exponente
    cout << "Introduce la base: ";
    cin >> base;
    cout << "Introduce el exponente: ";
    cin >> exponente;

    // Llamar a la función recursiva y mostrar el resultado
    int resultado = potencia(base, exponente);
    cout << "El resultado de " << base << " elevado a " << exponente << " es: " << resultado << endl;
    
    return 0;
}
