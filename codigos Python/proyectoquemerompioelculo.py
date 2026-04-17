class Nodo:
    def __init__(self, clave, valor):
        self.clave = clave
        self.valor = valor
        self.color = 'ROJO'
        self.izquierda = None
        self.derecha = None
        self.padre = None

class PlanificadorEventos:
    def __init__(self):
        self.raiz = None
    
    def esta_vacio(self):
        return self.raiz is None
    
    def insertar(self, clave, valor):
        nuevo_nodo = Nodo(clave, valor)
        
        if self.raiz is None:
            nuevo_nodo.color = 'NEGRO'
            self.raiz = nuevo_nodo
            return
        
        # Inserción BST normal
        actual = self.raiz
        while True:
            if nuevo_nodo.clave < actual.clave:
                if actual.izquierda is None:
                    actual.izquierda = nuevo_nodo
                    nuevo_nodo.padre = actual
                    break
                actual = actual.izquierda
            else:
                if actual.derecha is None:
                    actual.derecha = nuevo_nodo
                    nuevo_nodo.padre = actual
                    break
                actual = actual.derecha
        
        # Arreglar RB-Tree
        self._arreglar_insercion(nuevo_nodo)
    
    def _arreglar_insercion(self, nodo):
        while nodo != self.raiz and nodo.padre.color == 'ROJO':
            if nodo.padre == nodo.padre.padre.izquierda:
                tio = nodo.padre.padre.derecha
                if tio and tio.color == 'ROJO':
                    nodo.padre.color = 'NEGRO'
                    tio.color = 'NEGRO'
                    nodo.padre.padre.color = 'ROJO'
                    nodo = nodo.padre.padre
                else:
                    if nodo == nodo.padre.derecha:
                        nodo = nodo.padre
                        self._rotar_izquierda(nodo)
                    nodo.padre.color = 'NEGRO'
                    nodo.padre.padre.color = 'ROJO'
                    self._rotar_derecha(nodo.padre.padre)
            else:
                tio = nodo.padre.padre.izquierda
                if tio and tio.color == 'ROJO':
                    nodo.padre.color = 'NEGRO'
                    tio.color = 'NEGRO'
                    nodo.padre.padre.color = 'ROJO'
                    nodo = nodo.padre.padre
                else:
                    if nodo == nodo.padre.izquierda:
                        nodo = nodo.padre
                        self._rotar_derecha(nodo)
                    nodo.padre.color = 'NEGRO'
                    nodo.padre.padre.color = 'ROJO'
                    self._rotar_izquierda(nodo.padre.padre)
        self.raiz.color = 'NEGRO'
    
    def _rotar_izquierda(self, x):
        y = x.derecha
        x.derecha = y.izquierda
        if y.izquierda:
            y.izquierda.padre = x
        y.padre = x.padre
        if x.padre is None:
            self.raiz = y
        elif x == x.padre.izquierda:
            x.padre.izquierda = y
        else:
            x.padre.derecha = y
        y.izquierda = x
        x.padre = y
    
    def _rotar_derecha(self, x):
        y = x.izquierda
        x.izquierda = y.derecha
        if y.derecha:
            y.derecha.padre = x
        y.padre = x.padre
        if x.padre is None:
            self.raiz = y
        elif x == x.padre.derecha:
            x.padre.derecha = y
        else:
            x.padre.izquierda = y
        y.derecha = x
        x.padre = y
    
    def mirar(self):
        if self.esta_vacio():
            return None
        actual = self.raiz
        while actual.izquierda:
            actual = actual.izquierda
        return (actual.clave, actual.valor)
    
    def buscar(self, clave):
        actual = self.raiz
        while actual:
            if clave == actual.clave:
                return actual.valor
            elif clave < actual.clave:
                actual = actual.izquierda
            else:
                actual = actual.derecha
        return None
    
    def recorrido_inorden(self):
        resultado = []
        def recorrer(nodo):
            if nodo:
                recorrer(nodo.izquierda)
                resultado.append((nodo.clave, nodo.valor))
                recorrer(nodo.derecha)
        recorrer(self.raiz)
        return resultado
    
    def altura(self):
        def obtener_altura(nodo):
            if not nodo:
                return -1
            return 1 + max(obtener_altura(nodo.izquierda), obtener_altura(nodo.derecha))
        return obtener_altura(self.raiz)
    
    def contar_nodos(self):
        def contar(nodo):
            if not nodo:
                return 0
            return 1 + contar(nodo.izquierda) + contar(nodo.derecha)
        return contar(self.raiz)
    
    def sucesor(self, clave):
        actual = self.raiz
        while actual:
            if clave == actual.clave:
                break
            elif clave < actual.clave:
                actual = actual.izquierda
            else:
                actual = actual.derecha
        
        if not actual:
            return None
        
        if actual.derecha:
            actual = actual.derecha
            while actual.izquierda:
                actual = actual.izquierda
            return (actual.clave, actual.valor)
        
        padre = actual.padre
        while padre and actual == padre.derecha:
            actual = padre
            padre = padre.padre
        return (padre.clave, padre.valor) if padre else None
    
    def predecesor(self, clave):
        actual = self.raiz
        while actual:
            if clave == actual.clave:
                break
            elif clave < actual.clave:
                actual = actual.izquierda
            else:
                actual = actual.derecha
        
        if not actual:
            return None
        
        if actual.izquierda:
            actual = actual.izquierda
            while actual.derecha:
                actual = actual.derecha
            return (actual.clave, actual.valor)
        
        padre = actual.padre
        while padre and actual == padre.izquierda:
            actual = padre
            padre = padre.padre
        return (padre.clave, padre.valor) if padre else None
    
    def consulta_rango(self, bajo, alto):
        resultado = []
        def buscar_rango(nodo):
            if not nodo:
                return
            if bajo <= nodo.clave <= alto:
                resultado.append((nodo.clave, nodo.valor))
            if nodo.clave > bajo:
                buscar_rango(nodo.izquierda)
            if nodo.clave < alto:
                buscar_rango(nodo.derecha)
        buscar_rango(self.raiz)
        return resultado



if __name__ == "__main__":
    planificador = PlanificadorEventos()
    
    # Caso práctico
    planificador.insertar(20, "Autenticación usuario A")
    planificador.insertar(15, "Sincronización BD")
    planificador.insertar(25, "Envío notificación")
    planificador.insertar(10, "Limpieza caché")
    
    print("mirar():", planificador.mirar())
    print("buscar(25):", planificador.buscar(25))
    print("recorrido_inorden():", planificador.recorrido_inorden())
    print("consulta_rango(12, 22):", planificador.consulta_rango(12, 22))