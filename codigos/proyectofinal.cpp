/******************************************************************************

                              Online C++ Compiler.
               Code, Compile, Run and Debug C++ program online.
Write your code in this editor and press "Run" button to compile and execute it.

*******************************************************************************/

#include <iostream>

using namespace std;

void mostrarMenu(){
    cout << "1) Agregar libro " << endl;
    cout << "2) Mostrar libros " << endl;
    cout << "3) Realizar prestamos y Devoluciones " << endl;
    cout << "4) Registrar libro" << endl;
    cout << "5) registrar usuarios" << endl;
    cout << "6) gestionar multas"<< endl;
}

// void RegistroUsuarios(string usuario[][25], int &cantidadUsuarios)
// {
//     if (cantidadUsuarios >= 25)
//     {
//         cout << "No se permiten más de 25 usuarios registrados." << endl;
//     }
//     else
//     {
//         cout << "Ingrese el nombre del ususario (evite los espacios): " << endl;
//         cin >> usuario[0][cantidadUsuarios];

//         cout << "Ingrese la dirección del ususario (evite los espacios): " << endl;
//         cin >> usuario[1][cantidadUsuarios];

//         cout << "Ingrese el número de identificación del ususario (evite los espacios): " << endl;
//         cin >> usuario[2][cantidadUsuarios];

//         cantidadUsuarios++;
//     }
// }
void agregarlibros(string libros[][3], int &numLibros, string generos[]){
    int opcion;
    do{
        for(int i = 0; i < 5; i ++){
            cout << i+1 << ") " << generos[i] << endl;
        }
        cout << "Seleccione un genero: ";
        cin >> opcion;
    } while (opcion < 1 || opcion > 5);
    cout << "Ingrese titulo: ";
    cin >> libros[numLibros][0];
    cout << "Ingrese autor: ";
    cin >> libros[numLibros][1];
    libros[numLibros][2] = generos[opcion-1];
    numLibros++;
}

void mostrarLibros(string libros[][3], int numLibros){
    cout << "Titulo\t\tAutor\t\tGenero" << endl;
    cout << "---------------------------------" << endl;
    for(int i = 0; i < numLibros; i++){
        for (int j = 0; j < 3; j++){
            cout << libros[i][j] << "\t";
        }
        cout << endl;
    }
}

// void prestamos(int cantidadusuarios, int numerodelibros, int libro)
// {
//     string libros[3][40];
//     int buscador;
//     int buscadorx;
//     int usuario[3][25];
//     int cantidadusuarios = 25;
//     int cantidadlibros = 40;
//     cout << "ingrese el usuario";
//     cin >> buscador;
//     for (int i = 0; i < cantidadusuarios; i++)
//     {
//         if (buscador == usuario[0][1])
//         {
//             cout << "igrese el libro a prestar:";
//             cin >> buscador;

//             for (int j = 0; j < cantidadlibros; j++)
//             {
//                 if (buscadorx == libros[0][j]){
//                     numerodelibros[j]--;
//                     usuario [3][i] = libro [0][j];
//                 }
//             }
//         }
//     }
// }
// void devolver(int dias, int cantidadusuarios, int librodelusuario, int libros, int cantidadlibros, int usuario, int opcion, int buscador)
// {
//     for (int i = 0; i < cantidadusuarios; i++)
//     {
//         if (buscador == usuario[0][1])
//         {
//             cout << "se mdemoro mas de lo esperado?";
//         }
//     }
// }
int main()
{
    string generos[5] = {"Novela", "Comedia", "Accion", "Terror", "Suspenso"};
    string libros[40][3];
    int numLibros = 0;

    string usuarios[25][3];
    int numUsuarios = 0;

    string prestamos[200][4];
    int numPrestamos = 0;

    int opcion;
    do
    {
        mostrarMenu();
        cout << "ingrese una opcion" << endl;
        cin >> opcion;
        switch (opcion)
        {
            case 1:
                agregarlibros(libros, numLibros, generos);
                break;
            case 2:
                mostrarLibros(libros, numLibros);
                break;
            case 3:
                cout << "realizarprestamos y devoluciones"<<endl;
                break;
            case 4:
                cout << "registrarlibro"<<endl;
                break;
            case 5:
                cout << "registrar usuarios"<<endl;
                break;
            case 6:
                cout << "gestionar multas"<<endl;
                break;
            case 7:
                cout << "salir"<<endl;
            default:
                cout << "opcion invalida"<<endl;
        }
    } while (opcion != 7);
}

//     int cantidadusuarios;
//   int genero,titulo,autor;
//     int cantidadlibros = 0;
//     int Numerodelibros [40];
//     string Numerodelibros [3][40];
//     string usuario [3][25];
