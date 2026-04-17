

//----------------------------------------------------------//
//genere un  pray y post 
//entradas y salidas
//comentar y cual algoritmo y tecnica utilizamos
//resultado final 
//--------------------------------------------------------//
#include <fstream>
#include <iostream>

using namespace std;


int main () {
    ofstream colombisgay ("archivo.txt");
    colombisgay << "hola soy gay";
    colombisgay.close ();
    return 0;
}