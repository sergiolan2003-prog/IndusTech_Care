public class Alerta {

    private int id;
    private String mensaje;
    private String tipo; 
    private boolean activa;


    public Alerta(int id, String mensaje, String tipo) {
        this.id = id;
        this.mensaje = mensaje;
        this.tipo = tipo;
        this.activa = true;
    }

    public static Alerta crearAlerta(int id, String mensaje, String tipo) {
        return new Alerta(id, mensaje, tipo);
    }


    public void mostrarAlerta() {
        System.out.println("ID:" + id);
        System.out.println("Mensaje:" + mensaje);
        System.out.println("Tipo:" + tipo);
        System.out.println("Activa:" + activa);
    }


    public void actualizarAlerta(String nuevoMensaje, String nuevoTipo) {
        this.mensaje = nuevoMensaje;
        this.tipo = nuevoTipo;
    }


    public void desactivarAlerta() {
        this.activa = false;
        System.out.println("Alerta desactivada");
    }


    public void enviarAlerta() {
        if (activa) {
            System.out.println("Alerta: " + mensaje + " | Tipo: " + tipo);
        } else {
            System.out.println("La alerta no esta activa");
        }
    }

    public int getId() {
        return id;
    }

    public boolean isActiva() {
        return activa;
    }
}