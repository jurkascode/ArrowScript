package net.jurka.arrow.tasks;

import net.jurka.arrow.Task;
import net.jurka.arrow.stats.Stats;
import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.wrappers.Tile;

/**
 * Created by E6420 on 11.01.14.
 */
public class Run extends Task {

    private static final int MAX_REACH = 35;
    private Tile startTile;
    
    public Run(MethodContext ctx, Stats stats, Tile startTile) {
        super(ctx, stats);
        this.startTile = startTile;
    }

    @Override
    public boolean activate() {
        return startTile.distanceTo(ctx.players.local().getLocation()) >= MAX_REACH;
    }

    @Override
    public void execute() {
        stats.setStatus("Wow we are far away walk back");

        Tile tile = startTile;
        tile.randomize(10, 10);

        if (ctx.movement.isReachable(ctx.players.local().getLocation(), tile.getLocation())) {
            ctx.movement.findPath(tile).traverse();
        }
    }
}
