package proyectosantiagomora;

import javax.swing.JOptionPane;
import java.util.ArrayList;

public class Main {
    private static ArrayList<Usuario> listaUsuarios = new ArrayList<>();
    private static ArrayList<Producto> inventario = new ArrayList<>();

    public static void main(String[] args) {
        // Carga inicial de productos (10 artículos predeterminados)
        cargarInventarioInicial();
        
        boolean appCorriendo = true;
        while (appCorriendo) {
            String[] opcionesInciales = {"Registrar Usuario", "Login", "Cerrar App"};
            int eleccion = JOptionPane.showOptionDialog(null, "SISTEMA FIDECOMPRO\nSeleccione una opción:", 
                    "Inicio", 0, JOptionPane.QUESTION_MESSAGE, null, opcionesInciales, opcionesInciales[0]);

            if (eleccion == 0) registrarUsuario();
            else if (eleccion == 1) login();
            else appCorriendo = false;
        }
    }

    private static void cargarInventarioInicial() {
        inventario.add(new Producto("Arroz 1kg", 1200));
        inventario.add(new Producto("Frijoles 1kg", 1500));
        inventario.add(new Producto("Aceite 1L", 2200));
        inventario.add(new Producto("Café 500g", 3500));
        inventario.add(new Producto("Leche 1L", 950));
        inventario.add(new Producto("Azúcar 1kg", 850));
        inventario.add(new Producto("Atún", 1100));
        inventario.add(new Producto("Pasta", 600));
        inventario.add(new Producto("Detergente", 4500));
        inventario.add(new Producto("Pan Integral", 1400));
    }

    private static void registrarUsuario() {
        String u = JOptionPane.showInputDialog("Nuevo Usuario:");
        String p = JOptionPane.showInputDialog("Contraseña:");
        if (u != null && p != null) {
            listaUsuarios.add(new Usuario(u, p));
            JOptionPane.showMessageDialog(null, "Usuario registrado con éxito.");
        }
    }

    private static void login() {
        String u = JOptionPane.showInputDialog("Usuario:");
        String p = JOptionPane.showInputDialog("Contraseña:");
        boolean acceso = false;
        for (Usuario user : listaUsuarios) {
            if (user.getUsername().equals(u) && user.getPassword().equals(p)) acceso = true;
        }

        if (acceso) menuPrincipal();
        else JOptionPane.showMessageDialog(null, "Acceso Denegado", "Error", JOptionPane.ERROR_MESSAGE);
    }

    private static void menuPrincipal() {
        boolean enMenu = true;
        while (enMenu) {
            String[] opciones = {"Facturar (Venta)", "Gestionar Inventario", "Cerrar Sesión"};
            int selec = JOptionPane.showOptionDialog(null, "MENÚ PRINCIPAL", "Fidecompro", 
                    0, JOptionPane.PLAIN_MESSAGE, null, opciones, opciones[0]);

            switch (selec) {
                case 0 -> realizarVenta();
                case 1 -> gestionarInventario();
                default -> enMenu = false;
            }
        }
    }

    private static void gestionarInventario() {
        String[] opciones = {"Añadir Producto", "Eliminar Producto", "Volver"};
        int eleccion = JOptionPane.showOptionDialog(null, "ADMINISTRACIÓN DE PRODUCTOS", "Inventario", 
                0, JOptionPane.INFORMATION_MESSAGE, null, opciones, opciones[0]);

        if (eleccion == 0) { // Añadir
            String nom = JOptionPane.showInputDialog("Nombre del nuevo producto:");
            double pre = Double.parseDouble(JOptionPane.showInputDialog("Precio en Colones:"));
            inventario.add(new Producto(nom, pre));
            JOptionPane.showMessageDialog(null, "Producto añadido.");
        } 
        else if (eleccion == 1) { // Eliminar
            Producto borrar = (Producto) JOptionPane.showInputDialog(null, "Seleccione el producto a eliminar:",
                    "Eliminar", JOptionPane.WARNING_MESSAGE, null, inventario.toArray(), inventario.get(0));
            if (borrar != null) {
                inventario.remove(borrar);
                JOptionPane.showMessageDialog(null, "Producto eliminado.");
            }
        }
    }

    private static void realizarVenta() {
        String ced = JOptionPane.showInputDialog("Cédula del Cliente:");
        String nomC = JOptionPane.showInputDialog("Nombre del Cliente:");
        Cliente cliente = new Cliente(ced, nomC);
        
        ArrayList<DetalleFactura> carrito = new ArrayList<>();
        boolean comprando = true;

        while (comprando) {
            Producto p = (Producto) JOptionPane.showInputDialog(null, "Seleccione artículo:", 
                    "Venta", JOptionPane.QUESTION_MESSAGE, null, inventario.toArray(), inventario.get(0));

            if (p != null) {
                int cant = Integer.parseInt(JOptionPane.showInputDialog("Cantidad de " + p.getNombre() + ":"));
                carrito.add(new DetalleFactura(p, cant));
            }

            int r = JOptionPane.showConfirmDialog(null, "¿Agregar otro producto?", "Continuar", JOptionPane.YES_NO_OPTION);
            if (r == JOptionPane.NO_OPTION) comprando = false;
        }

        double total = 0;
        for (DetalleFactura d : carrito) total += d.getSubtotal();
        
        // Llamada a la clase Facturador para generar el archivo .txt
        Facturador.imprimirFacturaFisica(cliente, carrito, total);
    }
}