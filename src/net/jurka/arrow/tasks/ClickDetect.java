package net.jurka.arrow.tasks;

import net.jurka.arrow.Task;
import net.jurka.arrow.stats.Stats;
import org.powerbot.script.methods.MethodContext;

import java.awt.*;

public class ClickDetect extends Task {

    private int index, xMin, yMin, xMax, yMax;

    public ClickDetect(MethodContext ctx, Stats stats) {
        super(ctx, stats);
    }

    @Override
    public boolean activate() {
        // Gets activated on each cycle
        return true;
    }

    @Override
    public void execute() {
        Point point = ctx.mouse.getLocation();

        if (index == 0 || index >= 10) {
            xMin = point.x;
            yMin = point.y;

            xMax = point.x;
            yMax = point.y;
            index = 0;
        }

        if (point.x < xMin) xMin = point.x;
        else if (point.x > xMax) xMax = point.x;

        if (point.y < yMin) yMin = point.y;
        else if (point.y > yMax) yMax = point.y;

        index++;
    }

    public void drawMouseRectangle(Graphics g) {
        g.drawRect(xMin, yMin, xMax - xMin, yMax - yMin);
    }
}
