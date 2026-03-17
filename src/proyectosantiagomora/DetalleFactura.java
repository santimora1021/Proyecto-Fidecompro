package proyectosantiagomora;

public class DetalleFactura {
    private Producto producto;
    private int cantidad;

    public DetalleFactura(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public double getSubtotal() { return producto.getPrecio() * cantidad; }

    public String generarLinea() {
        return cantidad + " x " + producto.getNombre() + " (₡" + producto.getPrecio() + ") = ₡" + getSubtotal();
    }
}