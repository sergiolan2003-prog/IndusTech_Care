#include <iostream>
#include <string>

using namespace std;

void extraerMinusculas(const string& cadena, string& minusculas) {
    for (char letra : cadena) {
        if (letra >= 'a' && letra <= 'z') {
            minusculas += letra;
        }
    }
}

int main() {
    string cadena = "Hola Mundo 123!";
    string minusculas;

    extraerMinusculas(cadena, minusculas);

    cout << "Minúsculas: " << minusculas << endl;

    return 0;
}