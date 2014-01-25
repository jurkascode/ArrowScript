package net.jurka.arrow;

import net.jurka.arrow.stats.Stats;
import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.methods.MethodProvider;

public abstract class Task extends MethodProvider {

    protected Stats stats;

    public Task(MethodContext ctx, Stats stats) {
        super(ctx);
        this.stats = stats;
    }

    public abstract boolean activate();
    public abstract void execute();
}