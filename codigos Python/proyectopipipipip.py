
class Cita:
    contador_id = 1

    def __init__(self, paciente, hora, doctor):
        self.id = Cita.contador_id
        Cita.contador_id += 1
        self.paciente = paciente
        self.hora = hora                        #practicamente esto es solo lectura de lo que decia la clase :D ♥ 
        self.doctor = doctor
        self.estado = "Programada"
        self.siguiente = None

    def __str__(self):
        return f"[{self.id}] {self.paciente:<20} | {self.hora:<16} | Dr {self.doctor:<15} | Estado: {self.estado}"


class ListaCitas:
    def __init__(self):
        self.cabeza = None

    def agregar(self, cita):
        if not self.cabeza:                 #como toda lista y nodo tiene que tener agregar y pues la lista citas ♥ 
                                            # en este caso estamos aregando una cita a la listaCitas si sabe
            self.cabeza = cita
        else:
            actual = self.cabeza
            while actual.siguiente:
                actual = actual.siguiente  
            actual.siguiente = cita

    def buscar_por_id(self, id_cita):                         #facilito vea pues aca tenemos que buscar por el id y entonces como el id es 1 
                                                             # y como necesitamos buscar por id pues un actual = self cabeza que me recorra toda la lista
                                                            #aparte de  de mencionar que si el id que buscamos coincide con el id de la cita ya GG
        actual = self.cabeza                                                                 
        while actual:                                       #yo flipo in colors si sabe
            if actual.id == id_cita:
                return actual
            actual = actual.siguiente
        return None

    def listar_todas(self):
        actual = self.cabeza                                       #la misma madre aca actual = self cabeza listo recorremos con mi ciclo vengativo
        print(actual)                                   #mi amado while entonces seria ya print (actual) y porque actual?
                                                            #no falta que colombis llegue y ponga print (self.cabeza) bobo imprimimod actual
                                                            #ya que es la variable que esta guardando self.cabeza
                                                            #y self.cabeza es la listaCitas GG
        actual = actual.siguiente


class ColaPendientes:
    def __init__(self):
        self.items = []
                                                                #que ahora tengo que explicar esto? seguro? bueno pillese pues 
                                                                #prueba de escritorio breve ud pa crear una cola necesita que una lista y pues sus valores
                                                                #tiqui listo esos valores son en self.items = [] melo
                                                                #de ahi ya sabemos donde vamos a guardar todo lo de la colita chacha
                                                                #fiumba self.items.append que hace???????
                                                                #papi simple ud solo llega pone el append (cita) y para que pues pa encolar
                                                                #y que es encolar? pues agregar un elemento al final de la cola pa eso el append chaval
    def encolar(self, cita):                                    
        self.items.append(cita)

    def desencolar(self):
        return self.items.pop(0) if self.items else None            #esto si que es facil chavalin mire pues asi me aprende mire pa ud desencolar
                                                                     #necesita un pop y porque se preguntaran porque un pop(0) pues pa desencolarla
                                                                    #prueba de escritorio: tiene (a,b,c) si o q pone si quiere eliminar A pues pone pop(0)
                                                                    #mas que todo esto es para separar lo que habia en self.items  con la cita quedan separadas melo
    
    def eliminar(self, cita):           #aca es lo mismo si la cita esta en self.items papi la queremos eliminar? melo
                                        #listo con el bello comando que dio este sagrado lenguaje llamado python llamado remove lo eliminas ♥                                  
        if cita in self.items:          #y porque self.items.remove(cita) pues pues al ver que estan separadas pues solo es llamar la cita :D
            self.items.remove(cita)

    def listar(self):
        for cita in self.items:         #esto si no lo explico me rebajan nota asi que lo explicamos para que  entiendan mejor GG
            print(cita)                #basicamente esto imprime todas las citas pendientes que hayan no hace mas por eso el for 
                                    #que se va recorriendo de todas las citas que hay y busca en self.items las que hayan logica chaval

class PilaHistorial:
    def __init__(self):             #esto si que es breve  pilas pero no pilas del control sino pilas de python jajajaj me comi un payaso
                                    # lo mismo que en colas pero la diferencia es que no me acuerdo JAJAJAJAJAJAJ pero si son distintas
                                    # que para que el push agregamos lo que es la operacion a self.items  y de ahi el pop pues 
                                    #se encarga de eliminarlo mandarlo a comer chorizo y devolver el ultimo elemento y pues si no hay nada en la lista
                                    #pues retorna none :v 
        self.items = []

    def push(self, operacion):
        self.items.append(operacion)

    def pop(self):
        return self.items.pop() if self.items else None


