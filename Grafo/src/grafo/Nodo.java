package grafo;

import java.awt.Graphics;
import java.util.Objects;

public class Nodo {
    private int x, y;
    private String nombre;
    public static final int d = 20;  // Diámetro del nodo

    public Nodo(int x, int y, String nombre) {
        this.x = x;
        this.y = y;
        this.nombre = nombre;
    }

    // Métodos getter y setter
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getNombre() {
        return nombre;
    }

    // Método equals para comparar nodos
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Nodo nodo = (Nodo) obj;
        return x == nodo.x && y == nodo.y && Objects.equals(nombre, nodo.nombre);
    }

    // Método hashCode (es recomendable implementarlo junto con equals)
    @Override
    public int hashCode() {
        return Objects.hash(x, y, nombre);
    }

    // Método para pintar el nodo (si lo necesitas)
    public void pintar(Graphics g) {
        g.fillOval(x - d / 2, y - d / 2, d, d);
        g.drawString(nombre, x - d / 4, y - d / 2 - 5);
    }
}
