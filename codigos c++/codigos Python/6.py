class Contacto:
    def __init__(self, nombre, telefono, correo):
        self.nombre = nombre
        self.telefono = telefono
        self.correo = correo

agenda = {}

def agregar_contacto(nombre, telefono, correo):
    agenda[nombre] = Contacto(nombre, telefono, correo)

def buscar_contacto(nombre):
    return agenda.get(nombre)

def eliminar_contacto(nombre):
    if nombre in agenda:
        del agenda[nombre]

# Ejemplo de uso
agregar_contacto("Carlos", "123456789", "carlos@example.com")
agregar_contacto("Maria", "987654321", "maria@example.com")

contacto = buscar_contacto("Carlos")
if contacto:
    print(f"Contacto encontrado: {contacto.nombre}, {contacto.telefono}, {contacto.correo}")

eliminar_contacto("Maria")