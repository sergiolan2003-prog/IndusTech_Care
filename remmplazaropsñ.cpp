#include <iostream>
#include <string>

using namespace std;

int main() {
    // Definir la cadena principal
    string cadena = "El lenguaje de programacion es C++";
    
    // Subcadena a reemplazar y la nueva subcadena
    string subcadena_a_reemplazar = "C++";
    string nueva_subcadena = "Python";

    // Reemplazar la subcadena en la cadena principal
    size_t pos = cadena.find(subcadena_a_reemplazar);  // Buscar la posición de la subcadena

    if (pos != string::npos) {  // Si se encuentra la subcadena
        cadena.replace(pos, subcadena_a_reemplazar.length(), nueva_subcadena);  // Reemplazar la subcadena
    }

    // Mostrar el resultado
    cout << "Cadena despuess del reemplazo: " << cadena << endl;

    return 0;
}
