package proyectosantiagomora;

import javax.swing.JOptionPane;
import java.util.ArrayList;

public class Main {
    // Uso de Colecciones para cumplimiento de rúbrica
    private static ArrayList<Usuario> usuarios = new ArrayList<>(); 
    private static ArrayList<Producto> inventario = new ArrayList<>();
    private static Usuario usuarioActual = null;

    public static void main(String[] args) {
        // Inicialización de datos
        precargarInventario();
        usuarios.add(new Usuario("admin", "123")); // Usuario por defecto
        
        boolean appActiva = true;
        while (appActiva) {
            if (usuarioActual == null) {
                // FLUJO DE INICIO (Sin sesión)
                String[] opciones = {"Iniciar Sesión", "Registrar Usuario", "Salir"};
                int sel = JOptionPane.showOptionDialog(null, "SISTEMA FIDECOMPRO\nSeleccione una opción:", 
                        "Acceso", 0, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

                if (sel == 0) login();
                else if (sel == 1) registrar();
                else appActiva = false;
            } else {
                // FLUJO PRINCIPAL (Sesión activa)
                String[] opciones = {"Facturar (Venta)", "Gestionar Inventario", "Administrar Usuarios", "Cerrar Sesión"};
                int sel = JOptionPane.showOptionDialog(null, "Sesión activa: " + usuarioActual.getUsername(), 
                        "Menú Principal", 0, JOptionPane.PLAIN_MESSAGE, null, opciones, opciones[0]);

                switch (sel) {
                    case 0 -> realizarVenta();
                    case 1 -> gestionarInventario();
                    case 2 -> administrarUsuarios();
                    case 3 -> usuarioActual = null; // Logout
                }
            }
        }
    }

    private static void precargarInventario() {
        inventario.add(new ProductoNacional("Arroz 1kg", 1200));
        inventario.add(new ProductoImportado("Aceite Oliva", 4500));
        inventario.add(new ProductoNacional("Frijoles 1kg", 1500));
    }

    // --- MÓDULO DE VENTAS (FACTURACIÓN) ---
    private static void realizarVenta() {
        try {
            String id = JOptionPane.showInputDialog("Cédula del Cliente:");
            String nomC = JOptionPane.showInputDialog("Nombre del Cliente:");
            if (id == null || nomC == null || id.isEmpty()) return;
            
            Cliente cliente = new Cliente(id, nomC);
            ArrayList<DetalleFactura> carrito = new ArrayList<>();
            boolean comprando = true;

            while (comprando) {
                // Uso de Polimorfismo al mostrar productos en la lista
                Producto p = (Producto) JOptionPane.showInputDialog(null, "Seleccione producto:", 
                        "Catálogo de Ventas", 3, null, inventario.toArray(), inventario.get(0));

                if (p != null) {
                    int cant = Integer.parseInt(JOptionPane.showInputDialog("Cantidad de " + p.getNombre() + ":"));
                    if (cant <= 0) throw new Exception("La cantidad debe ser mayor a 0.");
                    carrito.add(new DetalleFactura(p, cant));
                }
                
                int r = JOptionPane.showConfirmDialog(null, "¿Desea agregar otro producto?", "Continuar", 0);
                if (r != 0) comprando = false;
            }

            if (!carrito.isEmpty()) {
                int total = 0; // Números enteros según solicitud
                for (DetalleFactura d : carrito) total += d.getSubtotal();
                
                // Llamada al facturador físico
                Facturador.generarFacturaFisica(cliente, carrito, total);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error: Debe ingresar un número entero en la cantidad.", "Error de entrada", 0);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", 0);
        }
    }

    // --- MÓDULO DE INVENTARIO (CON IVA) ---
    private static void gestionarInventario() {
        String[] opciones = {"Añadir Producto", "Eliminar Producto", "Volver"};
        int sel = JOptionPane.showOptionDialog(null, "ADMINISTRACIÓN DE INVENTARIO", "Inventario", 
                0, JOptionPane.INFORMATION_MESSAGE, null, opciones, opciones[0]);

        try {
            if (sel == 0) {
                String nom = JOptionPane.showInputDialog("Nombre del nuevo producto:");
                double pre = Double.parseDouble(JOptionPane.showInputDialog("Precio base (Colones):"));

                // Selección de origen para aplicar IVA o no
                String[] tipos = {"Nacional (Sin IVA)", "Importado (+13% IVA)"};
                int tipoSel = JOptionPane.showOptionDialog(null, "¿Origen del producto?", "Configurar IVA", 
                        0, JOptionPane.QUESTION_MESSAGE, null, tipos, tipos[0]);

                if (tipoSel == 0) inventario.add(new ProductoNacional(nom, pre));
                else if (tipoSel == 1) inventario.add(new ProductoImportado(nom, pre));
                
                JOptionPane.showMessageDialog(null, "Producto guardado exitosamente.");
            } else if (sel == 1) {
                Producto borrar = (Producto) JOptionPane.showInputDialog(null, "Seleccione producto a eliminar:",
                        "Eliminar", 2, null, inventario.toArray(), inventario.get(0));
                if (borrar != null) inventario.remove(borrar);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en los datos. Intente de nuevo.", "Error", 0);
        }
    }

    // --- MÓDULO DE USUARIOS ---
    private static void administrarUsuarios() {
        if (usuarios.size() <= 1) {
            JOptionPane.showMessageDialog(null, "No hay otros usuarios registrados.");
            return;
        }
        String[] nombres = new String[usuarios.size()];
        for (int i = 0; i < usuarios.size(); i++) nombres[i] = usuarios.get(i).getUsername();
        
        String borrar = (String) JOptionPane.showInputDialog(null, "Seleccione usuario para eliminar:", 
                "Administrar Cuentas", 2, null, nombres, nombres[0]);
        
        if (borrar != null) {
            if (borrar.equals(usuarioActual.getUsername())) {
                JOptionPane.showMessageDialog(null, "No puede eliminar la cuenta en uso.");
            } else {
                usuarios.removeIf(u -> u.getUsername().equals(borrar));
                JOptionPane.showMessageDialog(null, "Usuario eliminado.");
            }
        }
    }

    private static void login() {
        String u = JOptionPane.showInputDialog("Usuario:");
        String p = JOptionPane.showInputDialog("Contraseña:");
        for (Usuario user : usuarios) {
            if (user.getUsername().equals(u) && user.getPassword().equals(p)) {
                usuarioActual = user;
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Credenciales incorrectas.", "Error", 0);
    }

    private static void registrar() {
        String u = JOptionPane.showInputDialog("Nuevo nombre de usuario:");
        String p = JOptionPane.showInputDialog("Nueva contraseña:");
        if (u != null && !u.trim().isEmpty() && p != null) {
            usuarios.add(new Usuario(u, p));
            JOptionPane.showMessageDialog(null, "Registro exitoso.");
        }
    }
}