package proyectosantiagomora;

class ProductoNacional extends Producto {
    public ProductoNacional(String nombre, double precio) { super(nombre, precio); }
    @Override
    public int calcularPrecioFinal() {
        return (int) Math.round(precioBase); // Sin IVA adicional
    }
}