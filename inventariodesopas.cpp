#include <iostream>
#include <string>

using namespace std;

// Estructura para representar un producto
struct Producto {
    string nombre;
    float precio;
    int cantidad;
};

int main() {
    int n, numCarrito = 0;
    
    // Pedir al usuario el número de productos en el inventario
    cout << "Ingrese el numero de productos en el inventario: ";
    cin >> n;

    // Crear un arreglo dinamico para el inventario
    Producto* inventario = new Producto[n];

    // Pedir los detalles de los productos en el inventario
    for (int i = 0; i < n; i++) {
        cout << "Ingrese el nombre del producto " << i + 1 << ": ";
        cin.ignore();  // Limpiar el buffer
        getline(cin, inventario[i].nombre);
        cout << "Ingrese el precio de " << inventario[i].nombre << ": ";
        cin >> inventario[i].precio;
        cout << "Ingrese la cantidad de " << inventario[i].nombre << ": ";
        cin >> inventario[i].cantidad;
    }

    Producto carrito[10]; // Definir un carrito de 10 productos
    int opcion;

    do {
        cout << "\nTienda en Linea - Menu de opciones:\n";
        cout << "1. Agregar producto al carrito\n";
        cout << "2. Realizar pago\n";
        cout << "3. Ver inventario\n";
        cout << "4. Salir\n";
        cout << "Seleccione una opcion: ";
        cin >> opcion;

        if (opcion == 1) {
            string nombreProducto;
            cout << "Ingrese el nombre del producto a agregar al carrito: ";
            cin.ignore();  // Limpiar el buffer
            getline(cin, nombreProducto);

            // Buscar el producto en el inventario
            bool encontrado = false;
            for (int i = 0; i < n; i++) {
                if (inventario[i].nombre == nombreProducto && inventario[i].cantidad > 0) {
                    carrito[numCarrito++] = inventario[i];  // Agregar producto al carrito
                    inventario[i].cantidad--;  // Reducir la cantidad del producto en el inventario
                    cout << "Producto agregado al carrito.\n";
                    encontrado = true;
                    break;
                }
            }

            if (!encontrado) {
                cout << "Producto no disponible o agotado.\n";
            }

        } else if (opcion == 2) {
            float total = 0;
            // Sumar los precios de todos los productos en el carrito
            for (int i = 0; i < numCarrito; i++) {
                total += carrito[i].precio;  // Sumar el precio de cada producto en el carrito
            }
            cout << "Total a pagar: " << total << endl;
            cout << "Pago realizado con exito.\n";
            numCarrito = 0; // Vaciar el carrito después del pago
        } else if (opcion == 3) {
            cout << "\nInventario actual:\n";
            for (int i = 0; i < n; i++) {
                cout << inventario[i].nombre << " - Precio: " << inventario[i].precio << " - Cantidad: " << inventario[i].cantidad << endl;
            }
        }

    } while (opcion != 4);

    // Liberar memoria
    delete[] inventario;

    cout << "Gracias por su compra" << endl;

    return 0;
}
