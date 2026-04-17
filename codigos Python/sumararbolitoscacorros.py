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

def suma_nodos(nodo):
    if nodo is None:
        return 0
    suma_izq = suma_nodos(nodo.izq)
    suma_der = suma_nodos(nodo.der)
    return nodo.valor + suma_izq + suma_der
print(suma_nodos(raiz))