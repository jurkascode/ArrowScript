package net.jurka.arrow.tasks;

import net.jurka.arrow.Task;
import net.jurka.arrow.stats.Stats;
import net.jurka.arrow.util.Constant;
import org.powerbot.script.lang.Filter;
import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.util.Random;
import org.powerbot.script.wrappers.Item;

public class Drop extends Task {

    public Drop(MethodContext ctx, Stats stats) {
        super(ctx, stats);
    }

    private Filter getCrap() {
        return new Filter<Item>() {

            @Override
            public boolean accept(Item item) {
                String name = item.getName();

                return !(name.equals("Logs") || name.equals("Arrow shaft"));
            }
        };
    }

    @Override
    public boolean activate() {
        return Random.nextBoolean() && 
               Random.nextBoolean() && 
               ctx.backpack.select(getCrap()).count() >= Constant.MAX_ITEMS_ALLOWED;
    }

    @Override
    public void execute() {

        stats.setStatus("Drop crap");

        // Drop all the other items
        for (Item item : ctx.backpack.select(getCrap())) {
            if (!item.isOnScreen()) {
                ctx.backpack.scroll(item);
            }
            item.interact("Drop");
            sleep(100, 500);
        }

        // When done update stats
//        stats.setArrowShaft(arrowShaft.getStackSize());
    }
}
