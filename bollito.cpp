#include <iostream> 
#include <random> // Para generar números aleatorios
#include <cstring> 
#include <cmath> // Para operaciones matemáticas avanzadas
#include <fstream> // Para manejo de archivos
#include <ranges> // Para manipular colecciones de datos
#include <sstream> // Para trabajar con flujos de texto
using namespace std; 

// Funcion para imprimir un vector de enteros
void printArray(vector<int> arr) {
    for (int n : arr) { // Iterar sobre cada elemento del vector
        cout << "[" << n << "] \t"; // Imprimir el elemento entre corchetes
    }
    cout << "\n"; // Nueva línea al final
}

// Sobrecarga de printArray para enteros largos sin signo
void printArray(vector<unsigned long long> arr) {
    for (unsigned long long n : arr) { // Iterar sobre el vector
        cout << "[" << n << "] \t"; // Imprimir el elemento entre corchetes
    }
    cout << "\n"; // Nueva línea
}

// Sobrecarga de printArray para caracteres
void printArray(vector<char> arr) {
    for (char c : arr) { // Iterar sobre cada caracter
        cout << c; // Imprimir el caracter
    }
    cout << "\n"; // Nueva línea
}

// Funcion para imprimir una matriz de mensajes cifrados
void printMessages(vector<vector<unsigned long long>> messages) {
    int acum = 0; // Contador para numerar los mensajes
    for (vector<unsigned long long> msg : messages) { // Iterar sobre cada fila de la matriz
        cout << "(" << ++acum << ")" << "\t"; // Numerar el mensaje
        for (unsigned long long value : msg) { // Iterar sobre los elementos del mensaje
            cout << "[" << value << "] \t"; // Imprimir el valor entre corchetes
        }
        cout << "\n"; // Nueva línea
    }
}

// Leer un valor entero del usuario dentro de un rango permitido
int readValue(int min, int max) {
    int value; // Almacena el valor ingresado
    while (true) { // Bucle infinito hasta que se valide el valor
        try {
            cin >> value; // Leer el valor
            if (cin.fail()) { // Si la entrada no es válida
                cin.clear(); // Limpiar el estado de error
                cin.ignore(100000000000000000, '\n'); // Ignorar el resto de la entrada
                throw invalid_argument("VALOR INVALIDO\n"); // Lanzar excepción
            }
            if (value < min || value > max) { // Si el valor está fuera del rango
                continue; // Reintentar
            }
            break; // Salir del bucle si el valor es válido
        } catch (const exception& e) { // Capturar excepción
            cout << e.what(); // Mostrar el mensaje de error
        }
    }
    return value; // Retornar el valor válido
}

// Cifrado César:
string cifrarCesar(const string& texto, int desplazamiento) {
    string textoCifrado = texto; // Copiar el texto original

    for (char& caracter : textoCifrado) { //iteramos sobre cada caracter de texto desde A hasta las Z
        if ((caracter >= 'A' && caracter <= 'Z') || (caracter >= 'a' && caracter <= 'z')) {
            //char base = verifica si es minuscula o mayuscula
            char base = (caracter >= 'A' && caracter <= 'Z') ? 'A' : 'a';
            caracter = base + (caracter - base + desplazamiento) % 26; //se deszplasa
        }
    }

    return textoCifrado; // Retornar el texto cifrado
}

// Descifrado César: Invierte el desplazamiento
string descifrarCesar(const string& textoCifrado, int desplazamiento) {
    return cifrarCesar(textoCifrado, -desplazamiento); // Llama a cifrar con desplazamiento negativo
}

// Genera un número primo con la fórmula de Euler
int eulerPrimeNumber(int n) {
    return (n * n) + n + 41; // Fórmula de Euler
}

// Verifica si un número es entero
bool isInteger(double d) {
    return floor(d) == d; // Compara el número con su parte entera
}

// Estima el valor de d en RSA
int estimateD(double phi, double e) {
    double d;
    double k = 1; // Contador
    while (true) {
        d = (1 + (k * phi)) / e; // Calcula d
        if (isInteger(d)) { // Verifica si d es entero
            break; // Salir del bucle si d es válido
        }
        k++; // Incrementar k
    }
    return d; // Retornar d
}

// Genera números primos con la fórmula de Euler
vector<int> generacionEuler(int seed) {
    const int EULER_LIMIT = 39; // Límite de la fórmula de Euler
    mt19937 nGenerator(random_device{}()); // Generador de números aleatorios
    uniform_int_distribution<int> distribution(0, EULER_LIMIT); // Distribución uniforme
    vector<int> primeGenerated; // Almacenar primos generados
    if (seed > EULER_LIMIT) {
        cout << "LA FORMULA DE EULER SOLO ADMITE EL RANGO:\n";
        cout << "0 <= N <= 39, POR FAVOR USE UNA SEMILLA DIFERENTE\n";
        return primeGenerated; // Retorna vector vacío
    }
    for (int i = 0; i < seed; i++) { // Generar números primos
        primeGenerated.push_back(eulerPrimeNumber(distribution(nGenerator)));
    }
    return primeGenerated; // Retornar primos generados
}

// Verifica si un número es primo
bool isPrimo(int n) {
    int div = 0; // Contador de divisores
    for (int i = 1; i < n / 2; i++) { // Iterar hasta la mitad del número
        if (n % i == 0) { // Si es divisible
            div++;
        }
        if (div >= 2) { // Si tiene más de 2 divisores
            return false; // No es primo
        }
    }
    return true; // Es primo
}

// Generación de claves RSA y métodos de cifrado se encuentran después. 
// El código es extenso, pero sigue lógicas similares a estas bases descritas: 
// verificaciones, transformaciones matemáticas, y flujo de entrada/salida.
