class Nodo:
    def __init__(self, dato):
        self.dato = dato
        self.sig = None

class Lista:
    def __init__(self):
        self.inicio = None

    def agregar(self, dato):
        nuevo = Nodo(dato)
        if not self.inicio:
            self.inicio = nuevo
        else:
            actual = self.inicio
            while actual.sig:
                actual = actual.sig
            actual.sig = nuevo

    def mostrar(self):
        actual = self.inicio
        while actual:
            print(actual.dato, end=" ")
            actual = actual.sig
        print()

    def rotar(self, k):
        if not self.inicio or k == 0:
            return

        # Contar el total de nodos
        actual = self.inicio
        longitud = 1
        while actual.sig:
            actual = actual.sig
            longitud += 1

        k = k % longitud
        if k == 0:
            return

        # Conectar el final al inicio para hacerla circular
        actual.sig = self.inicio

        # Avanzar hasta el nuevo final
        actual = self.inicio
        for _ in range(k - 1):
            actual = actual.sig

        # Nuevo inicio y romper el ciclo
        self.inicio = actual.sig
        actual.sig = None

# Ejemplo
lista = Lista()
for n in [1, 2, 3, 4, 5]:
    lista.agregar(n)

print("Lista original:")
lista.mostrar()

lista.rotar(2)

print("Lista rotada 2 posiciones:")
lista.mostrar()
