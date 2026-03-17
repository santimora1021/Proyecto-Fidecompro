package proyectosantiagomora;

import java.util.ArrayList;

public class Inventario {
    private ArrayList<Producto> productos;

    public Inventario() {
        this.productos = new ArrayList<>(); // Uso de Colecciones 
    }

    public void agregarProducto(Producto p) throws Exception {
        if (p.getNombre().isEmpty()) {
            throw new Exception("El nombre del producto no puede estar vacío"); // Uso de Excepciones 
        }
        productos.add(p);
    }
}