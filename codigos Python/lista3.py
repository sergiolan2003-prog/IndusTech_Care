class Nodo:
    def __init__(self, dato):
        self.dato = dato
        self.ant = None
        self.sig = None

class ListaDoble:
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
            nuevo.ant = actual

    def mostrar(self):
        actual = self.inicio
        while actual:
            print(actual.dato, end=" ")
            actual = actual.sig
        print()

    def invertir(self):
        actual = self.inicio
        temp = None

        while actual:
            # Intercambia anterior y siguiente
            temp = actual.ant
            actual.ant = actual.sig
            actual.sig = temp
            # Avanza (antes era sig, ahora es ant)
            actual = actual.ant

        # Ajustamos el nuevo inicio
        if temp:
            self.inicio = temp.ant


lista = ListaDoble()
for n in [1, 2, 3, 4, 5]:
    lista.agregar(n)

print("Lista original:")
lista.mostrar()

lista.invertir()

print("Lista invertida:")
lista.mostrar()
