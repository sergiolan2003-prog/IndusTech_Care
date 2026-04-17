#include <iostream>
#include <random>
#include <cstring>
#include <cmath>
using namespace std;
void printArray(vector<int> arr) {
    for(int n : arr) {
        cout<<"["<<n<<"] \t";
    }
    cout<<"\n";
}
void printArray(vector<long long> arr) {
    for(int n : arr) {
        cout<<"["<<n<<"] \t";
    }
    cout<<"\n";
}
int readValue(int min, int max) {
    int value;
    while (true) {
        try {
            cin>>value;
            if(cin.fail()) {
                cin.clear();
                cin.ignore(100000000000000000, '\n');
                throw invalid_argument("VALOR INVALIDO\n");
            }
            if(value < min || value > max) {
                continue;
            }
            break;
        }catch(const exception& e) {
            cout<<e.what();
        }
    }
    return value;
}

string cifrarCesar(const string& texto, int desplazamiento) {
    string textoCifrado = texto;

    for (char& caracter : textoCifrado) {
        if ((caracter >= 'A'  and caracter <= 'Z') || (caracter >= 'a' && caracter <= 'z')) {
            char base = (caracter >= 'A' && caracter <= 'Z') ? 'A' : 'a';
            caracter = base + (caracter - base + desplazamiento) % 26;
        }
    }

    return textoCifrado;
}

