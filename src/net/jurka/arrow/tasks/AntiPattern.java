package net.jurka.arrow.tasks;

import net.jurka.arrow.Task;
import net.jurka.arrow.stats.Stats;
import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.util.Random;

public class AntiPattern extends Task {


    public AntiPattern(MethodContext ctx, Stats stats) {
        super(ctx, stats);
    }

    @Override
    public boolean activate() {
        return Random.nextBoolean() && Random.nextBoolean();
    }

    @Override
    public void execute() {
        if (Random.nextBoolean()) {
            stats.setStatus("Set angle");
            ctx.camera.setAngle(Random.nextInt(0, 370));
        }

        if (Random.nextBoolean()) {
            stats.setStatus("Set camera pitch");
            ctx.camera.setPitch(Random.nextInt(45, 90));
        }

    }
}
