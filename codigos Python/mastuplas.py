tupla = (1,2,3)
a,b,c = tupla

print ("valorares desempaquetados:", a ,  b , c)




def operaciones (a,b):
    suma = a + b[0]
    resta = a - b[1]
    multiplicacion = a * b[2]
    division = a / b[3]

    return [suma,resta,multiplicacion,division]

    resultados = operaciones (10,5)
    print ("resultados:", resultados)