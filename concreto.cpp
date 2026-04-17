#include <iostream>
using namespace std;


int sumaDeDigitos(int numero) {
    if (numero == 0) return 0; 
    return numero % 10 + sumaDeDigitos(numero / 10);  // Sumar el último dígito y continuar con el resto :)
}

int main() {
    int numero;
    
    // Solicitar al usuario que ingrese un número
    cout << "Ingresa un numero entero positivo: ";
    cin >> numero;

    // Mostrar la suma de los dígitos
    cout << "La suma de los dígitos es: " << sumaDeDigitos(numero) << endl;

    return 0;
}
