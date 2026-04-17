#include <iostream>
#include <string>

using namespace std;

int main() {
    // Definir las dos cadenas que vamos a concatenar
    string palabra1 = "Hola";
    string palabra2 = "Mundo";

    // Concatenar las dos palabras
    string concatenada = palabra1 + palabra2;

    // Inicializar los contadores de vocales
    int a = 0, e = 0, i = 0, o = 0, u = 0;

    // Recorrer la cadena concatenada y contar las vocales
    for (char c : concatenada) {
        c = tolower(c);  // Convertir a minúscula para contar sin importar mayúsculas
        if (c == 'a') a++;
        else if (c == 'e') e++;
        else if (c == 'i') i++;
        else if (c == 'o') o++;
        else if (c == 'u') u++;
    }

    // Mostrar los resultados
    cout << "Vocales en la cadena \"" << concatenada << "\":" << endl;
    cout << "a: " << a << endl;
    cout << "e: " << e << endl;
    cout << "i: " << i << endl;
    cout << "o: " << o << endl;
    cout << "u: " << u << endl;

    return 0;
}
