import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

public class Mantenimiento {

    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;

    public Mantenimiento(LocalDate fecha, LocalTime horaInicio, LocalTime horaFin) {
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    // Calcular duración en minutos
    public long calcularDuracion() {
        return Duration.between(horaInicio, horaFin).toMinutes();
    }

    // Analizar si el mantenimiento fue largo
    public boolean esMantenimientoLargo() {
        return calcularDuracion() > 120; // más de 2 horas
    }

    // Mostrar información
    public void mostrar() {
        System.out.println("Fecha: " + fecha);
        System.out.println("Hora inicio: " + horaInicio);
        System.out.println("Hora fin: " + horaFin);
        System.out.println("Duración: " + calcularDuracion() + " minutos");

        if (esMantenimientoLargo()) {
            System.out.println(" Mantenimiento largo");
        } else {
            System.out.println("Mantenimiento normal");
        }
        System.out.println();
    }
}