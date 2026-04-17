class Nodo:
    def __init__(self, valor):
        self.valor = valor
        self.izquierda = None
        self.derecha = None

def contar_hojas(nodo):
    if nodo is None:
        return 0
    if nodo.izquierda is None and nodo.derecha is None:
        return 1
    return contar_hojas(nodo.izquierda) + contar_hojas(nodo.derecha)

raiz = Nodo('A')
raiz.izquierda = Nodo('B')
raiz.derecha = Nodo('C')
raiz.izquierda.izquierda = Nodo('D')
raiz.derecha.izquierda = Nodo('E')
raiz.derecha.derecha = Nodo('F')
raiz.derecha.derecha.derecha = Nodo('G')

print("Cantidad de nodos hoja:", contar_hojas(raiz))
