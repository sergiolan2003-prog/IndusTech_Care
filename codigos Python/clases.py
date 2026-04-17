class Hola:
    def init (self, nombre, edad):
        self.nombre = nombre
        self.edad = edad

    def saluda(self):
        print ("hola Soy {} y tengo {} años".format(self.nombre,self.edad))
