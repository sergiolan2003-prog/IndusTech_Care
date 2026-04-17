class Auto:
    def __init__(self, marca, modelo, kilometraje):
        self.marca = marca
        self.modelo = modelo
        self.kilometraje = kilometraje

    def registrar_viaje(self, kilometros):
        self.kilometraje += kilometros

# Crear autos
autos = [
    Auto("Toyota", "Corolla", 15000),
    Auto("Ford", "Mustang", 20000)
]

# Registrar viaje
autos[0].registrar_viaje(500)

# Mostrar información
for auto in autos:
    print(f"Marca: {auto.marca}, Modelo: {auto.modelo}, Kilometraje: {auto.kilometraje}")