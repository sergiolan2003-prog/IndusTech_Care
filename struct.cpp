#include <iostream>
using namespace std;

struct Punto {
    float x;
    float y;
};

int main() {
    int capacidad = 5;
    Punto* puntos = new Punto[capacidad];
    int tamano = 0;
    
    // Agregar puntos
    puntos[tamano].x = 1.5;
    puntos[tamano].y = 2.3;
    tamano++;
    
    puntos[tamano].x = 3.7;
    puntos[tamano].y = 4.2;
    tamano++;
    
    // Mostrar puntos
    for(int i = 0; i < tamano; i++) {
        cout << "Punto " << i << ": (" << puntos[i].x << ", " << puntos[i].y << ")" << endl;
    }
    
    // Liberar memoria
    delete[] puntos;
    
    return 0;
}