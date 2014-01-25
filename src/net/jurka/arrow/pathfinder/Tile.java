package net.jurka.arrow.pathfinder;

import net.jurka.arrow.pathfinder.heuristic.Heuristics;

public class Tile  {

    private int x;
    private int y;
    private TileType type;
    public Tile parent;
    public int g = 0, h, f;

    public Tile(int x, int y, TileType type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public Tile(int x, int y) {
        this(x, y, TileType.FLOOR);
    }

    public void setParent(Tile parent) {
        this.parent = parent;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public TileType getType() {
        return type;
    }

    public void setType(TileType type) {
        this.type = type;
    }

    public int getF() {
        return f;
    }

    public void setF(int f) {
        this.f = f;
    }

    public int getG(Tile n) {
        if (n.parent != null) {

            Tile p = n.parent;
            return p.g + 1;
        }
        return 0;
    }

    public void setG(int g) {
        this.g = g;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tile tile = (Tile) o;

        if (x != tile.x) return false;
        if (y != tile.y) return false;
        if (type != tile.type) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        result = 31 * result + type.hashCode();
        return result;
    }


    @Override
    public String toString() {
        String s;
        switch (type) {
            case FLOOR:
                s = ".";
            break;

            case PATH:
                s = "g";
            break;

            default:
                s = "x";
        }
        return s;
    }

    public int distance(Tile b, Heuristics heuristics) {
        return heuristics.distance(this, b);
    }
}
