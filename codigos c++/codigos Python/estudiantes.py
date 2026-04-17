estudiantes = {"Ana": 85, "Juan": 90, "Luis": 78, "María": 92, "Carlos": 88}

notas = list(estudiantes.values())
promedio = sum(notas) / len(notas)
superior = sum(1 for n in notas if n > promedio)

def ordenar_por_nota(item):
    return item[1]

ordenados = sorted(estudiantes.items(), key=ordenar_por_nota, reverse=True) #si queremos de menor a mayor quitamos el reverse = true

print(f"Nota promedio: {promedio:.2f}")
print(f"Estudiantes con nota superior al promedio: {superior}")
print("Estudiantes ordenados por nota:", ordenados)