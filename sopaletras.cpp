#include <iostream>
#include <vector>
#include <string>
#include <algorithm>
using namespace std;

bool buscarHorizontal(vector<vector<char>>& sopa, string palabra) {
    for (auto& fila : sopa) {
        string linea(fila.begin(), fila.end());
        if (linea.find(palabra) != string::npos) return true;
    }
    return false;
}

bool buscarVertical(vector<vector<char>>& sopa, string palabra) {
    int columnas = sopa[0].size();
    for (int c = 0; c < columnas; ++c) {
        string columnaStr;
        for (auto& fila : sopa) {
            columnaStr += fila[c];
        }
        if (columnaStr.find(palabra) != string::npos) return true;
    }
    return false;
}

bool buscarPalabra(vector<vector<char>>& sopa, string palabra) {
    return buscarHorizontal(sopa, palabra) || buscarVertical(sopa, palabra);
}

void bubbleSort(vector<char>& arr) {
    int n = arr.size();
    for (int i = 0; i < n-1; i++) {
        for (int j = 0; j < n-i-1; j++) {
            if (arr[j] > arr[j+1]) {
                swap(arr[j], arr[j+1]);
            }
        }
    }
}

void ordenarSopa(vector<vector<char>>& sopa) {
    for (auto& fila : sopa) {
        bubbleSort(fila);
    }
    sort(sopa.begin(), sopa.end(), 
        [](const vector<char>& a, const vector<char>& b) {
            return a[0] < b[0];
        });
}

void imprimirSopa(vector<vector<char>>& sopa) {
    for (auto& fila : sopa) {
        for (char letra : fila) {
            cout << letra << " ";
        }
        cout << endl;
    }
}

int main() {
    vector<vector<char>> sopa = {
        {'H', 'O', 'L', 'A'},
        {'M', 'U', 'N', 'D'},
        {'P', 'E', 'R', 'R'}
    };

    string palabras[] = {"HOLA", "PERRO", "MUNDO"};
    
    cout << "Sopa de letras original:" << endl;
    imprimirSopa(sopa);
    cout << endl;

    for (auto& palabra : palabras) {
        cout << "Buscando '" << palabra << "': " 
             << (buscarPalabra(sopa, palabra) ? "Encontrada" : "No encontrada") 
             << endl;
    }

    ordenarSopa(sopa);
    cout << "\nSopa de letras ordenada (Bubble Sort):" << endl;
    imprimirSopa(sopa);

    return 0;
}