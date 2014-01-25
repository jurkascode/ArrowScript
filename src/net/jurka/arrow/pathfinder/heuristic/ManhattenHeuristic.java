package net.jurka.arrow.pathfinder.heuristic;

import net.jurka.arrow.pathfinder.Tile;

public class ManhattenHeuristic implements Heuristics {

    @Override
    public int distance(Tile a, Tile b) {
        return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());
    }
}
