package net.jurka.arrow.tasks;

import net.jurka.arrow.Task;
import net.jurka.arrow.pathfinder.Map;
import net.jurka.arrow.pathfinder.Path;
import net.jurka.arrow.pathfinder.RSMap;
import net.jurka.arrow.stats.Stats;
import net.jurka.arrow.util.Constant;
import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.wrappers.CollisionMap;
import org.powerbot.script.wrappers.GameObject;
import org.powerbot.script.wrappers.Model;
import org.powerbot.script.wrappers.Tile;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

public class Chop extends Task {

    private Rectangle[] onScreenHuds;
    private GameObject lastTree;
    private CollisionMap collisionMap;
    private Map map;
    private Path path;
    private Tile[] lastPath = null;
    private GameObject lastObject = null;
    private Tile mapBase;

    public Chop(MethodContext ctx, Stats stats) {
        super(ctx, stats);
        onScreenHuds = ctx.hud.getBounds();

        // TODO: handle walking off the map
        mapBase = ctx.game.getMapBase();
        collisionMap = ctx.movement.getCollisionMap();

        map = RSMap.convertMap(collisionMap);
        path = new Path(map);
    }

    @Override
    public boolean activate() {

        final Tile base = ctx.game.getMapBase();

        if (!base.equals(mapBase)) {
            collisionMap = ctx.movement.getCollisionMap();

            map = RSMap.convertMap(collisionMap);
            path = new Path(map);
        }

        return ctx.backpack.select().count() < Constant.MAX_ITEMS_INVETORY
                && !ctx.players.local().isInMotion()
                && ctx.players.local().getAnimation() == -1
                && findTree(base); // The expensive call should be in end
    }

    public boolean findTree(final Tile base) {

        // TODO: Optimize this call, this requires expensive calculations
        final Tile playerLocation = ctx.players.local().getLocation();

        return !ctx.objects
            .select()
            .name("Tree", "Dead tree", "Evergreen")
            .nearest()
            .limit(5)
            .sort(new Comparator<GameObject>() {

                @Override
                public int compare(GameObject o1, GameObject o2) {
                    int first, second;

                    path.search(RSMap.fromRSTile(playerLocation, base, collisionMap),
                            RSMap.fromRSTile(o1.getLocation(), base, collisionMap), true);
                    first = path.getRealDistance();

                    path.search(RSMap.fromRSTile(playerLocation, base, collisionMap),
                            RSMap.fromRSTile(o2.getLocation(), base, collisionMap), true);
                    second = path.getRealDistance();

                    return Integer.compare(first, second);
                }
            })
            .isEmpty();
    }

    public boolean isBlockedByHud(Point point) {
        for (Rectangle rectangle : onScreenHuds) {
            if (rectangle.contains(point)) {
                return true;
            }
        }

        return false;
    }

    private boolean isTreeFar(GameObject object) {
        return object.getLocation().distanceTo(ctx.players.local().getLocation()) >= Constant.TREE_DISTANCE;
    }

    private boolean chop(GameObject tree) {
        Point interactive = tree.getCenterPoint();

        if (tree != null && tree.equals(lastTree)) {
            stats.incrMissClicks();
            lastTree.getModel();
            interactive = lastTree.getNextPoint();
        }

        if (!isBlockedByHud(interactive)) {
            ctx.mouse.click(interactive, true);
            stats.incrClicks();
            lastTree = tree;
            return true;
        } else {
            ctx.camera.turnTo(tree);
        }
        return false;
    }

    @Override
    public void execute() {
        Tile base = ctx.game.getMapBase();

        stats.setStatus("Chop");
        GameObject tree = ctx.objects.poll();

        // TODO: Rename path finder Tile differently
        ArrayList<net.jurka.arrow.pathfinder.Tile> tilePath = path.search(
                RSMap.fromRSTile(ctx.players.local().getLocation(), base, collisionMap),
                RSMap.fromRSTile(tree.getLocation(), base, collisionMap), true);

        lastObject = tree;
        lastPath = RSMap.convertTilePath(tilePath, ctx.game.getMapBase());

        if (tree.isOnScreen()) {
            chop(tree);

        } else {

            // Tree too far
            ctx.camera.turnTo(tree);
            // Check if back on screen
            if (tree.isOnScreen() && !isTreeFar(tree)) {
                chop(tree);
            } else {
                // walk there
                ctx.movement.stepTowards(tree);
            }
        }
    }

    public void draw(Graphics g) {

        drawObject(g);
        drawPath(g);
    }

    private void drawObject(Graphics g) {

        GameObject object = getObject();

        if (object != null) {
            g.setColor(Color.blue);
            Model model = object.getModel();
            if (model != null) {
                model.drawWireFrame(g);
            }
        }
    }

    private void drawPath(Graphics g) {

        Tile[] path = getPath();
        if (path != null) {

            for (Tile tile : path) {

                tile.getMatrix(ctx).draw(g);
            }
        }
    }

    public GameObject getObject() {
        return lastObject;
    }

    public Tile[] getPath() {
        return lastPath;
    }
}