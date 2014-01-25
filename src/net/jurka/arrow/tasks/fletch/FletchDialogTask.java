package net.jurka.arrow.tasks.fletch;

import net.jurka.arrow.stats.Stats;
import org.powerbot.script.methods.MethodContext;

public class FletchDialogTask extends FletchTask {

    public FletchDialogTask(MethodContext ctx, Stats stats) {
        super(ctx, stats);
    }

    @Override
    public boolean activate() {
        return fletchDialog.isVisible();
    }

    @Override
    public void execute() {
        stats.setStatus("Fletch dialog open");
        if (fletchDialog.isVisible())
            fletchDialog.click();
    }
}
