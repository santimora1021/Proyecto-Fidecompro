package proyectosantiagomora;

public abstract class Producto {
    protected String nombre;
    protected double precioBase;

    public Producto(String nombre, double precioBase) {
        this.nombre = nombre;
        this.precioBase = precioBase;
    }

    public abstract int calcularPrecioFinal(); // Polimorfismo
    public String getNombre() { return nombre; }

    @Override
    public String toString() {
        return nombre + " - ₡" + calcularPrecioFinal();
    }
}