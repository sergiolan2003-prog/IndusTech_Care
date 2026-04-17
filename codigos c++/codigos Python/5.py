class CuentaBancaria:
    def __init__(self, titular, saldo=0, tipo_cuenta="Ahorros"):
        self.titular = titular
        self.saldo = saldo
        self.tipo_cuenta = tipo_cuenta

    def depositar(self, cantidad):
        self.saldo += cantidad

    def retirar(self, cantidad):
        if cantidad <= self.saldo:
            self.saldo -= cantidad
        else:
            print("Fondos insuficientes")

    def mostrar_saldo(self):
        print(f"Saldo de {self.titular}: ${self.saldo}")

# Administrar cuentas
cuentas = {}
cuentas["001"] = CuentaBancaria("Juan Perez", 1000)
cuentas["002"] = CuentaBancaria("Ana Gomez", 500)

cuentas["001"].depositar(200)
cuentas["002"].retirar(100)

for numero, cuenta in cuentas.items():
    cuenta.mostrar_saldo()