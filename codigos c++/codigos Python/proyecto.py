class CuentaBancaria:
   
    num = 1
    def __init__(self, titular):
        # Incrementa el número de cuenta para cada nueva cuenta
        CuentaBancaria.num += 1
        self.num_cuenta = f'Codigo-{CuentaBancaria.num}'
        self.titular = titular
        self.saldo = 0.0
        # Inicializa una lista vacía para almacenar el historial de transacciones
        self.historial_transacciones = []

    #depositar dinero
    def depositar(self, monto):
        if monto > 0:
            # Aumenta el saldo con el monto depositado
            self.saldo += monto
            # Registra la transacción en el historial
            self.historial_transacciones.append(f"Depósito: ${monto}")
            return f"Depósito exitoso. Nuevo saldo: ${self.saldo}"
        return "El monto debe ser positivo"

    #retirar dinero
    def retirar(self, monto):
        if monto > 0:
            if self.saldo >= monto:
                # Reduce el saldo con el monto retirado
                self.saldo -= monto
                # Registra la transacción en el historial
                self.historial_transacciones.append(f"Retiro: ${monto}")
                return f"Retiro exitoso. Nuevo saldo: ${self.saldo}"
            return "Saldo insuficiente"
        return "El monto debe ser positivo"

    #consultar el saldo
    def ver_saldo(self):
        return f"Saldo actual: ${self.saldo}"

    #ver el historial de transacciones de la cuenta
    def ver_historial(self):
        # Une todas las transacciones en una cadena separada por saltos de línea (no sabia como hacerlo esto me lo explico olave)
        return "\n".join(self.historial_transacciones)


class Banco:
    # Método constructor de la clase Banco
    def __init__(self):
        #diccionario vacío para almacenar las cuentasew
        self.cuentas = []
    
                
    #crear una nueva cuenta
    def Nueva_cuenta(self, titular, saldo_inicial=0):
        # Crea una nueva instancia de CuentaBancaria
        nueva_cuenta = CuentaBancaria(titular)
        # Asigna el saldo inicial a la nueva cuenta
        nueva_cuenta.saldo = saldo_inicial
        # Almacena la nueva cuenta en el diccionario de cuentas
        self.cuentas[nueva_cuenta.num_cuenta] = nueva_cuenta
        # Devuelve un mensaje con el número de cuenta generado
        return f"Cuenta creada con éxito. Número de cuenta: {nueva_cuenta.num_cuenta}"
    
           


    # Método para depositar dinero en una cuenta específica
    def depositar_banco(self, numero_cuenta, monto):
        if numero_cuenta in self.cuentas:
            #depositar de la cuenta correspondiente
            return self.cuentas[numero_cuenta].depositar(monto)
        return "La cuenta no existe o no fue encontrada"

    # Método para retirar dinero de una cuenta específica
    def retirar_banco(self, numero_cuenta, monto):
        if numero_cuenta in self.cuentas:
            # Llama al método retirar de la cuenta correspondiente
            return self.cuentas[numero_cuenta].retirar(monto)
        return "No se pudo retirar Motivos: La cuenta no existe o no fue encontrada"

    #consultar el saldo de una cuenta específica
    def ver_saldo_banco(self, numero_cuenta):
        if numero_cuenta in self.cuentas:
            # Llama al método ver_saldo de la cuenta correspondiente
            return self.cuentas[numero_cuenta].ver_saldo()
        return "No se pudo ver el saldo Motivos: La cuenta no existe o no fue encontrada"

    # Método para ver el historial de transacciones de una cuenta específica
    def ver_historial_banco(self, numero_cuenta):
        if numero_cuenta in self.cuentas:
            # Llama al método ver_historial de la cuenta correspondiente
            return self.cuentas[numero_cuenta].ver_historial()
        return "No se pudo ver el historial Motivos: La cuenta no existe o no fue encontrada"

    # Método para transferir dinero entre dos cuentas
    def transferir(self, cuenta_origen, cuenta_destino, monto):
        if cuenta_origen not in self.cuentas:                   
            return "Cuenta de origen no encontrada o no existe"
        if cuenta_destino not in self.cuentas:
            return "Cuenta de destino no encontrada o no existe"

        # Obtiene las instancias de las cuentas de origen y destino (esto me dijo olave que lo pusiera pq tampoco sabia)
        cuenta_orig = self.cuentas[cuenta_origen]
        cuenta_dest = self.cuentas[cuenta_destino]


        if cuenta_orig.saldo >= monto:
            # Realiza la transferencia     # ejemplo xd: 10000 - 5000 = 5000
            cuenta_orig.saldo -= monto
            cuenta_dest.saldo += monto
            # Registra la transacción en el historial de ambas cuentas
            cuenta_orig.historial_transacciones.append(f"Transferencia enviada: -${monto}")
            cuenta_dest.historial_transacciones.append(f"Transferencia recibida: +${monto}")
            return f"Transferencia exitosa. Nuevo saldo: ${cuenta_orig.saldo}"
        return "Saldo insuficiente en la cuenta de origen"


# Función para mostrar el menú y manejar las opciones del usuario
def menu():
    # Crea una instancia de la clase Banco
    banco = Banco()
    while True:
        # Muestra el menú de opciones
        print("\n--- Menú Banco ---")
        print("1. Crear nueva cuenta")
        print("2. Depositar")
        print("3. Retirar")
        print("4. Ver saldo")
        print("5. Transferir")
        print("6. Ver historial de transacciones")
        print("7. Salir")
        opcion = input("Seleccione una opción: ")

        if opcion == "1":
            #  crear una nueva cuenta
            titular = input("Ingrese el nombre del titular: ")
            saldo_inicial = input("Ingrese el saldo inicial (tambien puede poner 0): ")
            saldo_inicial = float(saldo_inicial) if saldo_inicial else 0.0
            print(banco.Nueva_cuenta(titular, saldo_inicial))

        elif opcion == "2":
            # depositar dinero en una cuenta
            num_cuenta = input("Ingrese el número de cuenta: ")
            monto = float(input("Ingrese el monto a depositar: "))
            print(banco.depositar_banco(num_cuenta, monto))

        elif opcion == "3":
            # retirar dinero de una cuenta
            num_cuenta = input("Ingrese el número de cuenta: ")
            monto = float(input("Ingrese el monto a retirar: "))
            print(banco.retirar_banco(num_cuenta, monto))

        elif opcion == "4":
            #consultar el saldo de una cuenta
            num_cuenta = input("Ingrese el número de cuenta: ")
            print(banco.ver_saldo_banco(num_cuenta))

        elif opcion == "5":
            # transferir dinero entre cuentas
            cuenta_origen = input("Ingrese el número de cuenta de origen: ")
            cuenta_destino = input("Ingrese el número de cuenta de destino: ")
            monto = float(input("Ingrese el monto a transferir: "))
            print(banco.transferir(cuenta_origen, cuenta_destino, monto))

        elif opcion == "6":
            # ver el historial de transacciones de una cuenta
            num_cuenta = input("Ingrese el número de cuenta: ")
            print(banco.ver_historial_banco(num_cuenta))

        elif opcion == "7":
            print("Gracias por usarme, profe. Se lo agradezco mucho :) Sistema 100% funcional. \n Recomiéndenos en WhatsApp 304 394 5372 \n instagram como: juanpablo8552005")
            break

        else:
            print("Opción no válida. Intente nuevamente.")


menu()


