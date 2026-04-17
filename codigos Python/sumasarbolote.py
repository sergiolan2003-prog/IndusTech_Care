def suma_por_nivel(root):
    if not root:
        return []

    resultado = []
    cola = [root]

    while cola:
        nivel_tamano = len(cola)
        suma_nivel = 0
        siguiente_nivel = []

        for i in range(nivel_tamano):
            nodo = cola[i]
            suma_nivel += nodo.valor

            if nodo.izq:
                siguiente_nivel.append(nodo.izq)
            if nodo.der:
                siguiente_nivel.append(nodo.der)

        resultado.append(suma_nivel)
        cola = siguiente_nivel

    return resultado
# Primera vuelta del while (Nivel 0)
#nivel_tamano = 1

#suma_nivel = 0

#Iteramos:

#nodo = 3

#suma_nivel += 3 → suma_nivel = 3

#Agregamos hijos: -7, 5 → siguiente_nivel = [-7, 5]

#resultado = [3]

#cola = [-7, 5]

