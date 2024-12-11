package grafo;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Grafo {
    public static void main(String[] args) {

        JOptionPane.showMessageDialog(null, "------GRAFOS------\nRECORRIDO DE GPS", "INICIO", JOptionPane.INFORMATION_MESSAGE);

        JFrame ventana = new JFrame("GRAFO");
        ventana.add(new Dibujo());
        ventana.setSize(600, 600);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Modificaciones según el paso 5
        ventana.setLocationRelativeTo(null); // Centra la ventana en la pantalla
        ventana.setResizable(false);         // Bloquea el cambio de tamaño de la ventana

        ventana.setVisible(true); // Muestra la ventana
    }
}
