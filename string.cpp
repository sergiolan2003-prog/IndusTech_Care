#include <iostream>
#include <string>
using namespace std;

int main () {

    string cadena =  "el perro come el perro duerme";
    string subcadena = "perro";
    string nuevasubcadena = "gato";

       string a = cadena.replace(3,5, "gato");
        
    cout << a << endl;

    return 0;
}