class Hola:
    def init (self, nombre, edad):
        self.__nombre = nombre
        self.__edad = edad

    def saluda(self):
        print ("hola Soy {} y tengo {} años".format(self.nombre,self.edad))
