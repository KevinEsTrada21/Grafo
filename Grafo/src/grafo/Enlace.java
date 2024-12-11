package grafo;

import java.awt.Point;
import java.awt.Graphics;
import java.awt.geom.Line2D;

public class Enlace {
    private Nodo nodo1;
    private Nodo nodo2;
    private int minutos;

    public Enlace(Nodo nodo1, Nodo nodo2, int minutos) {
        this.nodo1 = nodo1;
        this.nodo2 = nodo2;
        this.minutos = minutos;
    }

    public Nodo getNodo1() {
        return nodo1;
    }

    public Nodo getNodo2() {
        return nodo2;
    }

    public int getMinutos() {
        return minutos;
    }

    // Método para dibujar el enlace entre los nodos
    public void pintar(Graphics g) {
        g.drawLine(nodo1.getX(), nodo1.getY(), nodo2.getX(), nodo2.getY());
        g.drawString(minutos + " min", (nodo1.getX() + nodo2.getX()) / 2, (nodo1.getY() + nodo2.getY()) / 2);
    }

    // Método para verificar si un punto está cerca del enlace
    public boolean containsPoint(Point p) {
        int tolerancia = 10; // Tolerancia para considerar que el clic fue sobre el enlace
        Line2D linea = new Line2D.Float(nodo1.getX(), nodo1.getY(), nodo2.getX(), nodo2.getY());
        return linea.ptSegDist(p) <= tolerancia;
    }

    // Método para verificar si el enlace contiene un nodo
    public boolean contieneNodo(Nodo nodo) {
        return nodo.equals(nodo1) || nodo.equals(nodo2);
    }
}

