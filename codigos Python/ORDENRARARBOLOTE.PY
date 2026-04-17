def bst_a_lista_ordenada(nodo, lista):
    if nodo:
        bst_a_lista_ordenada(nodo.izq, lista)
        lista.append(nodo.valor)
        bst_a_lista_ordenada(nodo.der, lista)

class Nodo:
    def __init__(self, valor):
        self.valor = valor
        self.izq = None
        self.der = None

raiz = Nodo(10)
raiz.izq = Nodo(5)
raiz.der = Nodo(20)
raiz.izq.izq = Nodo(3)
raiz.izq.der = Nodo(7)
raiz.der.der = Nodo(30)

lista = []
bst_a_lista_ordenada(raiz, lista)
print(lista)
