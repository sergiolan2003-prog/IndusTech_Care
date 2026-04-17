class Nodo:
    def __init__(self, valor):
        self.valor = valor
        self.izq = None
        self.der = None

class BST:
    def __init__(self):
        self.raiz = None
    
    def insertar(self, valor):
        if self.raiz is None:
            self.raiz = Nodo(valor)
        else:
            self._insertar(self.raiz, valor)
    
    def _insertar(self, nodo, valor):
        if valor < nodo.valor:
            if nodo.izq is None:
                nodo.izq = Nodo(valor)
            else:
                self._insertar(nodo.izq, valor)
        else:
            if nodo.der is None:
                nodo.der = Nodo(valor)
            else:
                self._insertar(nodo.der, valor)
    
    def altura(self):
        return self._altura(self.raiz)
    
    def _altura(self, nodo):
        if nodo is None:
            return -1
        return 1 + max(self._altura(nodo.izq), self._altura(nodo.der))
    
    def contar_hojas(self):
        return self._contar_hojas(self.raiz)
    
    def _contar_hojas(self, nodo):
        if nodo is None:
            return 0
        if nodo.izq is None and nodo.der is None:
            return 1
        return self._contar_hojas(nodo.izq) + self._contar_hojas(nodo.der)
    
    def mostrar(self):
        self._mostrar(self.raiz, 0)
    
    def _mostrar(self, nodo, nivel):
        if nodo is not None:
            self._mostrar(nodo.der, nivel + 1)
            print("  " * nivel + str(nodo.valor))
            self._mostrar(nodo.izq, nivel + 1)


secuencia = [8, 5, 15, 3, 7, 12, 18, 6, 10]
bst = BST()

print("Insertando:", secuencia)
for valor in secuencia:
    bst.insertar(valor)

print("\nÁrbol BST final:")
bst.mostrar()

print(f"\nAltura: {bst.altura()}")
print(f"Nodos hoja: {bst.contar_hojas()}")

def mostrar_hojas(nodo):
    if nodo is None:
        return []
    if nodo.izq is None and nodo.der is None:
        return [nodo.valor]
    hojas = []
    hojas.extend(mostrar_hojas(nodo.izq))
    hojas.extend(mostrar_hojas(nodo.der))
    return hojas

hojas = mostrar_hojas(bst.raiz)
print(f"Las hojas son: {sorted(hojas)}")