string descifrarCesar(const string& textoCifrado, int desplazamiento) {
    return cifrarCesar(textoCifrado, -desplazamiento);
}
int eulerPrimeNumber(int n) {
    return (n*n) + n + 41;
}
int extended_gcd(int a, int b, int &x, int &y) {
    if (b == 0) {
        x = 1;
        y = 0;
        return a;
    }
    int x1, y1;
    int gcd = extended_gcd(b, a % b, x1, y1);
    x = y1;
    y = x1 - (a / b) * y1;
    return gcd;
}
int modular_inverse(int e, int phi) {
    int x, y;
    int gcd = extended_gcd(e, phi, x, y);
    if (gcd != 1) {
        throw std::runtime_error("Modular inverse does not exist");
    }
    return (x % phi + phi) % phi;
}
vector<int> generacionEuler(int seed) {
    mt19937 nGenerator(random_device{}());
    uniform_int_distribution<int> distribution(0, 39);
    vector<int> primeGenerated;
    if(seed > 39) {
        cout<<"LA FORMULA DE EULER SOLO ADMITE EL RANGO:\n";
        cout<<"0 <= N <= 39, POR FAVOR USE UNA SEMILLA DIFERENTE\n";
        return primeGenerated;
    }
    for(int i = 0; i < seed; i++) {
        primeGenerated.push_back(eulerPrimeNumber(distribution(nGenerator)));
    }
    return primeGenerated;
}
bool mcd(int a, int b) {
    int res = 1;
    for(int i = 1; i < b; i++) {
        if(a % i == 0 && b % i == 0) {
            res = i;
        }
    }
    return res == 1;
}
int e(int phi) {
    mt19937 primeSelector(random_device{}());
    vector<int> possibleE;
    for(int i = 1; i < phi; i++) {
        if(mcd(i,phi) && possibleE.size() < 40){
            possibleE.push_back(i);
        }
    }
    uniform_int_distribution<int> generator(0,possibleE.size());
    return possibleE.at(generator(primeSelector));

}
int exponenciar(int base, int exponente) {
    for(int i = 1; i <= exponente; i++) {
        base*= base;
    }
    return base;
}
void cifrarMensajeRSA(const char* mensaje, int e, int n) {
    vector<long long> mensajeCifrado;
    int m;
    for(int i = 0; i < strlen(mensaje); i++) {
        unsigned char caracter = static_cast<unsigned char>(mensaje[i]);
        int m = static_cast<int>(caracter);
        int c = exponenciar(m,e);
        mensajeCifrado.push_back(c % n);
    }
    printArray(mensajeCifrado);
}
void cifradoRSA(vector<int> primeNumbers, int seed, const char* mensaje) {
    mt19937 primeSelector(random_device{}());
    uniform_int_distribution<int> distribution(0,seed - 1);
    int p = primeNumbers.at(distribution(primeSelector));
    int q;
    do {
        int index = distribution(primeSelector);
        q = primeNumbers.at(index);
    }while (q == p);
    int n = p * q;
    int phi = (p-1)*(q-1);
    int euler = e(phi);
    int d = modular_inverse(euler, phi);
    cout<<p<<","<<q<<","<<n<<","<<phi<<","<<euler<<","<<d<<"\n";
    cifrarMensajeRSA(mensaje, euler, n);
    cout<<"\n";
}
void cifrarMensaje() {
    int seed;
    string mensaje;
    vector<int> primeNumbersGenerated;
    cin.ignore(1000000000000,'\n');
    cout<<"POR FAVOR INGRESE EL MENSAJE: ";
    getline(cin,mensaje);
    cout<<"POR FAVOR INGRESE LA SEMILLA PARA GENERAR SU CLAVE ENCRIPTADA: (2-100) \n0 MENOS SEGURO - 100 MAS SEGURO: ";
    seed = readValue(2,100);
    cout<<"POR FAVOR SELECCIONE EL METODO DE GENERACION \n";
    cout<<"1. POLINOMIO DE EULER \n";
    cout<<"2. GENERACION FUERZA BRUTA (MENOS SEGURIDAD)\n";
    cout<<"3. COMBINATORIA \n";
    int generationOpc = readValue(1, 3);
    switch (generationOpc) {
        case 1:
            cifradoRSA(generacionEuler(seed), seed, mensaje.c_str());
        break;
        case 2:
        break;
        case 3:
        break;
        default:
            cout<<"ERROR";
        break;
    }
}
void cifrarMensajeArchivo() {

}
void descifrarMensaje() {

}
void cifradoRsa() {
    bool salida = false;
    do {
        int opc;
        cout<<"-------MENU RSA---------\n\n";
        cout<<"1. CIFRAR MENSAJE DESDE CONSOLA \n";
        cout<<"2. CIFRAR MENSAJE DESDE ARCHIVO \n";
        cout<<"3. DESCIFRAR MENSAJE \n";
        cout<<"4. SALIR \n";
        opc = readValue(1,4);
        switch (opc) {
            case 1:
                cout<<"---[OPCION SELECCIONADA: CIFRAR MENSAJE DESDE CONSOLA]---\n";
                cifrarMensaje();
            break;
            case 2:
                cout<<"---[OPCION SELECCIONADA: CIFRAR MENSAJE DESDE ARCHIVO]---\n";
                cifrarMensajeArchivo();
            break;
            case 3:
                cout<<"---[OPCION SELECCIONADA: DESCIFRAR MENSAJE]---\n";
                descifrarMensaje();
            break;
            case 4:
                cout<<"SALIENDO.................\n";
                salida = true;
            break;
        }
    }while (!salida);
}
void cifradoCesar() {

}
int main() {
    bool salida = false;
    do {
        int opc;
        cout<<"-------MENU-------\n";
        cout<<"1. CIFRADO RSA\n";
        cout<<"2. CIFRADO CESAR\n";
        cout<<"3. SALIR\n";
        cout<<"INGRESE OPCION: ";
        opc = readValue(1,3);
        switch (opc) {
            case 1:
                cout<<"---OPCION SELECCIONADA: CIFRADO RSA---\n";
                cifradoRsa();
            break;
            case 2:
                cout<<"---OPCION SELECCIONADA: CIFRADO CESAR---\n";
                cifradoCesar();
            break;
            case 3:
                cout<<"SALIENDO...................";
                salida = true;
            break;
            default:
                cout<<"ERROR \n";
            break;
        }
    }while(!salida);
    string mensaje;
    int popodeburro; // es el dezplasamiento

    cout << "Introduce el mensaje a cifrar: ";
    getline(cin, mensaje);

    cout << "Introduce el desplazamiento (1-25): ";
    cin >> popodeburro;

    while (popodeburro < 1 and popodeburro > 25) {
        cout << "El desplazamiento debe estar entre 1 y 25. Intenta de nuevo: ";
        cin >> popodeburro;
    }

    string mensajeCifrado = cifrarCesar(mensaje, popodeburro);

    cout << "\nMensaje original: " << mensaje << endl;
    cout << "Mensaje cifrado: " << mensajeCifrado << endl;

    // Verificar
    string nalgadecolombis = descifrarCesar(mensajeCifrado, popodeburro);
    cout << "Mensaje descifrado: " << nalgadecolombis << endl;
}

