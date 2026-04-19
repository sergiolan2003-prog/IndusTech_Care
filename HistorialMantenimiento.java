// IC-63: historial por máquina implementado
import java.util.ArrayList;
import java.util.List;

public class HistorialMantenimiento {
    private List<Mantenimiento> historial = new ArrayList<>();

    public void agregar(Mantenimiento m) {
        historial.add(m);
    }
    public void consultar() {
        for (Mantenimiento m : historial) {
            m.mostrar();
        }
    }
}
