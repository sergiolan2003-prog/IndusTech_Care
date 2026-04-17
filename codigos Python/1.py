class Estudiante:
    def __init__(self, nombre, edad, calificaciones):
        self.nombre = nombre
        self.edad = edad
        self.calificaciones = calificaciones

    def calcular_promedio(self):
        if len(self.calificaciones) == 0:
            return 0
        return sum(self.calificaciones) / len(self.calificaciones)

    def mostrar_informacion(self):
        print(f"Nombre: {self.nombre}")
        print(f"Edad: {self.edad}")
        print(f"Calificaciones: {self.calificaciones}")
        print(f"Promedio: {self.calcular_promedio()}")
        print("-" * 20)

# Crear una lista de estudiantes
estudiantes = [
    Estudiante("Juan", 20, [85, 90, 78]),
    Estudiante("Ana", 22, [92, 88, 91]),
    Estudiante("Luis", 21, [77, 82, 79])
]

# Mostrar la información de cada estudiante
for estudiante in estudiantes:
    estudiante.mostrar_informacion()