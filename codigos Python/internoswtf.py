def contar_nodos_internos(nodo):
    if nodo is None or (nodo.izq is None and nodo.der is None):
        return 0
    internos_izq = contar_nodos_internos(nodo.izq)
    internos_der = contar_nodos_internos(nodo.der)
    return 1 + internos_izq + internos_der

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
print(contar_nodos_internos(raiz))