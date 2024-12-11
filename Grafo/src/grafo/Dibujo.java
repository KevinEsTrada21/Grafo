package grafo;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

public class Dibujo extends JPanel implements MouseListener, KeyListener {

    private Vector<Nodo> vectorNodos;
    private Vector<Enlace> enlaces;
    private Nodo nodoSeleccionado; // Nodo seleccionado para crear un enlace

    public Dibujo() {
        this.vectorNodos = new Vector<>();
        this.enlaces = new Vector<>();
        this.nodoSeleccionado = null; // Inicialmente no hay nodo seleccionado
        this.addMouseListener(this);
        this.addKeyListener(this);
        this.setFocusable(true); // Asegúrate de que el panel tenga el foco para escuchar las teclas
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for (Nodo nodo : vectorNodos) {
            nodo.pintar(g);
        }
        for (Enlace enlace : enlaces) {
            enlace.pintar(g);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // Agregar nodos con el botón izquierdo del mouse
        if (e.getButton() == MouseEvent.BUTTON1) {
            // Verificar si el clic fue sobre un nodo
            Nodo nodoSeleccionadoLocal = null;
            for (Nodo nodo : vectorNodos) {
                if (new Rectangle(nodo.getX() - Nodo.d / 2, nodo.getY() - Nodo.d / 2, Nodo.d, Nodo.d).contains(e.getPoint())) {
                    nodoSeleccionadoLocal = nodo;
                    break;
                }
            }

            if (nodoSeleccionadoLocal != null) {
                if (nodoSeleccionado == null) {
                    // Primer nodo seleccionado para enlace
                    nodoSeleccionado = nodoSeleccionadoLocal;
                } else {
                    // Segundo nodo seleccionado, crear un enlace
                    if (nodoSeleccionado != nodoSeleccionadoLocal) {
                        // Pedir los minutos al usuario
                        String minutosStr = JOptionPane.showInputDialog(this, "Ingrese los minutos del enlace:");
                        if (minutosStr != null && !minutosStr.trim().isEmpty()) {
                            try {
                                int minutos = Integer.parseInt(minutosStr);
                                // Crear un nuevo enlace con los minutos
                                Enlace nuevoEnlace = new Enlace(nodoSeleccionado, nodoSeleccionadoLocal, minutos);
                                enlaces.add(nuevoEnlace);
                                repaint();
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(this, "El valor ingresado no es válido para los minutos.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(this, "El valor de los minutos no puede estar vacío.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "No puedes enlazar un nodo consigo mismo.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    }
                    nodoSeleccionado = null; // Resetear para esperar el siguiente clic
                }
            } else {
                // Si no se ha seleccionado ningún nodo, crear uno nuevo
                String nombreNodo = JOptionPane.showInputDialog(this, "Ingrese el nombre del nodo:");
                if (nombreNodo != null && !nombreNodo.trim().isEmpty()) {
                    // Verificar si hay otro nodo cerca de la posición clicada
                    boolean nodoCerca = false;
                    for (Nodo nodo : vectorNodos) {
                        double distancia = Math.sqrt(Math.pow(e.getX() - nodo.getX(), 2) + Math.pow(e.getY() - nodo.getY(), 2));
                        if (distancia < Nodo.d) { // Si hay otro nodo cerca (menos de un radio de Nodo.d)
                            nodoCerca = true;
                            break;
                        }
                    }

                    if (!nodoCerca) {
                        // Crear un nuevo nodo con las coordenadas del clic y el nombre ingresado
                        Nodo nuevoNodo = new Nodo(e.getX(), e.getY(), nombreNodo);
                        vectorNodos.add(nuevoNodo);
                        repaint();
                    } else {
                        JOptionPane.showMessageDialog(this, "Ya existe un nodo cerca de esta ubicación.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "El nombre del nodo no puede estar vacío.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            // Al presionar espacio, pedir los nodos y calcular el camino más corto
            String nodoOrigen = JOptionPane.showInputDialog(this, "Ingrese el nombre del nodo de origen:");
            String nodoDestino = JOptionPane.showInputDialog(this, "Ingrese el nombre del nodo de destino:");

            Nodo origen = null;
            Nodo destino = null;

            // Buscar los nodos en el vectorNodos
            for (Nodo nodo : vectorNodos) {
                if (nodo.getNombre().equals(nodoOrigen)) {
                    origen = nodo;
                }
                if (nodo.getNombre().equals(nodoDestino)) {
                    destino = nodo;
                }
            }

            if (origen == null || destino == null) {
                JOptionPane.showMessageDialog(this, "No se encontraron los nodos ingresados.");
            } else {
                var camino = Dijkstra.calcularCaminoMasCorto(origen, destino, vectorNodos, enlaces);
                if (camino.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "No existe un camino entre los nodos.");
                } else {
                    StringBuilder mensaje = new StringBuilder("Camino más corto:\n");
                    for (Nodo nodo : camino) {
                        mensaje.append(nodo.getNombre()).append("\n");
                    }
                    JOptionPane.showMessageDialog(this, mensaje.toString());
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // No necesitamos implementar nada aquí
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // No necesitamos implementar nada aquí
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // No se utiliza, pero se debe implementar por la interfaz
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // No se utiliza, pero se debe implementar por la interfaz
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // No se utiliza, pero se debe implementar por la interfaz
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // No se utiliza, pero se debe implementar por la interfaz
    }
}
