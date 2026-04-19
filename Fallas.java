
    public class Falla {
    
    private int id;
    private String descripcion;
    private String nivelGravedad; 

    public Falla(int id, String descripcion, String nivelGravedad) {
        this.id = id;
        this.descripcion = descripcion;
        this.nivelGravedad = nivelGravedad;
    }

    public static Falla crearFalla(int id, String descripcion, String nivelGravedad) {
        return new Falla(id, descripcion, nivelGravedad);
    }

    public void mostrarFalla() {
        System.out.println("ID: " + id);
        System.out.println("Descripción: " + descripcion);
        System.out.println("Nivel de Gravedad: " + nivelGravedad);
    }

    public void actualizarFalla(String nuevaDescripcion, String nuevaGravedad) {
        this.descripcion = nuevaDescripcion;
        this.nivelGravedad = nuevaGravedad;
    }

    public void eliminarFalla() {
        this.descripcion = null;
        this.nivelGravedad = null;
        System.out.println("Falla eliminada");
    }

    public int getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getNivelGravedad() {
        return nivelGravedad;
    }
}