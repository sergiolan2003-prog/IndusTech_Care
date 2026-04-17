class Producto:
    def __init__(self, nombre, precio, cantidad):
        self.nombre = nombre
        self.precio = precio
        self.cantidad = cantidad

# Lista de productos
inventario = [
    Producto("Camisa", 20, 50),
    Producto("Pantalón", 30, 30),
    Producto("Zapatos", 50, 20)
]

def buscar_producto(nombre):
    for producto in inventario:
        if producto.nombre == nombre:
            return producto
    return None

def calcular_valor_total():
    return sum(producto.precio * producto.cantidad for producto in inventario)

# Ejemplo de uso
producto = buscar_producto("Camisa")
if producto:
    print(f"Producto encontrado: {producto.nombre}, Precio: ${producto.precio}, Cantidad: {producto.cantidad}")

print(f"Valor total del inventario: ${calcular_valor_total()}")