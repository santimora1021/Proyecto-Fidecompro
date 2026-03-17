package proyectosantiagomora;

import java.io.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Facturador {
    public static void imprimirFacturaFisica(Cliente c, ArrayList<DetalleFactura> detalles, double total) {
        String fileName = "Factura_" + c.getCedula() + ".txt";
        try (PrintWriter pw = new PrintWriter(new FileWriter(fileName))) {
            pw.println("========== FIDECOMPRO S.A. ==========");
            pw.println("Cliente: " + c.getNombre());
            pw.println("ID: " + c.getCedula());
            pw.println("-------------------------------------");
            for (DetalleFactura d : detalles) {
                pw.println(d.generarLinea());
            }
            pw.println("-------------------------------------");
            pw.println("TOTAL FINAL: ₡" + total);
            pw.println("=====================================");
            JOptionPane.showMessageDialog(null, "Factura física generada con éxito.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al guardar el archivo.");
        }
    }
}