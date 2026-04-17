class Nodo:
    def __init__(self, clave, valor):
        self.clave = clave
        self.valor = valor
        self.siguiente = None

class TablaHash:
    def __init__(self, tamaño):
        self.tamaño = tamaño
        self.tabla = [None] * tamaño

    def hash(self, clave):
        return hash(clave) % self.tamaño

    def insertar(self, clave, valor):
        indice = self.hash(clave)
        nodo = self.tabla[indice]

        if nodo is None:
            self.tabla[indice] = Nodo(clave, valor)
        else:
           
            while nodo:
                if nodo.clave == clave:
                    nodo.valor = valor 
                    return
                if nodo.siguiente is None:
                    break
                nodo = nodo.siguiente
            nodo.siguiente = Nodo(clave, valor)

    def obtener(self, clave):
        indice = self.hash(clave)
        nodo = self.tabla[indice]

        while nodo:
            if nodo.clave == clave:
                return nodo.valor
            nodo = nodo.siguiente
        return None

    def eliminar(self, clave):
        indice = self.hash(clave)
        nodo = self.tabla[indice]
        anterior = None

        while nodo:
            if nodo.clave == clave:
                if anterior:
                    anterior.siguiente = nodo.siguiente
                else:
                    self.tabla[indice] = nodo.siguiente
                return True
            anterior = nodo
            nodo = nodo.siguiente
        return False

    def mostrar(self):
        for i, nodo in enumerate(self.tabla):
            print(f"Índice {i}:", end=" ")
            actual = nodo
            while actual:
                print(f"({actual.clave}: {actual.valor})", end=" -> ")
                actual = actual.siguiente
            print("None")


tabla = TablaHash(5)
tabla.insertar("Juan", 25)
tabla.insertar("Ana", 30)
tabla.insertar("Carlos", 40)
tabla.insertar("Juan", 26)  
tabla.insertar("María", 35)

tabla.mostrar()

print("Buscar 'Ana':", tabla.obtener("Ana"))
print("Eliminar 'Carlos':", tabla.eliminar("Carlos"))
tabla.mostrar()