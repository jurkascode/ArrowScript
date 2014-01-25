package net.jurka.arrow.tasks.fletch;

import net.jurka.arrow.Task;
import net.jurka.arrow.stats.Stats;
import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.wrappers.Component;

public abstract class FletchTask extends Task {


    protected final Component knifeDialog = ctx.widgets.get(1179, 33);
    protected final Component fletchDialog = ctx.widgets.get(1370, 38);
    protected final Component craftingDialog = ctx.widgets.get(1251, 8);

    protected final Component fetchDoneButton = ctx.widgets.get(1251, 55);
    protected final Component cancelButton = ctx.widgets.get(1251, 49);

    public FletchTask(MethodContext ctx, Stats stats) {
        super(ctx, stats);
    }
}
