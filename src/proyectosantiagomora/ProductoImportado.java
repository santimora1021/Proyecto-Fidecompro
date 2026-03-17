package proyectosantiagomora;

class ProductoImportado extends Producto {
    public ProductoImportado(String nombre, double precio) { super(nombre, precio); }
    @Override
    public int calcularPrecioFinal() {
        return (int) Math.round(precioBase * 1.13); // +13% IVA
    }
}