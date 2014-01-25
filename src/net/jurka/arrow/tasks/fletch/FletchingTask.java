package net.jurka.arrow.tasks.fletch;

import net.jurka.arrow.stats.Stats;
import net.jurka.arrow.util.Constant;
import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.util.Condition;

import java.util.concurrent.Callable;

public class FletchingTask extends FletchTask {

    public FletchingTask(MethodContext ctx, Stats stats) {
        super(ctx, stats);
    }

    @Override
    public boolean activate() {
        return cancelButton.isVisible();
    }

    @Override
    public void execute() {

        stats.setStatus("Fletching");

        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return cancelButton.isVisible() && ctx.players.local().getAnimation() == Constant.FLETCHING;
            }
        });
    }
}
