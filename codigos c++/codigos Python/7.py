class Alumno:
    def __init__(self, nombre, carrera):
        self.nombre = nombre
        self.carrera = carrera
        self.notas = []

    def agregar_nota(self, nota):
        self.notas.append(nota)

    def promedio(self):
        return sum(self.notas) / len(self.notas) if self.notas else 0

# Crear alumnos y agregar notas
alumno1 = Alumno("Juan", "Ingeniería")
alumno1.agregar_nota(85)
alumno1.agregar_nota(90)

alumno2 = Alumno("Ana", "Medicina")
alumno2.agregar_nota(88)
alumno2.agregar_nota(92)

# Mostrar promedios
print(f"{alumno1.nombre}: {alumno1.promedio()}")
print(f"{alumno2.nombre}: {alumno2.promedio()}")