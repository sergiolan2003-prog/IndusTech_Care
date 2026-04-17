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
    
    def kesimo_menor(self, k):
      
        contador = [0] 
        resultado = [None]
        self._kesimo_menor_inorder(self.raiz, k, contador, resultado)
        return resultado[0]
    
    def _kesimo_menor_inorder(self, nodo, k, contador, resultado):
        if nodo is None or resultado[0] is not None:
            return
        
        self._kesimo_menor_inorder(nodo.izq, k, contador, resultado)
        
     
        contador[0] += 1
        if contador[0] == k:
            resultado[0] = nodo.valor
            return
        
        self._kesimo_menor_inorder(nodo.der, k, contador, resultado)
    
    def inorder_completo(self):
        resultado = []
        self._inorder(self.raiz, resultado)
        return resultado
    
    def _inorder(self, nodo, resultado):
        if nodo is not None:
            self._inorder(nodo.izq, resultado)
            resultado.append(nodo.valor)
            self._inorder(nodo.der, resultado)
    
    def mostrar(self):
        self._mostrar(self.raiz, 0)
    
    def _mostrar(self, nodo, nivel):
        if nodo is not None:
            self._mostrar(nodo.der, nivel + 1)
            print("  " * nivel + str(nodo.valor))
            self._mostrar(nodo.izq, nivel + 1)


secuencia = [8, 5, 15, 3, 7, 12, 18, 6, 10]
bst = BST()

print("K-esimo mas pequeñin")
print("=" * 30)
print(f"bts del ejercicio 1 pa: {secuencia}")

for valor in secuencia:
    bst.insertar(valor)

print("\nÁrbol BST:")
bst.mostrar()


elementos_ordenados = bst.inorder_completo()
print(f"\nElementos en orden: {elementos_ordenados}")


k = 3
tercer_menor = bst.kesimo_menor(k)

print(f"\nEl {k}° elemento mas pequeño es: {tercer_menor}")



print("\nVerificación con otros valores de k:")
for i in range(1, min(6, len(elementos_ordenados) + 1)):
    kesimo = bst.kesimo_menor(i)
    print(f"{i}° elemento más pequeño: {kesimo}")

print("-" * 14)
print("1. realiza el recorrido por el bst jeje")
print("2. El recorrido inorder de un BST da elementos ordenados")
print("3. Contar elementos durante el recorrido")
print("4. Cuando el contador llega a k, devuelve ese elemento")
print()

print ("ventajas ricas ricosas:")
print("No necesitamos ordenar todo el árbol")
print("Paramos cuando encontramos el k-ésimo elemento")
print("Complejidad: O(k) en el mejor caso, O(n) en el peor caso :/ ")