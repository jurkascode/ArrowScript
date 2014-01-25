package net.jurka.arrow.tasks;

import net.jurka.arrow.Task;
import net.jurka.arrow.stats.Stats;
import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.wrappers.Tile;

public class Combat extends Task {


    public Combat(MethodContext ctx, Stats stats) {
        super(ctx, stats);
    }

    @Override
    public boolean activate() {
        return ctx.players.local().isInCombat();
    }

    @Override
    public void execute() {
        stats.setStatus("In compat");
        // Make a run
        ctx.movement.setRunning(true);

        Tile tile = ctx.players.local().getLocation();
        tile.randomize(10, 10);

        if (ctx.movement.isReachable(ctx.players.local().getLocation(), tile.getLocation())) {
           ctx.movement.findPath(tile).traverse();
        }

    }
}
