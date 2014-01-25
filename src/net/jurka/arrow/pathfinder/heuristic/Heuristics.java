package net.jurka.arrow.pathfinder.heuristic;


import net.jurka.arrow.pathfinder.Tile;

public interface Heuristics {

    public int distance(Tile a, Tile b);
}
