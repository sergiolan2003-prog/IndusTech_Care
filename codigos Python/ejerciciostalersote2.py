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
    
    def preorder(self):
        resultado = []
        self._preorder(self.raiz, resultado)
        return resultado
    
    def _preorder(self, nodo, resultado):
        if nodo is not None:
            resultado.append(nodo.valor)  
            self._preorder(nodo.izq, resultado)  
            self._preorder(nodo.der, resultado)  
    
    def inorder(self):
        resultado = []
        self._inorder(self.raiz, resultado)
        return resultado
    
    def _inorder(self, nodo, resultado):
        if nodo is not None:
            self._inorder(nodo.izq, resultado)   
            resultado.append(nodo.valor)         
            self._inorder(nodo.der, resultado)  
    
    def postorder(self):
        resultado = []
        self._postorder(self.raiz, resultado)
        return resultado
    
    def _postorder(self, nodo, resultado):
        if nodo is not None:
            self._postorder(nodo.izq, resultado) 
            self._postorder(nodo.der, resultado) 
            resultado.append(nodo.valor)          
    
    def mostrar(self):
        self._mostrar(self.raiz, 0)
    
    def _mostrar(self, nodo, nivel):
        if nodo is not None:
            self._mostrar(nodo.der, nivel + 1)
            print("  " * nivel + str(nodo.valor))
            self._mostrar(nodo.izq, nivel + 1)


bst = BST()
valores = [20, 10, 30, 5, 15, 40]

print("los valores del bst respete  >:( ):", valores)
for valor in valores:
    bst.insertar(valor)

print("\nÁrbol BST:")
print("    20")
print("  10   30")
print("5  15   40")

print("\nmostramos el arbolito o q?:")
bst.mostrar()

print("\nrecorridos sabrosos y caramelosos:")
print("=" * 15)

preorder = bst.preorder()
inorder = bst.inorder()
postorder = bst.postorder()

print(f"Pre-order:  {preorder}")
print(f"In-order:   {inorder}")
print(f"Post-order: {postorder}")

print("\nExplicacionsita:")
print("- preorder: raiz -> izquierda -> derecha")
print("- inorder: izquierda -> raiz -> derecha (ordenado)")
print("- postorder: izquierda -> derecha -> raiz")