package proyectosantiagomora;

import java.io.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Facturador {
    public static void generarFacturaFisica(Cliente c, ArrayList<DetalleFactura> carrito, double total) throws IOException {
        String archivo = "Factura_" + c.getCedula() + ".txt";
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(archivo))) {
            writer.println("********** FIDECOMPRO S.A. **********");
            writer.println("Cliente: " + c.getNombre());
            writer.println("Cédula: " + c.getCedula());
            writer.println("-------------------------------------");
            for (DetalleFactura detalle : carrito) {
                writer.println(detalle.obtenerLinea());
            }
            writer.println("-------------------------------------");
            writer.println("TOTAL A PAGAR: ₡" + total);
            writer.println("*************************************");
            JOptionPane.showMessageDialog(null, "Factura física creada: " + archivo);
        } catch (IOException e) {
            // Relanzamos la excepción para que el Main la capture (Requerimiento 20)
            throw new IOException("Error técnico al escribir el archivo: " + e.getMessage());
        }
    }
}