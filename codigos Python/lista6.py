class Nodo:
    def __init__(self, dato):
        self.dato = dato
        self.sig = None

class ListaSimple:
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
            print(actual.dato, end=" → " if actual.sig else "\n")
            actual = actual.sig

    def es_palindromo(self):
        # Guardar los valores de la lista en una pila (usamos una lista de Python)
        valores = []
        actual = self.inicio
        while actual:
            valores.append(actual.dato)
            actual = actual.sig
        
    
        actual = self.inicio
        while actual:
            if actual.dato != valores.pop():
                return False
            actual = actual.sig
        
        return True
