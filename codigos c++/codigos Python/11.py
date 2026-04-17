class Libro:
    def __init__(self, titulo, autor):
        self.titulo = titulo
        self.autor = autor

class Usuario:
    def __init__(self, nombre):
        self.nombre = nombre
        self.libros_prestados = []

    def prestar_libro(self, libro):
        self.libros_prestados.append(libro)

    def devolver_libro(self, libro):
        if libro in self.libros_prestados:
            self.libros_prestados.remove(libro)

# Diccionario para rastrear préstamos
prestamos = {}

libro1 = Libro("1984", "George Orwell")
libro2 = Libro("Sapiens", "Yuval Noah Harari")

usuario1 = Usuario("Juan")
usuario1.prestar_libro(libro1)
prestamos[libro1.titulo] = usuario1.nombre

usuario2 = Usuario("Ana")
usuario2.prestar_libro(libro2)
prestamos[libro2.titulo] = usuario2.nombre

# Mostrar préstamos
for libro, usuario in prestamos.items():
    print(f"Libro: {libro}, Prestado a: {usuario}")