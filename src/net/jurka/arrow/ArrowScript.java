package net.jurka.arrow;

import net.jurka.arrow.stats.Stats;
import net.jurka.arrow.stats.StatsPaint;
import net.jurka.arrow.tasks.*;
import net.jurka.arrow.tasks.fletch.FletchDialogTask;
import net.jurka.arrow.tasks.fletch.FletchingTask;
import net.jurka.arrow.tasks.fletch.KnifeDialogTask;
import net.jurka.arrow.tasks.fletch.LogCraftTask;
import org.powerbot.event.MessageEvent;
import org.powerbot.event.MessageListener;
import org.powerbot.event.PaintListener;
import org.powerbot.script.Manifest;
import org.powerbot.script.PollingScript;
import org.powerbot.script.util.Random;
import org.powerbot.script.wrappers.Tile;

import java.awt.*;
import java.util.LinkedList;

@Manifest(
        name = "Arrow shaft script",
        authors = "Jurka",
        description = "Creates arrow shafts and really fast"
)
public class ArrowScript extends PollingScript implements MessageListener, PaintListener {

    private Tile startTile;

    private Stats stats;
    private StatsPaint paintStats = new StatsPaint();
    private LinkedList<Task> tasks = new LinkedList<Task>();

    private Chop chop;
    private ClickDetect detect;
    
    @Override
    public void start() {
        stats = Stats.getInstance();
        paintStats.setStats(stats);

        startTile = ctx.players.local().getLocation();
        chop = new Chop(ctx, stats);
        detect = new ClickDetect(ctx, stats);

        // TODO: Refactor the task adding into some facade, or factory method

        tasks.add(chop);
        tasks.add(new LogCraftTask(ctx, stats));
        tasks.add(new KnifeDialogTask(ctx, stats));
        tasks.add(new FletchDialogTask(ctx, stats));
        tasks.add(new FletchingTask(ctx, stats));

        tasks.add(new Combat(ctx, stats));
        tasks.add(new Drop(ctx, stats));
        tasks.add(new Run(ctx, stats, startTile));
        tasks.add(new AntiPattern(ctx, stats));
        tasks.add(detect);
    }

    @Override
    public int poll() {

        if (tasks.isEmpty()) {

            log.warning("There are no tasks to execute stop!");
            this.getController().stop();
        }

        for (Task task : tasks) {
            if (task.activate()) {
                log.info("Current task: " + task.getClass().getSimpleName());
                task.execute();
                return Random.nextInt(500, 1500);
            }
        }

        return 500;
    }

    @Override
    public void messaged(MessageEvent e) {
        String message = e.getMessage();

        if (message.contains("You get some logs.")) {
            stats.incrLogsCut();
        } else if (message.contains("You carefully cut the wood into")) {
            stats.incArrowShaft(15);
        }
    }

    @Override
    public void repaint(Graphics g) {
        paintStats.draw(g);
        chop.draw(g);
        detect.drawMouseRectangle(g);
    }


}