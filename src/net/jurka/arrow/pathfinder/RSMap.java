package net.jurka.arrow.pathfinder;

import org.powerbot.script.wrappers.CollisionFlag;
import org.powerbot.script.wrappers.CollisionMap;

import java.util.ArrayList;

public class RSMap {

    private static final int MAP_WIDTH = 104;
    private static final int MAP_HEIGHT = 104;

    public static Map convertMap(CollisionMap collisionMap) {
        CollisionFlag flag;
        Tile[][] tiles = new Tile[MAP_HEIGHT][MAP_WIDTH];

        for (int y = 0; y < MAP_HEIGHT; y++) {
            for (int x = 0; x < MAP_WIDTH; x++) {
                try {
                    flag = collisionMap.getFlagAt(x, y);
                    tiles[y][x] = new Tile(x, y, getType(flag));

                } catch (ArrayIndexOutOfBoundsException e) {
                    /// Access some of the edge
                }
            }
        }

        return new Map(tiles);
    }

    public static TileType getType(CollisionFlag flag) {
        TileType type = TileType.FLOOR;

        // TODO: Rewrite with the the bitwise operations
        if (flag.contains(CollisionFlag.OBJECT_BLOCK) ||
                flag.contains(CollisionFlag.DEAD_BLOCK) ||
                flag.contains(CollisionFlag.DECORATION_BLOCK) ||
                flag.contains(CollisionFlag.PADDING)) {

            type = TileType.WALL;
        }
        return type;
    }

    public static Tile fromRSTile(org.powerbot.script.wrappers.Tile location, org.powerbot.script.wrappers.Tile base, CollisionMap map) {

        int x = location.x - base.x,
            y = location.y - base.y;

        CollisionFlag flag = map.getFlagAt(x, y);

        return new Tile(x, y, getType(flag));
    }

    public static org.powerbot.script.wrappers.Tile[] convertTilePath(ArrayList<Tile> tiles, org.powerbot.script.wrappers.Tile base) {
        org.powerbot.script.wrappers.Tile[] tilePath = new org.powerbot.script.wrappers.Tile[tiles.size()];

        for (int i = 0; i < tiles.size(); i++) {
            Tile tile = tiles.get(i);

            tilePath[i] = new org.powerbot.script.wrappers.Tile(tile.getX() + base.getX(), tile.getY() + base.getY());
        }
        return tilePath;
    }
}
