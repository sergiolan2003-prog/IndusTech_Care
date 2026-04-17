class Empleado:
    def __init__(self, nombre, salario, departamento):
        self.nombre = nombre
        self.salario = salario
        self.departamento = departamento

    def aumentar_salario(self, porcentaje):
        self.salario *= (1 + porcentaje / 100)

    def cambiar_departamento(self, nuevo_departamento):
        self.departamento = nuevo_departamento


empleados = [
    Empleado("Carlos", 3000, "Ventas"),
    Empleado("Maria", 4000, "IT")
]

# Aumentar salario y cambiar departamento
empleados[0].aumentar_salario(10)
empleados[1].cambiar_departamento("Recursos Humanos")

# Mostrar información
for empleado in empleados:
    print(f"Nombre: {empleado.nombre}, Salario: {empleado.salario}, Departamento: {empleado.departamento}")