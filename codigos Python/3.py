class Libro:
    def __init__(self, titulo, autor, año_publicacion):
        self.titulo = titulo
        self.autor = autor
        self.año_publicacion = año_publicacion

biblioteca = {
    "Ficción": [],
    "No Ficción": []
}

def agregar_libro(categoria, titulo, autor, año_publicacion):
    biblioteca[categoria].append(Libro(titulo, autor, año_publicacion))

def buscar_libro(titulo):
    for categoria, libros in biblioteca.items():
        for libro in libros:
            if libro.titulo == titulo:
                return libro
    return None

# Ejemplo de uso
agregar_libro("Ficción", "1984", "George Orwell", 1949)
agregar_libro("No Ficción", "Sapiens", "Yuval Noah Harari", 2011)

libro = buscar_libro("1984")
if libro:
    print(f"Libro encontrado: {libro.titulo} por {libro.autor}")