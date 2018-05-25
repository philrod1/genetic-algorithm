package controller;

import model.World;
import view.WorldPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class DotWorld {
    public static void main(String[] args) {
        try {
            File file = new File("res/dot-maze.png");
            System.out.println(file.getAbsolutePath());
            final BufferedImage image = ImageIO.read(file);
            final World world = new World(image, 1000, 1000);
            final JPanel panel = new WorldPanel(world);
            SwingUtilities.invokeLater(() -> {
                final JFrame frame = new JFrame("DotsWorld");
                panel.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
                frame.getContentPane().add(panel);
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                frame.setVisible(true);
                frame.pack();
            });
//            final Timer timer = new Timer();
//            final TimerTask task = new TimerTask() {
//                @Override
//                public void run() {
//                    boolean pause = false;
//                    if (!world.go()) {
//                        System.out.println("Done");
//                        world.reset();
//                        timer.cancel();
//                        timer.purge();
//                    }
//                    panel.repaint();
//                    if (pause) {
//                        try {
//                            Thread.sleep(2000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            };
            while (true) {
                if (!world.go()) {
                    panel.repaint();
                    world.nextGeneration(0.001);
                    world.reset();
//                    try {
//                        Thread.sleep(2000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                }
                panel.repaint();
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
