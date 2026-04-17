#include <iostream>
#include <vector>
#include <conio.h>
#include <stdlib.h>
using namespace std;
int main () {

	vector  <int> producto;
	int  numero, pares = 0,elemento;
	int tamaC1oarreglo;

	cout << "ingrese el tamaC1o del arreglo";
	cin >> tamaC1oarreglo;

	for (int i = 0; i<tamaC1oarreglo; i++) {
		cout << "ingrese los valores del arreglo";
		cin >> elemento;
		producto.push_back (elemento);

	}

        vector <int>::iterator it;
        for (it = producto.begin (); it < producto.end ();  it ++){
            
            if (*it % 2 == 0){
                pares += *it;

            }
        }
                    cout << "la sumatoria de los pares es:" << pares;
                
        return 0;


}
              