# Registrar cita pa colombis
def registrar_cita(lista, cola, historial):
    paciente = input("Paciente: ")
    hora = input("Hora: ")                                  #esto es full texto ni hay que explicarlo 
    doctor = input("Doctor: ")
    cita = Cita(paciente, hora, doctor)
    lista.agregar(cita)
    cola.encolar(cita)
    historial.push(("crear", cita))
    print(f"Cita registrada con ID {cita.id}")


#todas las citas de colombis el mas gay
def listar_citas(lista):                                 #esto ya es mas que obvio naaaaaaaaaaa
    print("\n""Todas las Citas")
    lista.listar_todas()


#citas pendientes de colombis hora y fecha JAJAJAJ
def listar_pendientes(cola):                             #esto tambien ♥
    print("\nCitas Pendientes")
    cola.listar()


# Modificar cita todo pa colombis mi idolo
def modificar_cita(lista, historial):
    id_cita = int(input("ID de la cita: "))
    cita = lista.buscar_por_id(id_cita)                        #mañana se lo explico profe son las 1:34 de la mañana 
    if cita:
        copia_anterior = Cita(cita.paciente, cita.hora, cita.doctor)
        copia_anterior.id = cita.id
        copia_anterior.estado = cita.estado

        print("1. Cambiar paciente\n2. Cambiar hora\n3. Cambiar doctor")
        op = input("Opción: ")                                                      #a este paso cambio todo y cambio a colombis lo vendo enmercado libre
        if op == "1":
            cita.paciente = input("Nuevo nombre: ")
        elif op == "2":
            cita.hora = input("Nueva hora: ")
        elif op == "3":
            cita.doctor = input("Nuevo doctor: ")
        historial.push(("modificar", copia_anterior))
        print("Cita modificada.")
    else:
        print("No encontrada.")


# Cancelar cita pa colombis por tenerle miedo al exito
def cancelar_cita(lista, cola, historial):
    id_cita = int(input("ID de la cita: "))
    cita = lista.buscar_por_id(id_cita)
    if cita and cita.estado == "Programada":              #posdata: esto lo estoy haciendo desde la 2:45am ayuda porfavor
        historial.push(("cancelar", cita))                
        cita.estado = "Cancelada"
        cola.eliminar(cita)
        print("Cita cancelada ahora colombis se murio porque la cancelaste :(")
    else:
        print("No se puede cancelar jajajaja colombis sigue vivo con bendicion")


def procesar_cita(cola, historial):
    if cola.items:
        cita = cola.desencolar()
        historial.push(("procesar", cita))
        cita.estado = "Completada"
        print(f"Cita {cita.id} procesada")
    else:
        print("No hay citas para procesar")


def deshacer(historial, lista, cola):
    if not historial.items:
        print("Nada que deshacer")
        return

    tipo, cita = historial.pop()

    if tipo == "crear":
        cita.estado = "Cancelada"
        cola.eliminar(cita)
        print("Deshecha creación")

    elif tipo == "cancelar":
        cita.estado = "Programada"
        cola.encolar(cita)
        print("Deshecha cancelación")

    elif tipo == "procesar":
        cita.estado = "Programada"
        cola.encolar(cita)
        print("Deshecho procesamiento")

    elif tipo == "modificar":
        actual = lista.buscar_por_id(cita.id)
        if actual:
            actual.paciente = cita.paciente
            actual.hora = cita.hora
            actual.doctor = cita.doctor
            print("Deshecha modificación.")



def menu():
    lista = ListaCitas()
    cola = ColaPendientes()
    historial = PilaHistorial()

    while True:
        print ("HOSPITAL DE COLOMBIS DELUXE ")
        print("\n1. Registrar cita")
        print("2. Listar todas las citas")
        print("3. Listar citas pendientes")
        print("4. Modificar cita")
        print("5. Cancelar cita")
        print("6. Procesar cita")
        print("7. Deshacer última operación")
        print("8. Salir")
        op = input("Opción: ")

        if op == "1":
            registrar_cita(lista, cola, historial)
        elif op == "2":
            listar_citas(lista)
        elif op == "3":
            listar_pendientes(cola)                                     #puras opciones al fallo :D
        elif op == "4":
            modificar_cita(lista, historial)
        elif op == "5":
            cancelar_cita(lista, cola, historial)
        elif op == "6":
            procesar_cita(cola, historial)
        elif op == "7":
            deshacer(historial, lista, cola)
        elif op == "8":
            print("Saliendo...")
            break
        else:
            print("Opción inválida.")


# Iniciar la aplicación
if __name__ == "__main__":
    menu()

