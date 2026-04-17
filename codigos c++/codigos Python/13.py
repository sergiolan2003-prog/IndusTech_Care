class Viaje:
    def __init__(self, conductor, destino):
        self.conductor = conductor
        self.destino = destino
        self.pasajeros = []

    def agregar_pasajero(self, pasajero):
        self.pasajeros.append(pasajero)

    def calcular_costo_total(self, costo_por_pasajero):
        return len(self.pasajeros) * costo_por_pasajero

# Diccionario para administrar viajes
viajes = {
    "Viaje1": Viaje("Carlos", "Madrid"),
    "Viaje2": Viaje("Maria", "Barcelona")
}

# Agregar pasajeros y calcular costos
viajes["Viaje1"].agregar_pasajero("Juan")
viajes["Viaje1"].agregar_pasajero("Ana")
viajes["Viaje2"].agregar_pasajero("Luis")

for nombre, viaje in viajes.items():
    print(f"{nombre}: Conductor {viaje.conductor}, Destino {viaje.destino}, Costo total: ${viaje.calcular_costo_total(10)}")