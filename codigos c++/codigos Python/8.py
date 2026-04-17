class Evento:
    def __init__(self, nombre, fecha):
        self.nombre = nombre
        self.fecha = fecha
        self.participantes = []

    def registrar_participante(self, nombre):
        self.participantes.append(nombre)

    def eliminar_participante(self, nombre):
        if nombre in self.participantes:
            self.participantes.remove(nombre)

# Diccionario de eventos
eventos = {
    "Conferencia": Evento("Conferencia", "2023-10-15"),
    "Taller": Evento("Taller", "2023-11-20")
}

# Registrar y eliminar participantes
eventos["Conferencia"].registrar_participante("Juan")
eventos["Taller"].registrar_participante("Ana")
eventos["Conferencia"].eliminar_participante("Juan")

for nombre, evento in eventos.items():
    print(f"{nombre} ({evento.fecha}): {evento.participantes}")