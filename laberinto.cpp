#include <iostream>
#include <vector>
using namespace std;

const int FILAS = 5;
const int COLUMNAS = 5;

// imprimir el laberinto
vector<vector<char>> laberinto = {
    {'#', '#', '#', '#', '#'},
    {'#', ' ', ' ', ' ', '#'},
    {'#', ' ', '#', ' ', '#'},
    {'#', ' ', '#', 'E', '#'},
    {'#', '#', '#', '#', '#'}
};


int jugadorX = 1;
int jugadorY = 1;

// mostrar el laberinto pues en panbtalla
void mostrarLaberinto() {
    for (int i = 0; i < FILAS; i++) {
        for (int j = 0; j < COLUMNAS; j++) {
            if (i == jugadorX && j == jugadorY) {
                cout << 'P' << " "; // P representa al jugador
            } else {
                cout << laberinto[i][j] << " ";
            }
        }
        cout << endl;
    }
}

// Función para mover al jugador
bool moverJugador(char direccion) {
    int nuevoX = jugadorX;
    int nuevoY = jugadorY;

    switch (direccion) {
        case 'w': // Arriba
            nuevoX--;
            break;
        case 's': // Abajo
            nuevoX++;
            break;
        case 'a': // Izquierda
            nuevoY--;
            break;
        case 'd': // Derecha
            nuevoY++;
            break;
        default:
            cout << "Dirección inválida." << endl;
            return false;
    }

   
  
        //si yo choco con una pared mostrara el mensaaje de chocar
    if (laberinto[nuevoX][nuevoY] == '#') {
        cout << "Movimiento invalido. Chocaste con una pared." << endl;
        return false;
    }

  // que bendicion  si es valido se actualiza la posicion NO OLVIDAR!!!!!!!!!!!!!!!!!!!!!!!!
    jugadorX = nuevoX;
    jugadorY = nuevoY;
    return true;
}

// Función para comprobar si el jugador ha llegado a la salida
bool haGanado() {
    return (jugadorX == 3 && jugadorY == 3); // La salida está en la posición (3, 3)
}

int main() {
    cout << "Bienvenido al laberinto  mas dificil que usted va a jugar en toda su vida ya sabe " << endl;
    cout << "vea para ganar usted simplemente debe ir y llegar hasta donde dice E si usted llega hasta haya dejeme decirle que usted es el mejor" << endl;
    cout << "para moverse necesita  lo siguiente: un teclado y manos y ya solo presionar W para arriba D para la izquierda A para la derecha y S para abajo sino me entendio llame a juan simon para que el le pueda explicar bendiciones" << endl;


    //si el jugador llega al final la condicion se cumple y es verdadera entonces mandara el mesnaje 
    while (true) {
        mostrarLaberinto();
        if (haGanado()) {
            cout << "lo felicito logro vencer el laberinto mas dificil que a jugado en toda su vida :D" << endl;
            break;
        }

        cout << "le recuerdo como moverse en caso de que le dio pereza leer  (w/a/s/d): ";
        char movimiento;
        cin >> movimiento;

        if (!moverJugador(movimiento)) {
            continue;
        }
    }

    return 0;
}
