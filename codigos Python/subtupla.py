tupla = (1,2,3,4,5,6,7,8,9,10)
subtupla = ()

print ("tupla original:",tupla)

for i in range (len ( tupla)):
    if tupla [i] %2 == 0:
        subtupla += (tupla[i],)
        print (subtupla)


