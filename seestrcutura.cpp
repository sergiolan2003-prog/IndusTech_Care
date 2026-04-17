#include <iostream>
#include <vector>

using namespace std;

int main() {
    // Definir los datos: edad y asistencia de los estudiantes
    vector<int> edades = {20, 22, 21, 23};
    vector<int> asistencias = {90, 85, 92, 88};

    int totalEdad = 0, totalAsistencia = 0;
    int numEstudiantes = edades.size();

    // Calcular las sumas
    for (int i = 0; i < numEstudiantes; i++) {
        totalEdad += edades[i];
        totalAsistencia += asistencias[i];
    }

    // Calcular los promedios
    float promedioEdad = (float) totalEdad / numEstudiantes;
    float promedioAsistencia = (float) totalAsistencia / numEstudiantes;

    // Mostrar los resultados
    cout << "Promedio de edad: " << promedioEdad << endl;
    cout << "Promedio de asistencia: " << promedioAsistencia << "%" << endl;

    return 0;
}
