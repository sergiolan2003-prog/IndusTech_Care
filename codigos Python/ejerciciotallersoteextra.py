class Nodo:
    def __init__(self, value):
        self.value = value
        self.left = None
        self.right = None

def es_bst(node, min_val= 0, max_val= 100):
    if node is None:
        return True

    if not (min_val < node.value < max_val):
        print(f"Violación: el nodo con valor {node.value} no está en el rango permitido ({min_val}, {max_val})")
        return False

    return (es_bst(node.left, min_val, node.value) and
            es_bst(node.right, node.value, max_val))

root = Nodo(10)
root.left = Nodo(5)
root.right = Nodo(12)
root.left.left = Nodo(3)
root.left.right = Nodo(15)  
root.right.right = Nodo(20)


if es_bst(root):
    print("El arbol es un BST valido.")
else:
    print("El arbol NO es un BST valido.")
