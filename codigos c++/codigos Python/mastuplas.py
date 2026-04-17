tupla = (1,2,3)
a,b,c = tupla

print ("valorares desempaquetados:", a ,  b , c)




def operaciones (a,b):
    suma = a + b
    resta = a - b
    multiplicacion = a * b
    division = a / b

    return [suma,resta,multiplicacion,division]

    resultados = operaciones (10,5)
    print ("resultados:", resultados)