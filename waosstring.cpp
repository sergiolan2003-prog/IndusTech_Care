#include <iostream>
#include <string>
using namespace std;


void invertirCadena(string& cadena, int i) {
    int n = cadena.length();
    if (i >= n / 2) 
    return;  

    // Intercambiar los caracteres
    swap(cadena[i], cadena[n - i - 1]);

   
    invertirCadena(cadena, i + 1);
}

int main() {
    string cadena = "hola"; //debe ser aloh que bendicion

    
    invertirCadena(cadena, 0);

    // Imprimir la cadena invertida
    cout << "Cadena invertida: " << cadena << endl;

    return 0;
}
