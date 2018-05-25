package view;

import model.World;

import javax.swing.*;
import java.awt.*;

public class WorldPanel extends JPanel {

    private final World world;

    public WorldPanel(World world) {
        this.world = world;
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        world.draw(g2);
    }

}
