import java.util.List;

public class Reporte {

    public static void generar(List<Mantenimiento> lista, List<Maquinaria> maquinas) {

        int totalMantenimientos = lista.size();
        long tiempoTotal = 0;

        for (Mantenimiento m : lista) {
            tiempoTotal += m.calcularDuracion();
        }

        double promedio = (totalMantenimientos > 0) ? tiempoTotal / totalMantenimientos : 0;

        int operativas = 0, reparacion = 0, fueraServicio = 0;

        for (Maquinaria maq : maquinas) {
            switch (maq.estado) {
                case "operativo": operativas++; break;
                case "reparacion": reparacion++; break;
                case "fuera": fueraServicio++; break;
            }
        }

        System.out.println(" REPORTE ");
        System.out.println("Total mantenimientos: " + totalMantenimientos);
        System.out.println("Tiempo total: " + tiempoTotal + " min");
        System.out.println("Tiempo promedio: " + promedio + " min");

        System.out.println("\nEstado de equipos:");
        System.out.println("Operativos: " + operativas);
        System.out.println("En reparación: " + reparacion);
        System.out.println("Fuera de servicio: " + fueraServicio);
    }
}