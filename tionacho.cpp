#include <iostream>
using namespace std;

// Función recursiva para calcular el n-ésimo número de Fibonacci
int fibonacci(int n) {
    // Caso base: los primeros dos números de Fibonacci son 0 y 1
    if (n <= 1) {
        return n;
    } else {
        // Llamada recursiva: fibonacci(n-1) + fibonacci(n-2)
        return fibonacci(n - 1) + fibonacci(n - 2);
    }
}

int main() {
    int n;

    // Pedir al usuario el valor de n
    cout << "introduce un numero para calcular el tionacho (fibonacci)";
    cin >> n;

    // Mostrar el resultado
    cout << "El " << n << " número de Fibonacci es: " << fibonacci(n) << endl;

    return 0;
}
