package tfumagalli;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class Laberinto {
    public static boolean existeCamino(
            int[][] laberinto,
            int filaInicio,
            int columnaInicio,
            int filaFin,
            int columnaFin) {
        // use map.entry as a "pair" class to hold two integers (x, y)
        Queue<Map.Entry<Integer, Integer>> q = new LinkedList<>();

        // breadth-first search for target square
        q.add(Map.entry(columnaInicio, filaInicio));

        while (!q.isEmpty()) {
            var current = q.remove();
            int x = current.getKey();
            int y = current.getValue();

            // this should only happen if the starting point is a wall, obviously there is
            // no path in this case
            if (laberinto[y][x] == 1)
                return false;

            // reached the end
            if (x == columnaFin && y == filaFin)
                return true;

            // mark current position as visited
            laberinto[y][x] = -1;

            // queue up adjacent positions that are not walls or visited
            if (x - 1 >= 0 && laberinto[y][x - 1] == 0)
                q.add(Map.entry(x - 1, y));
            if (x + 1 < laberinto[0].length && laberinto[y][x + 1] == 0)
                q.add(Map.entry(x + 1, y));
            if (y - 1 >= 0 && laberinto[y - 1][x] == 0)
                q.add(Map.entry(x, y - 1));
            if (y + 1 < laberinto.length && laberinto[y + 1][x] == 0)
                q.add(Map.entry(x, y + 1));
        }

        return false;
    }

    public static void main(String[] args) {
        int[][] laberinto = {
                { 0, 0, 1, 0 },
                { 1, 0, 1, 0 },
                { 0, 0, 0, 0 },
                { 0, 1, 1, 1 }
        };

        boolean existe = existeCamino(laberinto, 0, 0, 3, 0);
        if (existe) {
            System.out.println("Existe un camino en el laberinto.");
        } else {
            System.out.println("No existe un camino en el laberinto.");
        }
        System.out.println("Caminos recorridos:");
        imprimirLaberinto(laberinto);

        int[][] laberintoSinSalida = {
                { 0, 0, 1, 0 },
                { 1, 0, 1, 1 },
                { 0, 0, 0, 0 },
                { 0, 1, 1, 1 }
        };
        boolean existeSinSalida = existeCamino(laberintoSinSalida, 0, 0, 0, 3);
        if (existeSinSalida) {
            System.out.println("Existe un camino en el laberinto sin salida (¡error!).");
        } else {
            System.out.println("No existe un camino en el laberinto sin salida.");
        }
        System.out.println("Caminos recorridos:");
        imprimirLaberinto(laberintoSinSalida);
    }

    public static void imprimirLaberinto(int[][] laberinto) {
        for (int[] fila : laberinto) {
            for (int celda : fila) {
                System.out.print(celda + " ");
            }
            System.out.println();
        }
    }
}
