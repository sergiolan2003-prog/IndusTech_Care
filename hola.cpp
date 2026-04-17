#include <iostream>
#include <fstream>
#include <vector>
#include <string>
#include <sstream>

using namespace std;

// Función para leer una matriz de caracteres desde un archivo
vector<vector<char>> leer_matriz(const string& archivo) {
    ifstream input(archivo);
    vector<vector<char>> matriz;
    string linea;

    if (!input) {
        cerr << "No se pudo abrir el archivo: " << archivo << endl;
        exit(1); // Terminar si no se puede abrir el archivo
    }

    while (getline(input, linea)) {
        vector<char> fila(linea.begin(), linea.end());
        matriz.push_back(fila);
    }

    return matriz;
}

// Función para buscar un patrón de submatriz dentro de la matriz
vector<pair<int, int>> buscar_submatriz(const vector<vector<char>>& matriz, const vector<vector<char>>& patron) {
    vector<pair<int, int>> coincidencias;
    int filas_matriz = matriz.size();
    int columnas_matriz = matriz[0].size();
    int filas_patron = patron.size();
    int columnas_patron = patron[0].size();

    for (int i = 0; i <= filas_matriz - filas_patron; ++i) {
        for (int j = 0; j <= columnas_matriz - columnas_patron; ++j) {
            bool coincide = true;
            for (int x = 0; x < filas_patron; ++x) {
                for (int y = 0; y < columnas_patron; ++y) {
                    if (matriz[i + x][j + y] != patron[x][y]) {
                        coincide = false;
                        break;
                    }
                }
                if (!coincide) break;
            }
            if (coincide) {
                coincidencias.push_back({i, j});
            }
        }
    }

    return coincidencias;
}

// Función para buscar un patrón de vector (horizontal y vertical) en la matriz
vector<pair<int, int>> buscar_vector(const vector<vector<char>>& matriz, const vector<char>& patron) {
    vector<pair<int, int>> coincidencias;
    int filas_matriz = matriz.size();
    int columnas_matriz = matriz[0].size();
    int longitud_patron = patron.size();

    // Buscar horizontalmente
    for (int i = 0; i < filas_matriz; ++i) {
        for (int j = 0; j <= columnas_matriz - longitud_patron; ++j) {
            bool coincide = true;
            for (int k = 0; k < longitud_patron; ++k) {
                if (matriz[i][j + k] != patron[k]) {
                    coincide = false;
                    break;
                }
            }
            if (coincide) {
                coincidencias.push_back({i, j});
            }
        }
    }

    // Buscar verticalmente
    for (int j = 0; j < columnas_matriz; ++j) {
        for (int i = 0; i <= filas_matriz - longitud_patron; ++i) {
            bool coincide = true;
            for (int k = 0; k < longitud_patron; ++k) {
                if (matriz[i + k][j] != patron[k]) {
                    coincide = false;
                    break;
                }
            }
            if (coincide) {
                coincidencias.push_back({i, j});
            }
        }
    }

    return coincidencias;
}

// Función para escribir el resultado en un archivo
void escribir_resultados(const string& archivo, const vector<vector<char>>& matriz,
                          const vector<pair<int, int>>& coincidencias_submatriz,
                          const vector<pair<int, int>>& coincidencias_vector) {
    ofstream output(archivo);
    if (!output) {
        cerr << "No se pudo crear el archivo de salida: " << archivo << endl;
        exit(1); // Terminar si no se puede abrir el archivo
    }

    // Escribir la matriz principal
    output << "Matriz Principal:\n";
    for (const auto& fila : matriz) {
        for (char c : fila) {
            output << c << " ";
        }
        output << "\n";
    }

    // Escribir las coincidencias de la submatriz
    output << "\nPatrón de Submatriz - Coincidencias:\n";
    for (const auto& pos : coincidencias_submatriz) {
        output << "Posición: (" << pos.first << ", " << pos.second << ")\n";
    }

    // Escribir las coincidencias del vector
    output << "\nPatrón de Vector - Coincidencias:\n";
    for (const auto& pos : coincidencias_vector) {
        output << "Posición: (" << pos.first << ", " << pos.second << ")\n";
    }

    // Explicación del método
    output << "\nExplicación del Método:\n";
    output << "El programa busca todas las coincidencias de un patrón (submatriz o vector) en la matriz principal.\n";
    output << "Para la submatriz, el programa verifica todas las posibles posiciones dentro de la matriz donde podría caber la submatriz.\n";
    output << "Para el vector, se buscan coincidencias tanto horizontal como verticalmente.\n";
}

int main() {
    string matriz_file = "matriz_input.txt";
    string patron1_file = "patron1_input.txt";
    string patron2_file = "patron2_input.txt";
    string resultado_file = "resultados_output.txt";

    // Leer los archivos
    vector<vector<char>> matriz = leer_matriz(matriz_file);
    vector<vector<char>> patron_submatriz = leer_matriz(patron1_file);
    vector<char> patron_vector;
    
    // Leer el patrón del vector (asumiendo que el patrón es una sola línea)
    ifstream input_vector(patron2_file);
    string linea;
    if (getline(input_vector, linea)) {
        patron_vector.assign(linea.begin(), linea.end());
    }

    // Buscar las coincidencias
    auto coincidencias_submatriz = buscar_submatriz(matriz, patron_submatriz);
    auto coincidencias_vector = buscar_vector(matriz, patron_vector);

    // Escribir los resultados
    escribir_resultados(resultado_file, matriz, coincidencias_submatriz, coincidencias_vector);

    cout << "Resultados guardados en el archivo " << resultado_file << endl;

    return 0;
}
