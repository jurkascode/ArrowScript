package net.jurka.arrow.tasks.fletch;

import net.jurka.arrow.stats.Stats;
import org.powerbot.script.methods.MethodContext;

public class KnifeDialogTask extends FletchTask {

    public KnifeDialogTask(MethodContext ctx, Stats stats) {
        super(ctx, stats);
    }

    @Override
    public boolean activate() {
        return knifeDialog.isVisible();
    }

    @Override
    public void execute() {
        if (knifeDialog.isVisible())
            knifeDialog.click();
    }
}
