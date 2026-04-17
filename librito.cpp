#include <iostream>
#include <string>
using namespace std;

struct Libro {
    string titulo;
    int anioPublicacion;
};

int main() {
    // Declarar una instancia de la estructura Libro
    Libro libro;

    // Asignar valores a los miembros de la estructura
    libro.titulo = "Cien Anos de Soledad";
    libro.anioPublicacion = 1967;

    // Mostrar los valores asignados
    cout << "Titulo: " << libro.titulo << endl;
    cout << "Ano de Publicacion: " << libro.anioPublicacion << endl;

    return 0;
}