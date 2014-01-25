package net.jurka.arrow.pathfinder;

import java.util.ArrayList;

public class Map {

    private Tile[][] tiles;

    public Map(Tile[][] tiles) {
        this.tiles = tiles;
    }

    public Map(String str) {
        this.tiles = toMap(str);
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public void setTiles(Tile[][] tiles) {
        this.tiles = tiles;
    }

    public Tile[][] toMap(String str) {
        String[] lines =  str.split("\n");
        Tile[][] tiles = new Tile[lines.length][lines[0].length()];

        for (int y = 0; y < lines.length; y++) {
            for (int x = 0; x < lines[0].length(); x++) {

                TileType type;

                switch (lines[y].charAt(x)) {
                    case '.':
                        type = TileType.FLOOR;
                        break;

                    default:
                        type = TileType.WALL;
                }

                tiles[y][x] = new Tile(x, y, type);

            }
        }
        return tiles;
    }

    public void clearPath(ArrayList<Tile> path) {

        for (Tile tile : path) {
            tiles[tile.getY()][tile.getX()].setType(TileType.FLOOR);
        }
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("");

        for (int y = 0; y < tiles.length; y++) {
            for (int x = 0; x < tiles[0].length; x++) {

                sb.append(tiles[y][x].toString());
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
