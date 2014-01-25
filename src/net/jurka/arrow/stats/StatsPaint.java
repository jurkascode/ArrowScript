package net.jurka.arrow.stats;

import net.jurka.arrow.util.Util;

import java.awt.*;

/**
 * Created by E6420 on 11.01.14.
 */
public class StatsPaint {

    private Stats stats;

    public StatsPaint() {
    }

    public void draw(Graphics g) {
        if (stats == null) {
            return;
        }

        displayStats(g);
    }

    private void displayStats(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setFont(new Font("Sans serif", Font.BOLD, 13));

        int xAxis = 45, yAxis = 45, sp = 15;

        g2.drawString("Arrow shaft maker", xAxis, yAxis);
        yAxis += sp + 2;

        g2.setFont(new Font("Sans serif", Font.BOLD, 11));
        g2.drawString(String.format("Status: %s", stats.getStatus()), xAxis, yAxis);
        yAxis += sp;

        g2.drawString(String.format("Run time: %s", Util.formatTime(stats.getStartTime())), xAxis, yAxis);
        yAxis += sp;

        g2.drawString(String.format("Logs cut: %d", stats.getLogsCut()), xAxis, yAxis);
        yAxis += sp;

        g2.drawString(String.format("Arrow shafts: %d", stats.getArrowShaft()), xAxis, yAxis);
        yAxis += sp;

        g2.drawString(String.format("Clicks made: %d", stats.getClicks()), xAxis, yAxis);
        yAxis += sp;

        g2.drawString(String.format("Miss clicks: %d", stats.getMissClicks()), xAxis, yAxis);

        g2.setColor(new Color(0, 250, 0, 50));
        g2.fillRect(40, 30, 260, yAxis - 20);
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }

}
