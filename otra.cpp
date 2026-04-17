#include <iostream>
#include <vector>

using namespace std;

int main() {
    // Definir el tamaño de la matriz principal
    int filas_matriz, columnas_matriz;
    
    // Ingresar el tamaño de la matriz
    cout << "Ingrese el número de filas de la matriz: ";
    cin >> filas_matriz;
    cout << "Ingrese el número de columnas de la matriz: ";
    cin >> columnas_matriz;

    // Crear la matriz principal
    vector<vector<char>> matriz(filas_matriz, vector<char>(columnas_matriz));
    
    // Ingresar la matriz principal
    cout << "Ingrese los elementos de la matriz (por filas):\n";
    for (int i = 0; i < filas_matriz; ++i) {
        for (int j = 0; j < columnas_matriz; ++j) {
            cin >> matriz[i][j];
        }
    }

    // Ingresar el patrón de la submatriz
    int filas_patron_submatriz, columnas_patron_submatriz;
    cout << "Ingrese el número de filas de la submatriz: ";
    cin >> filas_patron_submatriz;
    cout << "Ingrese el número de columnas de la submatriz: ";
    cin >> columnas_patron_submatriz;

    vector<vector<char>> patron_submatriz(filas_patron_submatriz, vector<char>(columnas_patron_submatriz));
    
    cout << "Ingrese los elementos del patrón de la submatriz (por filas):\n";
    for (int i = 0; i < filas_patron_submatriz; ++i) {
        for (int j = 0; j < columnas_patron_submatriz; ++j) {
            cin >> patron_submatriz[i][j];
        }
    }

    // Ingresar el patrón del vector
    int longitud_patron_vector;
    cout << "Ingrese la longitud del patrón del vector: ";
    cin >> longitud_patron_vector;

    vector<char> patron_vector(longitud_patron_vector);
    
    cout << "Ingrese los elementos del patrón del vector:\n";
    for (int i = 0; i < longitud_patron_vector; ++i) {
        cin >> patron_vector[i];
    }

    // Buscar la submatriz
    cout << "\nBuscando submatriz..." << endl;
    for (int i = 0; i <= filas_matriz - filas_patron_submatriz; i++) {  // Recorrer filas
        for (int j = 0; j <= columnas_matriz - columnas_patron_submatriz; j++) {  // Recorrer columnas
            bool coincide = true;
            for (int x = 0; x < filas_patron_submatriz; x++) {
                for (int y = 0; y < columnas_patron_submatriz; y++) {
                    if (matriz[i + x][j + y] != patron_submatriz[x][y]) {
                        coincide = false;
                        break;
                    }
                }
                if (!coincide) break;
            }
            if (coincide) {
                cout << "Submatriz encontrada en: Fila " << i << ", Columna " << j << endl;
            }
        }
    }

    // Buscar el vector horizontal
    cout << "\nBuscando vector horizontal..." << endl;
    for (int i = 0; i < filas_matriz; i++) {  // Recorrer filas
        for (int j = 0; j <= columnas_matriz - longitud_patron_vector; j++) {  // Recorrer columnas
            bool coincide = true;
            for (int k = 0; k < longitud_patron_vector; k++) {  // Patrón de 3 caracteres
                if (matriz[i][j + k] != patron_vector[k]) {
                    coincide = false;
                    break;
                }
            }
            if (coincide) {
                cout << "Vector encontrado horizontalmente en: Fila " << i << ", Columna " << j << endl;
            }
        }
    }

    // Buscar el vector vertical
    cout << "\nBuscando vector vertical..." << endl;
    for (int j = 0; j < columnas_matriz; j++) {  // Recorrer columnas
        for (int i = 0; i <= filas_matriz - longitud_patron_vector; i++) {  // Recorrer filas
            bool coincide = true;
            for (int k = 0; k < longitud_patron_vector; k++) {  // Patrón de 3 caracteres
                if (matriz[i + k][j] != patron_vector[k]) {
                    coincide = false;
                    break;
                }
            }
            if (coincide) {
                cout << "Vector encontrado verticalmente en: Fila " << i << ", Columna " << j << endl;
            }
        }
    }

    return 0;
}
