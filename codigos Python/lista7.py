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

    def eliminar(self, valor):
        actual = self.inicio
        while actual:
            if actual.dato == valor:
                if actual.ant:
                    actual.ant.sig = actual.sig
                else:
                    self.inicio = actual.sig  # era el primero

                if actual.sig:
                    actual.sig.ant = actual.ant
                return  # solo elimina el primero que encuentra
            actual = actual.sig

# Ejemplo
lista = ListaDoble()
for n in [10, 20, 30, 40, 50]:
    lista.agregar(n)

print("Lista original:")
lista.mostrar()

lista.eliminar(30)

print("Lista después de eliminar 30:")
lista.mostrar()
