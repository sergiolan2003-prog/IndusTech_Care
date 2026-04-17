def verCuenta(self, numcuentas):
        for cuenta in self.cuentas:
            if numcuentas in cuenta.keys():
                return cuenta[numcuentas]
