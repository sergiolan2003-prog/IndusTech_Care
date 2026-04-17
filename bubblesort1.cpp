#include <iostream>
#include <vector>
using namespace std;

// Función para ordenar el vector de forma descendente usando Bubble Sort
void bubbleSortDescendente(vector<float>& calificaciones) {
    int n = calificaciones.size();
    bool huboCambios;
    
    do {
        huboCambios = false;
        for(int i = 0; i < n-1; i++) {
            // Si el elemento actual es menor que el siguiente, los intercambiamos
            if(calificaciones[i] < calificaciones[i+1]) {
                float temp = calificaciones[i];
                calificaciones[i] = calificaciones[i+1];
                calificaciones[i+1] = temp;
                huboCambios = true;
            }
        }
    } while(huboCambios);
}

// Función para imprimir el vector
void imprimirCalificaciones(const vector<float>& calificaciones) {
    for(float calif : calificaciones) {
        cout << calif << " ";
    }
    cout << endl;
}

int main() {
    vector<float> calificaciones;
    float calificacion;
    char continuar;
    
    cout << "Programa de ordenamiento de calificaciones (Descendente)\n";
    cout << "=====================================================\n\n";
    
    do {
        cout << "Ingrese una calificacion: ";
        cin >> calificacion;
        
        calificaciones.push_back(calificacion);
        
        cout << "¿Desea ingresar otra calificacion? (s/n): ";
        cin >> continuar;
    } while(continuar == 's' || continuar == 'S');
    
    cout << "\nCalificaciones originales: ";
    imprimirCalificaciones(calificaciones);
    
    // Ordenar las calificaciones
    bubbleSortDescendente(calificaciones);
    
    cout << "Calificaciones ordenadas (descendente): ";
    imprimirCalificaciones(calificaciones);
    
    return 0;

    }

