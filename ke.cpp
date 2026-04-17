#include <iostream>
using namespace std;

struct Punto {
    float x;
    float y;
};

// Función para calcular la distancia euclidiana entre dos puntos
float distanciaEntrePuntos(const Punto& p1, const Punto& p2) {
    return (p2.x - p1.x) * (p2.x - p1.x) + (p2.y - p1.y) * (p2.y - p1.y);
}

int main() {
    Punto p1 = {3.0, 4.0};
    Punto p2 = {0.0, 0.0};

    cout << "Distancia entre puntos: " << distanciaEntrePuntos(p1, p2) << endl;

    return 0;
}
 //este codigo no supe hacerlo asi que le pedi a chat gpt lo admito :(