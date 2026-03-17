package proyectosantiagomora;

public class DetalleFactura {
    private Producto producto;
    private int cantidad;

    public DetalleFactura(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public int getSubtotal() {
        return producto.calcularPrecioFinal() * cantidad;
    }

    public String obtenerLinea() {
        return cantidad + " x " + producto.getNombre() + " = ₡" + getSubtotal();
    }
}