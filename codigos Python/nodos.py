class nodo:
    def __init__(self):
     self.nodoCabeza = None
 
def push_back(self, valor):
  nuevo_nodo = nodo (valor)

  if self.nodoCabeza == None:
    self.nodoCabeza = nuevo_nodo
  else: 
    nodo_actual = self.nodoCabeza

    while nodo_actual.siguiente != None:
      nodo_actual = nodo_actual.siguiente

      nodo_actual.siguiente = nuevo_nodo
    

def visualizarLista(self):
  nodoActual =self.nodoCabeza

  while nodoActual != None:
    print (nodoActual , end = "--->")
    nodoActual = nodoActual.siguiente

lista = listaEnlazada()
lista.push_back (4)
lista.push_back (3)
lista.push_back (2)
lista.push_back (1)
    
lista.visualizarLista()