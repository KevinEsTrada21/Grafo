package grafo;

import java.util.*;

public class Dijkstra {

    public static List<Nodo> calcularCaminoMasCorto(Nodo origen, Nodo destino, Vector<Nodo> nodos, Vector<Enlace> enlaces) {
        // Crear un mapa de adyacencia
        Map<Nodo, List<Enlace>> adyacencia = new HashMap<>();
        for (Enlace enlace : enlaces) {
            adyacencia.computeIfAbsent(enlace.getNodo1(), k -> new ArrayList<>()).add(enlace);
            adyacencia.computeIfAbsent(enlace.getNodo2(), k -> new ArrayList<>()).add(enlace);
        }

        // Inicialización de las distancias
        Map<Nodo, Integer> distancias = new HashMap<>();
        for (Nodo nodo : nodos) {
            distancias.put(nodo, Integer.MAX_VALUE);
        }
        distancias.put(origen, 0);

        // Predecesores para reconstruir el camino
        Map<Nodo, Nodo> predecesores = new HashMap<>();

        // Cola de prioridad para el algoritmo de Dijkstra
        PriorityQueue<Nodo> pq = new PriorityQueue<>(Comparator.comparingInt(distancias::get));
        pq.add(origen);

        while (!pq.isEmpty()) {
            Nodo actual = pq.poll();

            // Si llegamos al nodo destino, terminamos
            if (actual.equals(destino)) {
                break;
            }

            // Iterar sobre los enlaces adyacentes
            for (Enlace enlace : adyacencia.getOrDefault(actual, Collections.emptyList())) {
                Nodo vecino = (enlace.getNodo1().equals(actual)) ? enlace.getNodo2() : enlace.getNodo1();
                int nuevaDistancia = distancias.get(actual) + enlace.getMinutos();

                if (nuevaDistancia < distancias.get(vecino)) {
                    distancias.put(vecino, nuevaDistancia);
                    predecesores.put(vecino, actual);
                    pq.add(vecino);
                }
            }
        }

        // Reconstruir el camino más corto
        List<Nodo> camino = new ArrayList<>();
        Nodo nodoActual = destino;
        while (nodoActual != null) {
            camino.add(nodoActual);
            nodoActual = predecesores.get(nodoActual);
        }
        Collections.reverse(camino);
        return camino;
    }
}
