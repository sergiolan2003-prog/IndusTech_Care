# Lista doble con Bubble Sort
class NodoDoble:
    def __init__(self, dato):
        self.dato = dato
        self.siguiente = None
        self.anterior = None

class ListaDoble:
    def __init__(self):
        self.cabeza = None

    def push_back(self, dato):
        nuevo = NodoDoble(dato)
        if not self.cabeza:
            self.cabeza = nuevo
        else:
            temp = self.cabeza
            while temp.siguiente:
                temp = temp.siguiente
            temp.siguiente = nuevo
            nuevo.anterior = temp

    def bubble_sort(self):
        if not self.cabeza or not self.cabeza.siguiente:
            return
        cambio = True
        while cambio:
            cambio = False
            actual = self.cabeza
            while actual.siguiente:
                if actual.dato > actual.siguiente.dato:
                    actual.dato, actual.siguiente.dato = actual.siguiente.dato, actual.dato
                    cambio = True
                actual = actual.siguiente

    def mostrar(self):
        temp = self.cabeza
        while temp:
            print(temp.dato, end=' ')
            temp = temp.siguiente
        print()

class NodoSimple:
    def __init__(self, dato):
        self.dato = dato
        self.siguiente = None

class ListaSimple:
    def __init__(self):
        self.cabeza = None

    def push_back(self, dato):
        nuevo = NodoSimple(dato)
        if not self.cabeza:
            self.cabeza = nuevo
        else:
            temp = self.cabeza
            while temp.siguiente:
                temp = temp.siguiente
            temp.siguiente = nuevo

    def selection_sort(self):
        actual = self.cabeza
        while actual:
            minimo = actual
            temp = actual.siguiente
            while temp:
                if temp.dato < minimo.dato:
                    minimo = temp
                temp = temp.siguiente
            if minimo != actual:
                actual.dato, minimo.dato = minimo.dato, actual.dato
            actual = actual.siguiente

    def mostrar(self):
        temp = self.cabeza
        while temp:
            print(temp.dato, end=' ')
            temp = temp.siguiente
        print()


# Prueba
print("Doble (Bubble Sort):")
ld = ListaDoble()
for n in [5, 1, 4, 2]:
    ld.push_back(n)
ld.mostrar()
ld.bubble_sort()
ld.mostrar()

print("\nSimple (Selection Sort):")
ls = ListaSimple()
for n in [7, 3, 6, 2]:
    ls.push_back(n)
ls.mostrar()
ls.selection_sort()
ls.mostrar()
