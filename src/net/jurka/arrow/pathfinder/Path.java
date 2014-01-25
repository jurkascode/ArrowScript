package net.jurka.arrow.pathfinder;

import net.jurka.arrow.pathfinder.heuristic.EuclideanHeuristic;
import net.jurka.arrow.pathfinder.heuristic.Heuristics;

import java.util.*;

public class Path {

    final private Tile[][] tiles;
    private Tile end, start;
    private int realDistance;
    private Heuristics heuristics;


    final private Comparator<Tile> comparator = new Comparator<Tile>() {

        @Override
        public int compare(Tile o1, Tile o2) {
            return o1.f - o2.f;
        }
    };

    final private int[][] fourDirections = {
            { 0, -1 },
            { 0, 1 },
            { 1, 0 },
            { -1, 0 }
    };

    final private int[][] eightDirections = {
            { 1, 1 },
            { -1, -1 },
            { -1, 1},
            { 1, -1 },
            { 0, -1 },
            { 0, 1 },
            { 1, 0 },
            { -1, 0 }
    };

    public Path(net.jurka.arrow.pathfinder.Map map, Heuristics heuristics) {
        this.tiles = map.getTiles();
        this.heuristics = heuristics;
    }

    public Path(net.jurka.arrow.pathfinder.Map map) {
        this(map, new EuclideanHeuristic());
    }

    public ArrayList<Tile> search(Tile start, Tile end) {
        this.end = end;
        this.start = start;

        final PriorityQueue<Tile> openQueue = new PriorityQueue<Tile>(30, comparator);
        final Set<Tile> closed = new HashSet<Tile>(); // Already evaluated pairs

        start.h = start.distance(end, heuristics);
        start.f = start.g + start.h;
        openQueue.add(start);

        Tile currentPair;
        while (!openQueue.isEmpty()) {
            currentPair = openQueue.poll();

            if (currentPair.equals(end)) {
                end = currentPair;
                break;
            }
            closed.add(currentPair.parent);

            // Go through the closest pairs
            for (Tile neighbor : possibleNearMoves(currentPair)) {

                if (closed.contains(currentPair) || closed.contains(neighbor)) continue;

                neighbor.parent = currentPair;
                openQueue.add(neighbor);

            }
        }

        return constructPath(end);
    }

    public ArrayList<Tile> search(Tile start, Tile end, boolean changeEnd) {
        this.start = start;
        this.end = end;

        ArrayList<Tile> path = new ArrayList<Tile>();
        if (changeEnd) {
            // The new end is located at wall.
            if (end.getType() == TileType.WALL) {
                end = changeEndTile(end);
            }

            // Both start and end are same
            if (end == null || start.equals(end)) {
                realDistance = 0;
                return path;
            }
        }

        return search(start, end);
    }

    private ArrayList<Tile> constructPath(Tile end) {
        ArrayList<Tile> path = new ArrayList<Tile>();
        Set<Tile> partPath = new HashSet<Tile>();

        realDistance = 0;

        // No path found
        if (end.parent == null) {
            realDistance = -1;
            clearCalulation();
            return path;
        }

        Tile current = end;
        while (current != null) {

            if (partPath.contains(current)) {
                System.out.println("Detected loop cancel");
                return new ArrayList<Tile>();
            }
            partPath.add(current);
            path.add(current);

            realDistance++;
            current = current.parent;
        }

        clearCalulation();

        return path;
    }

    public void clearCalulation() {
        // TODO: refactor to visit only the tiles that have been visited

        for (int y = 0; y < tiles.length; y++) {
            for (int x = 0; x < tiles[0].length; x++) {

                Tile reference = tiles[y][x];
                reference.parent = null;
                reference.g = 0;
                reference.f = 0;
                reference.h = 0;
            }
        }
    }

    public ArrayList<Tile> possibleNearMoves(Tile current) {
        ArrayList<Tile> foundTiles = new ArrayList<Tile>(4);

        for (int[] pair : fourDirections) {

            int x = current.getX() + pair[0],
                y = current.getY() + pair[1];

            if (isInBounds(x, y) && tiles[y][x].getType() == TileType.FLOOR) {

                Tile tile = tiles[y][x];

                tile.g = tile.getG(tile);
                tile.h = tile.distance(end, heuristics);
                tile.f = tile.g + tile.h;

                foundTiles.add(tile);
            }
        }

        return foundTiles;
    }

    private Tile changeEndTile(Tile current) {

        ArrayList<Tile> tileDistanceToStart = new ArrayList<Tile>();

        Comparator<Tile> comparator = new Comparator<Tile>() {
            @Override
            public int compare(Tile o1, Tile o2) {
                int firstDistance = o1.distance(start, heuristics);
                int secondDistance = o2.distance(start, heuristics);

                return Integer.compare(firstDistance, secondDistance);
            }
        };

        for (int[] pair : eightDirections) {

            int x = current.getX() + pair[0],
                    y = current.getY() + pair[1];

            if (isInBounds(x, y) && tiles[y][x].getType() != TileType.WALL) {

               tileDistanceToStart.add(tiles[y][x]);
            }
        }
        Collections.sort(tileDistanceToStart, comparator);

        if (tileDistanceToStart.isEmpty()) {
            return null;
        }

        return tileDistanceToStart.get(0);
    }

    private boolean isInBounds(int x, int y) {
        return (x >= 0 && x < tiles[0].length) &&
                (y >= 0 && y < tiles.length);
    }

    public int getRealDistance() {
        return realDistance;
    }
}
