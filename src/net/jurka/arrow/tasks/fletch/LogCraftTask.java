package net.jurka.arrow.tasks.fletch;

import net.jurka.arrow.stats.Stats;
import net.jurka.arrow.util.Constant;
import org.powerbot.script.methods.Hud;
import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.wrappers.Item;

public class LogCraftTask extends FletchTask {

    public LogCraftTask(MethodContext ctx, Stats stats) {
        super(ctx, stats);
    }

    @Override
    public boolean activate() {
        return ctx.backpack.select().count() >= Constant.MAX_ITEMS_INVETORY
                && !knifeDialog.isVisible()
                && !fletchDialog.isVisible()
                && !craftingDialog.isVisible();
    }

    @Override
    public void execute() {
        if (!ctx.hud.isOpen(Hud.Window.BACKPACK)) {
            ctx.hud.open(Hud.Window.BACKPACK);
        }

        if (!ctx.hud.isVisible(Hud.Window.BACKPACK)) {
            ctx.hud.view(Hud.Window.BACKPACK);
        }

        for (Item log : ctx.backpack.select().limit(5).name("Logs").shuffle().first()) {
            if (!log.isOnScreen()) {
                ctx.backpack.scroll(log);
            }
            log.interact("Craft");
        }
    }
}
