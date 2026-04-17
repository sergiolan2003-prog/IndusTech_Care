x = 6
suma = 0
for i in range (1,x):
    if x % i == 0:
        suma += i
    if suma == x:
        print (suma)
