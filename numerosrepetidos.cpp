#include <iostream>
using namespace std;


int main () {
    int numero;
    int arreglito [5];
    int contador = 0;
    int repetidos = 0;

        cout << "ingrese el tamaño del arreglo";
        cin >> numero;
        


            for (int i = 0; i< numero; i++){
                cout << i + 1 << "ingrese los numeros que llevara el arreglo"<<endl;
                cin >> arreglito [i];

            }
                    for (int i = 0; i<numero; i++) {
                        for (int j = 0; i<numero; i++){

                            if (arreglito [j] == arreglito [i]) {

                                contador ++;
                            }
                        }
                         if (contador > repetidos) {
                            repetidos = contador;
                            contador = arreglito [i];
                        }
                    
                    }
                       cout << "el numero que menos se repite es: " << repetidos << endl;
}