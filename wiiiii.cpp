#include <iostream> 
#include <vector>    
using namespace std;  

void encontrarMaxMin(const vector<int>& vec, int index, int& maximo, int& minimo) {
    if (index == vec.size()) 


    return;

   //si el valor de el vector es pues mayor como se ve se actualiza lo mismo con el menor de abajo
    if (vec[index] > maximo) 
        maximo = vec[index];


    if (vec[index] < minimo) 
        minimo = vec[index];

    // Llamada recursiva para procesar el siguiente elemento en el vector
    encontrarMaxMin(vec, index + 1, maximo, minimo);
}

int main() {
  
    vector<int> vec = {3, 5, 7, 2, 8, -1, 4};

    
    int maximo = vec[0], minimo = vec[0];

   
    encontrarMaxMin(vec, 0, maximo, minimo);

    
    cout << "Máximo: " << maximo << endl;

    cout << "Mínimo: " << minimo << endl;

    return 0;  
}
