package proyectosantiagomora;

public class Cliente {
    private String cedula, nombre;
    public Cliente(String cedula, String nombre) { this.cedula = cedula; this.nombre = nombre; }
    public String getNombre() { return nombre; }
    public String getCedula() { return cedula; }
}